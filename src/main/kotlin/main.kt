import java.lang.RuntimeException
import java.util.concurrent.atomic.AtomicInteger

interface Attachment {
    val type: String
}
class Photo (val id_photo: Int? = null)
class PhotoAttachment : Attachment {
    override val type: String = "Photo"
    val photo: Photo = Photo()
}
class Video (val id_video: Int? = null)
class VideoAttachment : Attachment {
    override val type: String = "Video"
    val video: Video = Video()
}
data class Post(
    val id: Int = 1,
    val owner_id: Int = 1,
    val from_id: Int = 2,
    val date: Int,
    val text: String,
    val friends_only: Boolean,
    val likes: Likes,
    val original: Post?,
    val attachments: Array<Attachment>? = null,
    var comment: Array<Comment>? = null
)

data class Comment (val id: Int, val text: String)

data class Likes(
    var count: Int = 0,
    val user_likes: Boolean = false
)

class PostNotFoundException (message: String): RuntimeException(message)

class WallService {
    var posts = emptyArray<Post>()
    var comments = emptyArray<Comment>()

    private var idGenerator = AtomicInteger()
    private fun generateId(): Int = idGenerator.incrementAndGet()

    fun createComment (postId: Int, comment: Comment): Comment {
        for (post in posts) {
            if (post.id == postId){
                comments += comment
                return comments.last()
            }
        }
        throw PostNotFoundException("Такой пост не найден")
    }

    fun add(post: Post) = post.copy(id = generateId()).apply {
        posts += post
        return posts.last()
    }

    fun counts() = posts.size

    fun update(post: Post): Boolean {
        val (match, rest) = posts.partition { it.id == post.id }
        if (match.isNotEmpty()) {
            posts = rest.plus(post.copy(id = match.first().id, date = match.first().date)).toTypedArray()
        }
        return match.isNotEmpty()
    }

    fun find(predicate: (Post) -> Boolean): Post? = posts.firstOrNull(predicate)

    fun likedById(id: Int) {
        for ((index, post) in posts.withIndex())
            if (post.id == id) {
                posts[index] = post.copy(likes = post.likes.copy(count = post.likes.count + 1))
            }
    }
}

fun main() {
    val post = Post(1, 1, 2, 0, "Привет", true, Likes(), null)
    val service = WallService()
    service.add(post)
    println(service.posts.last())
    service.likedById(1)
    println(service.posts.last())
    service.createComment(1, Comment(2, "Привет"))

}


