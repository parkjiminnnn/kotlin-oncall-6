package oncall.domain

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ScheduleTest {
    private val workers = Workers(
        "준팍,도밥,고니,수아,루루,글로,솔로스타,우코,슬링키,참새,도리",
        "수아,루루,글로,솔로스타,우코,슬링키,참새,도리,준팍,도밥,고니"
    )
    private val calendar = Calendar("5,월")
    private val schedule = Schedule(calendar, workers)

    @Test
    fun getTotalWorkers() {
        val excepted = listOf(
            "준팍", "도밥", "고니", "수아", "루루", "글로", "솔로스타", "루루", "글로", "솔로스타",
            "우코", "슬링키", "솔로스타", "우코", "참새", "도리", "준팍", "도밥", "고니", "슬링키",
            "참새", "수아", "루루", "글로", "솔로스타", "우코", "도리", "준팍", "슬링키", "참새", "도리"
        )
        val result = schedule.getTotalWorkers()

        assertEquals(excepted, result)
    }
}