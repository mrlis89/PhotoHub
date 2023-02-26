package com.arnava.photohub.data.models.unsplash.collection

import com.arnava.photohub.data.models.unsplash.photo.Links
import com.arnava.photohub.data.models.unsplash.user.ProfileImage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoundCollectionList(
    @Json(name = "total")
    val total: Int?,
    @Json(name = "total_pages")
    val totalPages: Int?,
    @Json(name = "results")
    val results: List<FoundCollection>
)

@JsonClass(generateAdapter = true)
data class FoundCollection(
    @Json(name = "id")
    val id: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "published_at")
    val publishedAt: String?,
    @Json(name = "last_collected_at")
    val lastCollectedAt: String?,
    @Json(name = "updated_at")
    val updatedAt: String?,
    @Json(name = "curated")
    val curated: Boolean?,
    @Json(name = "featured")
    val featured: Boolean?,
    @Json(name = "total_photos")
    val totalPhotos: Int?,
    @Json(name = "private")
    val `private`: Boolean?,
    @Json(name = "share_key")
    val shareKey: String?,
    @Json(name = "tags")
    val tags: List<Tag?>?,
    @Json(name = "links")
    val links: Links?,
    @Json(name = "user")
    val user: User?,
    @Json(name = "cover_photo")
    val coverPhoto: CoverPhoto?,
    @Json(name = "preview_photos")
    val previewPhotos: List<PreviewPhoto?>?
) {
    @JsonClass(generateAdapter = true)
    data class Tag(
        @Json(name = "type")
        val type: String?,
        @Json(name = "title")
        val title: String?,
        @Json(name = "source")
        val source: Source?
    ) {
        @JsonClass(generateAdapter = true)
        data class Source(
            @Json(name = "ancestry")
            val ancestry: Ancestry?,
            @Json(name = "title")
            val title: String?,
            @Json(name = "subtitle")
            val subtitle: String?,
            @Json(name = "description")
            val description: String?,
            @Json(name = "meta_title")
            val metaTitle: String?,
            @Json(name = "meta_description")
            val metaDescription: String?,
            @Json(name = "cover_photo")
            val coverPhoto: CoverPhoto?
        ) {
            @JsonClass(generateAdapter = true)
            data class Ancestry(
                @Json(name = "type")
                val type: Type?,
                @Json(name = "category")
                val category: Category?,
                @Json(name = "subcategory")
                val subcategory: Subcategory?
            ) {
                @JsonClass(generateAdapter = true)
                data class Type(
                    @Json(name = "slug")
                    val slug: String?,
                    @Json(name = "pretty_slug")
                    val prettySlug: String?
                )

                @JsonClass(generateAdapter = true)
                data class Category(
                    @Json(name = "slug")
                    val slug: String?,
                    @Json(name = "pretty_slug")
                    val prettySlug: String?
                )

                @JsonClass(generateAdapter = true)
                data class Subcategory(
                    @Json(name = "slug")
                    val slug: String?,
                    @Json(name = "pretty_slug")
                    val prettySlug: String?
                )
            }

            @JsonClass(generateAdapter = true)
            data class CoverPhoto(
                @Json(name = "id")
                val id: String?,
                @Json(name = "created_at")
                val createdAt: String?,
                @Json(name = "updated_at")
                val updatedAt: String?,
                @Json(name = "promoted_at")
                val promotedAt: String?,
                @Json(name = "width")
                val width: Int?,
                @Json(name = "height")
                val height: Int?,
                @Json(name = "color")
                val color: String?,
                @Json(name = "blur_hash")
                val blurHash: String?,
                @Json(name = "description")
                val description: String?,
                @Json(name = "alt_description")
                val altDescription: String?,
                @Json(name = "urls")
                val urls: Urls?,
                @Json(name = "links")
                val links: Links?,
                @Json(name = "likes")
                val likes: Int?,
                @Json(name = "liked_by_user")
                val likedByUser: Boolean?,
                @Json(name = "current_user_collections")
                val currentUserCollections: List<Any?>?,
                @Json(name = "sponsorship")
                val sponsorship: Any?,
                @Json(name = "topic_submissions")
                val topicSubmissions: TopicSubmissions?,
                @Json(name = "premium")
                val premium: Boolean?,
                @Json(name = "user")
                val user: User?
            ) {
                @JsonClass(generateAdapter = true)
                data class Urls(
                    @Json(name = "raw")
                    val raw: String?,
                    @Json(name = "full")
                    val full: String?,
                    @Json(name = "regular")
                    val regular: String?,
                    @Json(name = "small")
                    val small: String?,
                    @Json(name = "thumb")
                    val thumb: String?,
                    @Json(name = "small_s3")
                    val smallS3: String?
                )

                @JsonClass(generateAdapter = true)
                data class Links(
                    @Json(name = "self")
                    val self: String?,
                    @Json(name = "html")
                    val html: String?,
                    @Json(name = "download")
                    val download: String?,
                    @Json(name = "download_location")
                    val downloadLocation: String?
                )

                @JsonClass(generateAdapter = true)
                data class TopicSubmissions(
                    @Json(name = "spirituality")
                    val spirituality: Spirituality?,
                    @Json(name = "current-events")
                    val currentEvents: CurrentEvents?,
                    @Json(name = "textures-patterns")
                    val texturesPatterns: TexturesPatterns?,
                    @Json(name = "wallpapers")
                    val wallpapers: Wallpapers?,
                    @Json(name = "monochrome")
                    val monochrome: Monochrome?
                ) {
                    @JsonClass(generateAdapter = true)
                    data class Spirituality(
                        @Json(name = "status")
                        val status: String?,
                        @Json(name = "approved_on")
                        val approvedOn: String?
                    )

                    @JsonClass(generateAdapter = true)
                    data class CurrentEvents(
                        @Json(name = "status")
                        val status: String?,
                        @Json(name = "approved_on")
                        val approvedOn: String?
                    )

                    @JsonClass(generateAdapter = true)
                    data class TexturesPatterns(
                        @Json(name = "status")
                        val status: String?,
                        @Json(name = "approved_on")
                        val approvedOn: String?
                    )

                    @JsonClass(generateAdapter = true)
                    data class Wallpapers(
                        @Json(name = "status")
                        val status: String?
                    )

                    @JsonClass(generateAdapter = true)
                    data class Monochrome(
                        @Json(name = "status")
                        val status: String?
                    )
                }

                @JsonClass(generateAdapter = true)
                data class User(
                    @Json(name = "id")
                    val id: String?,
                    @Json(name = "updated_at")
                    val updatedAt: String?,
                    @Json(name = "username")
                    val username: String?,
                    @Json(name = "name")
                    val name: String?,
                    @Json(name = "first_name")
                    val firstName: String?,
                    @Json(name = "last_name")
                    val lastName: String?,
                    @Json(name = "twitter_username")
                    val twitterUsername: String?,
                    @Json(name = "portfolio_url")
                    val portfolioUrl: String?,
                    @Json(name = "bio")
                    val bio: String?,
                    @Json(name = "location")
                    val location: String?,
                    @Json(name = "links")
                    val links: Links?,
                    @Json(name = "profile_image")
                    val profileImage: ProfileImage?,
                    @Json(name = "instagram_username")
                    val instagramUsername: String?,
                    @Json(name = "total_collections")
                    val totalCollections: Int?,
                    @Json(name = "total_likes")
                    val totalLikes: Int?,
                    @Json(name = "total_photos")
                    val totalPhotos: Int?,
                    @Json(name = "accepted_tos")
                    val acceptedTos: Boolean?,
                    @Json(name = "for_hire")
                    val forHire: Boolean?,
                    @Json(name = "social")
                    val social: Social?
                ) {
                    @JsonClass(generateAdapter = true)
                    data class Links(
                        @Json(name = "self")
                        val self: String?,
                        @Json(name = "html")
                        val html: String?,
                        @Json(name = "photos")
                        val photos: String?,
                        @Json(name = "likes")
                        val likes: String?,
                        @Json(name = "portfolio")
                        val portfolio: String?,
                        @Json(name = "following")
                        val following: String?,
                        @Json(name = "followers")
                        val followers: String?
                    )

                    @JsonClass(generateAdapter = true)
                    data class ProfileImage(
                        @Json(name = "small")
                        val small: String?,
                        @Json(name = "medium")
                        val medium: String?,
                        @Json(name = "large")
                        val large: String?
                    )

                    @JsonClass(generateAdapter = true)
                    data class Social(
                        @Json(name = "instagram_username")
                        val instagramUsername: String?,
                        @Json(name = "portfolio_url")
                        val portfolioUrl: String?,
                        @Json(name = "twitter_username")
                        val twitterUsername: String?,
                        @Json(name = "paypal_email")
                        val paypalEmail: Any?
                    )
                }
            }
        }
    }

    @JsonClass(generateAdapter = true)
    data class Links(
        @Json(name = "self")
        val self: String?,
        @Json(name = "html")
        val html: String?,
        @Json(name = "photos")
        val photos: String?,
        @Json(name = "related")
        val related: String?
    )

    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "id")
        val id: String?,
        @Json(name = "updated_at")
        val updatedAt: String?,
        @Json(name = "username")
        val username: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "first_name")
        val firstName: String?,
        @Json(name = "last_name")
        val lastName: String?,
        @Json(name = "twitter_username")
        val twitterUsername: String?,
        @Json(name = "portfolio_url")
        val portfolioUrl: String?,
        @Json(name = "bio")
        val bio: String?,
        @Json(name = "location")
        val location: String?,
        @Json(name = "links")
        val links: Links?,
        @Json(name = "profile_image")
        val profileImage: ProfileImage?,
        @Json(name = "instagram_username")
        val instagramUsername: String?,
        @Json(name = "total_collections")
        val totalCollections: Int?,
        @Json(name = "total_likes")
        val totalLikes: Int?,
        @Json(name = "total_photos")
        val totalPhotos: Int?,
        @Json(name = "accepted_tos")
        val acceptedTos: Boolean?,
        @Json(name = "for_hire")
        val forHire: Boolean?,
        @Json(name = "social")
        val social: Social?
    ) {
        @JsonClass(generateAdapter = true)
        data class Links(
            @Json(name = "self")
            val self: String?,
            @Json(name = "html")
            val html: String?,
            @Json(name = "photos")
            val photos: String?,
            @Json(name = "likes")
            val likes: String?,
            @Json(name = "portfolio")
            val portfolio: String?,
            @Json(name = "following")
            val following: String?,
            @Json(name = "followers")
            val followers: String?
        )

        @JsonClass(generateAdapter = true)
        data class ProfileImage(
            @Json(name = "small")
            val small: String?,
            @Json(name = "medium")
            val medium: String?,
            @Json(name = "large")
            val large: String?
        )

        @JsonClass(generateAdapter = true)
        data class Social(
            @Json(name = "instagram_username")
            val instagramUsername: String?,
            @Json(name = "portfolio_url")
            val portfolioUrl: String?,
            @Json(name = "twitter_username")
            val twitterUsername: String?,
            @Json(name = "paypal_email")
            val paypalEmail: Any?
        )
    }

    @JsonClass(generateAdapter = true)
    data class CoverPhoto(
        @Json(name = "id")
        val id: String?,
        @Json(name = "created_at")
        val createdAt: String?,
        @Json(name = "updated_at")
        val updatedAt: String?,
        @Json(name = "promoted_at")
        val promotedAt: String?,
        @Json(name = "width")
        val width: Int?,
        @Json(name = "height")
        val height: Int?,
        @Json(name = "color")
        val color: String?,
        @Json(name = "blur_hash")
        val blurHash: String?,
        @Json(name = "description")
        val description: String?,
        @Json(name = "alt_description")
        val altDescription: String?,
        @Json(name = "urls")
        val urls: Urls?,
        @Json(name = "links")
        val links: Links?,
        @Json(name = "likes")
        val likes: Int?,
        @Json(name = "liked_by_user")
        val likedByUser: Boolean?,
        @Json(name = "current_user_collections")
        val currentUserCollections: List<Any?>?,
        @Json(name = "sponsorship")
        val sponsorship: Any?,
        @Json(name = "topic_submissions")
        val topicSubmissions: TopicSubmissions?,
        @Json(name = "user")
        val user: User?
    ) {
        @JsonClass(generateAdapter = true)
        data class Urls(
            @Json(name = "raw")
            val raw: String?,
            @Json(name = "full")
            val full: String?,
            @Json(name = "regular")
            val regular: String?,
            @Json(name = "small")
            val small: String?,
            @Json(name = "thumb")
            val thumb: String?,
            @Json(name = "small_s3")
            val smallS3: String?
        )

        @JsonClass(generateAdapter = true)
        data class Links(
            @Json(name = "self")
            val self: String?,
            @Json(name = "html")
            val html: String?,
            @Json(name = "download")
            val download: String?,
            @Json(name = "download_location")
            val downloadLocation: String?
        )

        @JsonClass(generateAdapter = true)
        data class TopicSubmissions(
            @Json(name = "film")
            val film: Film?,
            @Json(name = "interiors")
            val interiors: Interiors?,
            @Json(name = "spirituality")
            val spirituality: Spirituality?
        ) {
            @JsonClass(generateAdapter = true)
            data class Film(
                @Json(name = "status")
                val status: String?
            )

            @JsonClass(generateAdapter = true)
            data class Interiors(
                @Json(name = "status")
                val status: String?,
                @Json(name = "approved_on")
                val approvedOn: String?
            )

            @JsonClass(generateAdapter = true)
            data class Spirituality(
                @Json(name = "status")
                val status: String?,
                @Json(name = "approved_on")
                val approvedOn: String?
            )
        }

        @JsonClass(generateAdapter = true)
        data class User(
            @Json(name = "id")
            val id: String?,
            @Json(name = "updated_at")
            val updatedAt: String?,
            @Json(name = "username")
            val username: String?,
            @Json(name = "name")
            val name: String?,
            @Json(name = "first_name")
            val firstName: String?,
            @Json(name = "last_name")
            val lastName: String?,
            @Json(name = "twitter_username")
            val twitterUsername: String?,
            @Json(name = "portfolio_url")
            val portfolioUrl: String?,
            @Json(name = "bio")
            val bio: String?,
            @Json(name = "location")
            val location: String?,
            @Json(name = "links")
            val links: Links?,
            @Json(name = "profile_image")
            val profileImage: ProfileImage?,
            @Json(name = "instagram_username")
            val instagramUsername: String?,
            @Json(name = "total_collections")
            val totalCollections: Int?,
            @Json(name = "total_likes")
            val totalLikes: Int?,
            @Json(name = "total_photos")
            val totalPhotos: Int?,
            @Json(name = "accepted_tos")
            val acceptedTos: Boolean?,
            @Json(name = "for_hire")
            val forHire: Boolean?,
            @Json(name = "social")
            val social: Social?
        ) {
            @JsonClass(generateAdapter = true)
            data class Links(
                @Json(name = "self")
                val self: String?,
                @Json(name = "html")
                val html: String?,
                @Json(name = "photos")
                val photos: String?,
                @Json(name = "likes")
                val likes: String?,
                @Json(name = "portfolio")
                val portfolio: String?,
                @Json(name = "following")
                val following: String?,
                @Json(name = "followers")
                val followers: String?
            )

            @JsonClass(generateAdapter = true)
            data class ProfileImage(
                @Json(name = "small")
                val small: String?,
                @Json(name = "medium")
                val medium: String?,
                @Json(name = "large")
                val large: String?
            )

            @JsonClass(generateAdapter = true)
            data class Social(
                @Json(name = "instagram_username")
                val instagramUsername: String?,
                @Json(name = "portfolio_url")
                val portfolioUrl: String?,
                @Json(name = "twitter_username")
                val twitterUsername: String?,
                @Json(name = "paypal_email")
                val paypalEmail: Any?
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class PreviewPhoto(
        @Json(name = "id")
        val id: String?,
        @Json(name = "created_at")
        val createdAt: String?,
        @Json(name = "updated_at")
        val updatedAt: String?,
        @Json(name = "blur_hash")
        val blurHash: String?,
        @Json(name = "urls")
        val urls: Urls?
    ) {
        @JsonClass(generateAdapter = true)
        data class Urls(
            @Json(name = "raw")
            val raw: String?,
            @Json(name = "full")
            val full: String?,
            @Json(name = "regular")
            val regular: String?,
            @Json(name = "small")
            val small: String?,
            @Json(name = "thumb")
            val thumb: String?,
            @Json(name = "small_s3")
            val smallS3: String?
        )
    }
}
