package com.arnava.photohub.auth.models

data class TokensModel(
    val accessToken: String,
    val refreshToken: String,
)
