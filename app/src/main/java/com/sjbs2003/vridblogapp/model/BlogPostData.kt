package com.sjbs2003.vridblogapp.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class BlogPostData(
    val id: Int,
    val date: String,
    @SerialName("date_gmt") val dateGmt: String,
    val guid: Guid,
    val modified: String,
    @SerialName("modified_gmt") val modifiedGmt: String,
    val slug: String,
    val status: String,
    val type: String,
    val link: String,
    val title: Title,
    val content: Content,
    val excerpt: Excerpt,
    val author: Int,
    @SerialName("featured_media") val featuredMedia: Int,
    @SerialName("comment_status") val commentStatus: String,
    @SerialName("ping_status") val pingStatus: String,
    val sticky: Boolean,
    val template: String,
    val format: String,
    val categories: List<Int>,
    val tags: List<Int>,
    @SerialName("yoast_head") val yoastHead: String,
    @SerialName("yoast_head_json") val yoastHeadJson: YoastHeadJson,
    @SerialName("_links") val links: Links
)

@Serializable
data class Guid(val rendered: String)

@Serializable
data class Title(val rendered: String)

@Serializable
data class Content(
    val rendered: String,
    val protected: Boolean
)

@Serializable
data class Excerpt(
    val rendered: String,
    val protected: Boolean
)

@Serializable
data class YoastHeadJson(
    val title: String,
    val description: String,
    val robots: Robots,
    @SerialName("og_locale") val ogLocale: String,
    @SerialName("og_type") val ogType: String,
    @SerialName("og_title") val ogTitle: String,
    @SerialName("og_description") val ogDescription: String,
    @SerialName("og_url") val ogUrl: String,
    @SerialName("og_site_name") val ogSiteName: String,
    @SerialName("article_published_time") val articlePublishedTime: String,
    @SerialName("article_modified_time") val articleModifiedTime: String,
    @SerialName("twitter_card") val twitterCard: String,
    @SerialName("twitter_misc") val twitterMisc: TwitterMisc
)

@Serializable
data class Robots(
    val index: String,
    val follow: String,
    @SerialName("max-snippet") val maxSnippet: String,
    @SerialName("max-image-preview") val maxImagePreview: String,
    @SerialName("max-video-preview") val maxVideoPreview: String
)

@Serializable
data class TwitterMisc(
    @SerialName("Written by") val writtenBy: String,
    @SerialName("Est. reading time") val estReadingTime: String
)

@Serializable
data class Links(
    val self: List<Self>,
    val collection: List<Collection>,
    val about: List<About>,
    val author: List<Author>,
    val replies: List<Replies>,
    @SerialName("version-history") val versionHistory: List<VersionHistory>,
    @SerialName("predecessor-version") val predecessorVersion: List<PredecessorVersion>,
    @SerialName("wp:featuredmedia") val wpFeaturedmedia: List<WpFeaturedmedia>,
    @SerialName("wp:attachment") val wpAttachment: List<WpAttachment>,
    @SerialName("wp:term") val wpTerm: List<WpTerm>,
    val curies: List<Cury>
)

@Serializable
data class Self(val href: String)

@Serializable
data class Collection(val href: String)

@Serializable
data class About(val href: String)

@Serializable
data class Author(
    val embeddable: Boolean,
    val href: String
)

@Serializable
data class Replies(
    val embeddable: Boolean,
    val href: String
)

@Serializable
data class VersionHistory(
    val count: Int,
    val href: String
)

@Serializable
data class PredecessorVersion(
    val id: Int,
    val href: String
)

@Serializable
data class WpFeaturedmedia(
    val embeddable: Boolean,
    val href: String
)

@Serializable
data class WpAttachment(val href: String)

@Serializable
data class WpTerm(
    val taxonomy: String,
    val embeddable: Boolean,
    val href: String
)

@Serializable
data class Cury(
    val name: String,
    val href: String,
    val templated: Boolean
)

@Serializable
data class BlogItemUiModel(
    val title: String,
    val imageUrl: String,
    val date: String,
    val content: String,
    val excerpt: String
)
