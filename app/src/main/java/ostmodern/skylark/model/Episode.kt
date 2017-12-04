package ostmodern.skylark.model


import com.google.gson.annotations.SerializedName

data class Episode
/**
 * Constructor for [Episode].
 *
 * @param uid         value for id
 * @param self        url of episode
 * @param contentUrl  content url of episode
 * @param contentType content type of episode
 */
(val uid: String, val self: String, @field:SerializedName("content_url")
val contentUrl: String, @field:SerializedName("content_type")
 val contentType: String)
