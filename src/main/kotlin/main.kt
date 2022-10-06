import java.util.concurrent.atomic.AtomicInteger

data class Post(
    val id: Int = 1,
    val owner_id: Int = 1,
    val from_id: Int = 2,
    val date: Int,
    val text: String,
    val friends_only: Boolean,
    val likes: Likes
)


data class Likes(
    var count: Int = 0,
    val user_likes: Boolean = false
)

class WallService {
    var posts = emptyArray<Post>()

    private var idGenerator = AtomicInteger()
    private fun generateId(): Int = idGenerator.incrementAndGet()

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
    val post = Post(1, 1, 2, 0, "Привет", true, Likes())
    val service = WallService()
    service.add(post)
    println(service.posts.last())
    service.likedById(1)
    println(service.posts.last())
}


