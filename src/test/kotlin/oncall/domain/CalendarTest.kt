package oncall.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CalendarTest {
    private val calendar = Calendar("5,월")

    @Test
    fun `월 테스트`() {
        val excepted = 5
        val result = calendar.month

        assertEquals(excepted, result)
    }

    @Test
    fun `시작 요일 테스트`() {
        val excepted = "월"
        val result = calendar.startDay

        assertEquals(excepted, result)
    }

    @Test
    fun `월에 해당하는 날짜 테스트`() {
        val excepted = 31
        val result = calendar.getUntilDays()

        assertEquals(excepted, result)
    }

    @Test
    fun `공휴일 테스트`() {
        val excepted = listOf(5)
        val result = calendar.getPublicHolidays()

        assertEquals(excepted, result)
    }

    @Test
    fun `주말을 포함하는 공휴일 테스트`() {
        val excepted = listOf(5, 6, 7, 13, 14, 20, 21, 27, 28)
        val result = calendar.getFullHolidays()

        assertEquals(excepted, result)
    }

    @Test
    fun `평일 테스트`() {
        val excepted = listOf(1, 2, 3, 4, 8, 9, 10, 11, 12, 15, 16, 17, 18, 19, 22, 23, 24, 25, 26, 29, 30, 31)
        val result = calendar.getWeekdays()

        assertEquals(excepted, result)
    }
}
