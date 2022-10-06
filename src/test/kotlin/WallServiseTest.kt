import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


internal class WallServiceTest {

    @Test
    fun add() {
        val service = WallService()
        service.add(Post(1, 1, 2, 0, "Привет", true, Likes()))
        assertEquals(1, service.counts())
    }

    @Test
    fun updateExisting() {
        val service = WallService()
        service.add(Post(1, 1, 2, 0, "Привет", true, Likes()))
        service.add(Post(2, 1, 2, 0, "Пока", true, Likes()))
        service.add(Post(3, 1, 2, 0, "Добрый день", true, Likes()))
        val update = Post(1, 1, 2, 0, "Привет1", true, Likes())
        val result = service.update(update)
        assertTrue(result)
    }

    @Test
    fun updateNotExisting() {
        val service = WallService()
        service.add(Post(1, 1, 2, 0, "Привет", true, Likes()))
        service.add(Post(2, 1, 2, 0, "Пока", true, Likes()))
        service.add(Post(3, 1, 2, 0, "Добрый день", true, Likes()))
        val update = Post(4, 1, 2, 0, "Привет1", true, Likes())
        val result = service.update(update)
        assertFalse(result)
    }
}


