package oncall.domain

class Schedule(private val calendar: Calendar, private val workers: Workers) {
    fun inputHolidayWorkers(): List<Pair<Int, String>> {
        val fullHolidays = calendar.getFullHolidays()
        val holidayWorkers = workers.holidayWorkerNames
        var index = 0

        val matchedWorkers = fullHolidays.map { fullHoliday ->
            val holidayWorker = holidayWorkers[index]
            index = (index + 1) % holidayWorkers.size
            Pair(fullHoliday, holidayWorker)
        }
        return matchedWorkers
    }

    fun inputWeekdayWorkers(): List<Pair<Int, String>> {
        val weekdays = calendar.getWeekdays()
        val weekdayWorkers = workers.weekdayWorkerNames
        var index = 0
        val matchedWorkers = weekdays.map { weekday ->
            val weekdayWorker = weekdayWorkers[index]
            index = (index + 1) % weekdayWorkers.size
            Pair(weekday, weekdayWorker)
        }
        return matchedWorkers
    }

    fun inputFirstWorkers(): MutableList<String> {
        val totalWorkers = MutableList(calendar.getUntilDays()) { "" }
        val weekdayWorkers = inputWeekdayWorkers()
        val holidayWorkers = inputHolidayWorkers()
        val isFirstWeekdayWorker = weekdayWorkers.find { it.first == 1 }
        if (isFirstWeekdayWorker != null) {
            totalWorkers[0] = isFirstWeekdayWorker.second
        } else {
            totalWorkers[0] = holidayWorkers.find { it.first == 1 }!!.second
        }

        return totalWorkers
    }

    fun getTotalWorkers(): List<String> {
        val totalWorkers = inputFirstWorkers()
        val weekdayWorkers = inputWeekdayWorkers()
        val holidayWorkers = inputHolidayWorkers()

        for (i in 1 until totalWorkers.size) {
            val matchedWeekdayWorker = weekdayWorkers.find { it.first == i + 1 }?.second
            val matchedHolidayWorker = holidayWorkers.find { it.first == i + 1 }?.second
            if (matchedHolidayWorker != null) {
                totalWorkers[i] = matchedHolidayWorker
                if (totalWorkers[i - 1] == totalWorkers[i]) {
                    val currentIndex = holidayWorkers.indexOfFirst { it.first == i + 1 }
                    val nextHolidayWorker = if (currentIndex != -1 && currentIndex + 1 < holidayWorkers.size) {
                        holidayWorkers[currentIndex + 1].second
                    } else {
                        ""
                    }
                    totalWorkers[i] = nextHolidayWorker
                }
            } else if (matchedWeekdayWorker != null) {
                totalWorkers[i] = matchedWeekdayWorker
                if (totalWorkers[i - 1] == totalWorkers[i]) {
                    val currentIndex = weekdayWorkers.indexOfFirst { it.first == i + 1 }
                    val nextWeekdayWorker = if (currentIndex != -1 && currentIndex + 1 < weekdayWorkers.size) {
                        weekdayWorkers[currentIndex + 1].second
                    } else {
                        ""
                    }
                    totalWorkers[i] = nextWeekdayWorker
                }
            }
        }
        return totalWorkers
    }
}
