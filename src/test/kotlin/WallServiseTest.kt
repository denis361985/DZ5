import junit.framework.TestCase.*
import org.junit.Test


internal class WallServiceTest {

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val service = WallService()
        val commentForTest = Comment(102, "test")
        service.createComment(55, commentForTest)
    }
    @Test
    fun shouldAddComment() {
        val service = WallService()
        val postForComment = Post(1, 1, 2, 0, "Привет", true, Likes(), null)
        val commentForTest1 = Comment(101, "testGood")
        val expected = "testGood"

        service.add(postForComment)
        service.createComment(1, commentForTest1)

        val result = commentForTest1.text

        assertEquals(expected, result)
    }

    @Test
    fun add() {
        val service = WallService()
        service.add(Post(1, 1, 2, 0, "Привет", true, Likes(), null))
        assertEquals(1, service.counts())
    }

    @Test
    fun updateExisting() {
        val service = WallService()
        service.add(Post(1, 1, 2, 0, "Привет", true, Likes(), null))
        service.add(Post(2, 1, 2, 0, "Пока", true, Likes(), null))
        service.add(Post(3, 1, 2, 0, "Добрый день", true, Likes(), null))
        val update = Post(1, 1, 2, 0, "Привет1", true, Likes(), null)
        val result = service.update(update)
        assertTrue(result)
    }

    @Test
    fun updateNotExisting() {
        val service = WallService()
        service.add(Post(1, 1, 2, 0, "Привет", true, Likes(), null))
        service.add(Post(2, 1, 2, 0, "Пока", true, Likes(), null))
        service.add(Post(3, 1, 2, 0, "Добрый день", true, Likes(), null))
        val update = Post(4, 1, 2, 0, "Привет1", true, Likes(), null)
        val result = service.update(update)
        assertFalse(result)
    }
}


