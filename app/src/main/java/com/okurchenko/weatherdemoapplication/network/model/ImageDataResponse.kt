package com.okurchenko.weatherdemoapplication.network.model

import com.google.gson.annotations.SerializedName

data class ImageDataResponse(
    @SerializedName("totalHits") val totalHits: Int,
    @SerializedName("hits") val hits: List<Hits>,
    @SerializedName("total") val total: Int
)

data class Hits(
    @SerializedName("largeImageURL") val largeImageURL: String,
    @SerializedName("webformatHeight") val webFormatHeight: Int,
    @SerializedName("webformatWidth") val webFormatWidth: Int,
    @SerializedName("likes") val likes: Int,
    @SerializedName("imageWidth") val imageWidth: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("views") val views: Int,
    @SerializedName("comments") val comments: Int,
    @SerializedName("pageURL") val pageURL: String,
    @SerializedName("imageHeight") val imageHeight: Int,
    @SerializedName("webformatURL") val webFormatURL: String,
    @SerializedName("type") val type: String,
    @SerializedName("previewHeight") val previewHeight: Int,
    @SerializedName("tags") val tags: String,
    @SerializedName("downloads") val downloads: Int,
    @SerializedName("user") val user: String,
    @SerializedName("favorites") val favorites: Int,
    @SerializedName("imageSize") val imageSize: Int,
    @SerializedName("previewWidth") val previewWidth: Int,
    @SerializedName("userImageURL") val userImageURL: String,
    @SerializedName("previewURL") val previewURL: String
)