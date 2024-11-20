package oncall.domain

class Schedule(private val calendar: Calendar, private val workers: Workers) {
    private val weekdayWorkers = getMatchedWorkers(calendar.getWeekdays(), workers.weekdayWorkerNames)
    private val holidayWorkers = getMatchedWorkers(calendar.getFullHolidays(), workers.holidayWorkerNames)

    private fun getMatchedWorkers(days: List<Int>, workers: List<String>): List<Pair<Int, String>> {
        var index = 0
        val matchedWorkers = days.map { day ->
            val worker = workers[index]
            index = (index + 1) % workers.size
            Pair(day, worker)
        }
        return matchedWorkers
    }

    private fun inputFirstWorker(): MutableList<String> {
        val totalWorkers = MutableList(calendar.getUntilDays()) { "" }
        val firstWeekdayWorker = weekdayWorkers.find { it.first == 1 }
        if (firstWeekdayWorker != null) {
            totalWorkers[0] = firstWeekdayWorker.second
        } else totalWorkers[0] = holidayWorkers.find { it.first == 1 }!!.second
        return totalWorkers
    }

    private fun changeToOrder(
        totalWorkers: MutableList<String>,
        workers: List<Pair<Int, String>>,
        i: Int
    ): MutableList<String> {
        val matchedWorker = workers.find { it.first == i + 1 }?.second
        if (matchedWorker != null) {
            totalWorkers[i] = matchedWorker
            totalWorkers[i] = isContinueWorker(totalWorkers, workers, i)
        }
        return totalWorkers
    }

    private fun getNextWorker(currentIndex: Int, workers: List<Pair<Int, String>>): String {
        if (currentIndex != -1 && currentIndex + 1 < workers.size) {
            return workers[currentIndex + 1].second
        }
        return ""
    }

    private fun isContinueWorker(
        totalWorkers: MutableList<String>,
        workers: List<Pair<Int, String>>,
        i: Int
    ): String {
        if (totalWorkers[i - 1] == totalWorkers[i]) {
            val currentIndex = workers.indexOfFirst { it.first == i + 1 }
            val nextWorker = getNextWorker(currentIndex, workers)
            totalWorkers[i] = nextWorker
        }
        return totalWorkers[i]
    }

    fun getTotalWorkers(): List<String> {
        var totalWorkers = inputFirstWorker()

        for (i in 1 until totalWorkers.size) {
            totalWorkers = changeToOrder(totalWorkers, holidayWorkers, i)
            totalWorkers = changeToOrder(totalWorkers, weekdayWorkers, i)
        }
        return totalWorkers
    }
}
