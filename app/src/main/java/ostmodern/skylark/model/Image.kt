package ostmodern.skylark.model

data class Image
/**
 * Constructor for [Image].
 *
 * @param uid  id for image
 * @param url  link that includes image
 * @param self self url.
 */
(val uid: String, val url: String, val self: String)
