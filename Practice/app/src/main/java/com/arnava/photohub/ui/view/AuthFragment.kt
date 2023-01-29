package com.arnava.photohub.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arnava.photohub.R
import com.arnava.photohub.data.local.TokenStorage
import com.arnava.photohub.databinding.FragmentAuthBinding
import com.arnava.photohub.ui.view.home.HomeFragment
import com.arnava.photohub.ui.view.photos.PhotoDetailsFragment
import com.arnava.photohub.utils.common.NetworkState
import com.arnava.photohub.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        val photoUri = authViewModel.intentExternalData
        if (photoUri != null) {
            val bundle = Bundle().apply {
                putString(ARG_PARAM1, photoUri.lastPathSegment)
            }
            findNavController().navigate(R.id.action_navigation_auth_to_photoDetailsFragment, bundle)
            parentFragmentManager.commit {
                replace(R.id.nav_host_fragment, PhotoDetailsFragment::class.java, bundle)
            }
        } else {
            val localToken = authViewModel.getTokenFromLocalStorage()
            if (localToken != "" && NetworkState.isConnected()) {
                TokenStorage.accessToken = localToken
                authViewModel.saveUserNameToStorage()
                findNavController().navigate(R.id.action_navigation_auth_to_navigation_home)
            }
        }
        return binding.root
    }

    private val authResponseResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val dataIntent = it.data ?: return@registerForActivityResult
            authViewModel.handleAuthResponseIntent(dataIntent)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            authViewModel.authorizeAndWait(authResponseResult)
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            authViewModel.loadingFlow.collect {
                updateVisibility(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            authViewModel.toastFlow.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            authViewModel.authSuccessFlow.collect {
                findNavController().navigate(R.id.navigation_home)
            }
        }

    }

    private fun updateVisibility(isLoading: Boolean) {
        binding.loginButton.isVisible = !isLoading
        binding.loginProgress.isVisible = isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}