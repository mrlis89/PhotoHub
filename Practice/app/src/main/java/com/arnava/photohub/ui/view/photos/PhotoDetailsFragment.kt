package com.arnava.photohub.ui.view.photos

import android.Manifest
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.arnava.photohub.R
import com.arnava.photohub.data.models.unsplash.photo.DetailedPhoto
import com.arnava.photohub.databinding.FragmentPhotoDetailsBinding
import com.arnava.photohub.utils.worker.*
import com.arnava.photohub.viewmodel.PhotoDetailsViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class PhotoDetailsFragment : Fragment() {

    lateinit var workManager: WorkManager
    private var _binding: FragmentPhotoDetailsBinding? = null
    private val binding get() = _binding!!
    private val photoDetailsViewModel: PhotoDetailsViewModel by viewModels()
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (!map.values.all { it }) Toast.makeText(
                activity,
                "permission is not Granted",
                Toast.LENGTH_LONG
            ).show()
        }

    private fun checkPermissions() {
        val permissionsAreGranted = REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                requireActivity(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (!permissionsAreGranted) resultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            it.getString(ARG_PARAM1)?.let { photoId ->
                photoDetailsViewModel.loadPhotoById(photoId)
            }
        }
        workManager = WorkManager.getInstance(requireContext())
        _binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermissions()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            photoDetailsViewModel.photo.collect { detailedPhoto ->
                with(binding) {
                    likeByUserDetailed.isSelected = detailedPhoto?.likedByUser ?: false
                    likeByUserDetailed.setOnClickListener { likeIcon ->
                        detailedPhoto?.let { likeClick(it) }
                        likeIcon.isSelected = !likeIcon.isSelected
                        detailedPhoto?.likedByUser = likeIcon.isSelected
                    }
                    Glide
                        .with(this@PhotoDetailsFragment)
                        .load(detailedPhoto?.urls?.full)
                        .placeholder(R.drawable.placeholder_photo)
                        .into(photoView)
                    Glide
                        .with(this@PhotoDetailsFragment)
                        .load(detailedPhoto?.user?.profileImage?.medium)
                        .placeholder(R.drawable.placeholder_photo)
                        .into(authorImage)

                    authorName.text = detailedPhoto?.user?.name
                    authorUrl.text = detailedPhoto?.user?.instagramUsername
                        ?: detailedPhoto?.user?.twitterUsername
                    likesCountDetailed.text = detailedPhoto?.likes.toString()
                    locationText.text = detailedPhoto?.location?.name
                    totalDownloads.text = detailedPhoto?.downloads.toString()
                    likesCountDetailed.text = detailedPhoto?.likes.toString()
                    tagsText.text = detailedPhoto?.tags?.joinToString {
                        " ${it.title}"
                    }
                    exif.text =
                        buildString {
                            append(getString(R.string.camera))
                            append(detailedPhoto?.exif?.name ?: "")
                            append(getString(R.string.iso))
                            append(detailedPhoto?.exif?.iso ?: "")
                            append(getString(R.string.focal))
                            append(detailedPhoto?.exif?.focalLength ?: "")
                            append(getString(R.string.exposure))
                            append(detailedPhoto?.exif?.exposureTime ?: "")
                            append(getString(R.string.aperture))
                            append(detailedPhoto?.exif?.aperture ?: "")
                        }
                    creatorInfo.text = buildString {
                        append(getString(R.string.author))
                        append("\nInstagram: ${detailedPhoto?.user?.instagramUsername ?: ""}")
                        append("\nInfo: ${detailedPhoto?.user?.bio ?: ""}")
                    }
                    downloadBtn.setOnClickListener {
                        startDownloadingFile(
                            detailedPhoto?.urls?.raw ?: "",
                            detailedPhoto?.id.toString()
                        )
                    }

                }
            }
        }

    }

    private fun startDownloadingFile(url: String, fileName: String) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()
        val data = Data.Builder()

        data.apply {
            putString(KEY_FILE_NAME, "$fileName.jpeg")
            putString(KEY_FILE_URL, url)
        }

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(FileDownloadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        workManager.enqueue(oneTimeWorkRequest)

        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(viewLifecycleOwner) { info ->
                info?.let {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            val uri = it.outputData.getString(KEY_FILE_URI)
                            uri?.let {
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.setDataAndType(uri.toUri(), "image/jpeg")
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                val pendingIntent =
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.getActivity(
                                        requireContext(),
                                        0,
                                        intent,
                                        PendingIntent.FLAG_IMMUTABLE
                                    )
                                    else PendingIntent.getActivity(
                                        requireContext(),
                                        0,
                                        intent,
                                        PendingIntent.FLAG_UPDATE_CURRENT
                                    )
                                displayNotification(pendingIntent)
                            }
                        }
                        WorkInfo.State.FAILED -> {
                            Toast.makeText(requireContext(), "Download in failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                        WorkInfo.State.RUNNING -> {
                            Toast.makeText(requireContext(), "Download in progress...", Toast.LENGTH_SHORT)
                                .show()
                        }
                        else -> {

                        }
                    }
                }
            }
    }

    private fun displayNotification(pendingIntent: PendingIntent) {
        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(CHANNEL_DESC)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .addAction(R.drawable.placeholder_photo, getString(R.string.notif_open_photo), pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(requireContext()).notify(NOTIFICATION_ID, notification)
    }

    private fun likeClick(item: DetailedPhoto) {
        runBlocking {
            if (!item.likedByUser) photoDetailsViewModel.likePhoto(item.id)
            else photoDetailsViewModel.unlikePhoto(item.id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val REQUIRED_PERMISSIONS = buildList {
            add(Manifest.permission.READ_EXTERNAL_STORAGE)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    }
}