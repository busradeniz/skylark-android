package ostmodern.skylark.model


data class Set
/**
 * Constructor for [Set].
 *
 * @param uid           id for set object
 * @param title         title of set object
 * @param filmCount     count of film in set
 * @param formattedBody formatted description of set
 * @param imageUrls     list of image for set
 * @param items         list of episodes
 */
(val uid: String, val title: String, val filmCount: Int, val formattedBody: String,
 val imageUrls: List<String>, val items: List<Episode>)