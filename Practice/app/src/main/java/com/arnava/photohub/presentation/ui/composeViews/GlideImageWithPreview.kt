package com.arnava.photohub.presentation.ui.composeViews

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.arnava.photohub.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GlideImageWithPreview(
    data: Any?,
    modifier: androidx.compose.ui.Modifier? = null,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    if (data == null)
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = contentDescription,
            modifier = modifier ?: androidx.compose.ui.Modifier,
            alignment = Alignment.Center,
            contentScale = contentScale
        )
    else
        GlideImage(
            model = data,
            contentDescription = contentDescription,
            modifier = modifier ?: androidx.compose.ui.Modifier,
            contentScale = contentScale
        )
}