package oncall.view

import oncall.utils.SevenDays

object OutputView {
    fun scheduleView(month: Int, days: Int, startDay: String, holidays: List<Int>, totalWorkers: List<String>) {
        val sevenDays = SevenDays.entries.toList()
        val startIndex = sevenDays.indexOfFirst { it.day == startDay }
        var day = 1
        var workerIndex = 1
        while (true) {
            for (i in 0 until 7) {
                val currentDay = sevenDays[(startIndex + i) % 7]
                if (day > days) return
                val currentWorker = totalWorkers[workerIndex - 1]
                changeToHoliday(month, day, holidays, currentDay, currentWorker)
                day++
                workerIndex++
            }
        }
    }

    private fun changeToHoliday(
        month: Int,
        day: Int,
        holidays: List<Int>,
        currentDay: SevenDays,
        currentWorker: String
    ) {
        if (holidays.contains(day)) {
            return println("${month}월 ${day}일 ${currentDay.day}(휴일) $currentWorker")
        }
        return println("${month}월 ${day}일 ${currentDay.day} $currentWorker")
    }
}