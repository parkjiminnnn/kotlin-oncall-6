package oncall.view

import oncall.utils.SevenDays

object OutputView {
    fun scheduleView(month: Int, days: Int, startDay: String, holidays: List<Int>, totalWorkers: List<String>) {
        val sevenDays = SevenDays.entries.toList()
        val startIndex = sevenDays.indexOfFirst { it.day == startDay }

        for ((workerIndex, day) in (1..days).withIndex()) {
            val currentDay = sevenDays[(startIndex + (day - 1)) % sevenDays.size]
            val currentWorker = totalWorkers[workerIndex % totalWorkers.size]

            changeToHoliday(month, day, holidays, currentDay, currentWorker)
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
