package com.arnava.photohub.presentation.ui.composeViews

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnava.photohub.api.model.Photo
import com.arnava.rick_n_morty.ui.theme.UnsplashTheme

@Composable
fun PhotoView(photo: Photo, callback: (Photo) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(6.dp)
            .background(Color.DarkGray, shape = RoundedCornerShape(9.dp))
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        GlideImageWithPreview(
            data = photo.urls.regular, Modifier
                .size(110.dp)
                .padding(10.dp)
                .weight(0.3f)
        )
        Column(
            modifier = Modifier
                .padding(start = 6.dp)
                .weight(0.4f)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 6.dp),
                text = photo.description.toString(),
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.White
            )
        }
        Button(
            modifier = Modifier
                .padding(end = 10.dp)
                .weight(0.2f)
                .clickable { callback(photo) },
            onClick = { callback(photo) },
            content = {
                Text(
                    text = "Show details",
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        )
    }
}
//
//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun ComposablePreview() {
//    val character = Character(
//        1,
//        "name",
//        "status",
//        "species",
//        "type",
//        "gender",
//        Origin("origin", "url"),
//        Location("name", "url"),
//        null,
//        emptyList(),
//        "created",
//        "url"
//    )
//    UnsplashTheme() {
//        PhotoView(
//            character
//        ) {}
//    }
//}