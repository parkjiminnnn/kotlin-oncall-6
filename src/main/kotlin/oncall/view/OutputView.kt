package oncall.view

object OutputView {
    fun scheduleView(month: Int, days: Int, startDay: String, holidays: List<Int>) {
        val sevenDays = SevenDays.entries.toList()
        val startIndex = sevenDays.indexOfFirst { it.day == startDay }
        var day = 1
        while (true) {
            for (i in 0 until 7) {
                val currentDay = sevenDays[(startIndex + i) % 7]
                if (day > days) return
                changeToHoliday(month, day, holidays, currentDay)
                day++
            }
        }
    }

    private fun changeToHoliday(month: Int, day: Int, holidays: List<Int>, currentDay: SevenDays) {
        if (holidays.contains(day)) {
            return println("${month}월 ${day}일 ${currentDay.day}(휴일)")
        }
        return println("${month}월 ${day}일 ${currentDay.day}")
    }
}