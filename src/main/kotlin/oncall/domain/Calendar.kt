package oncall.domain

import oncall.utils.SevenDays

class Calendar(rawMonthAndStartDay: String) {
    private val monthAndStartDay = rawMonthAndStartDay.split(',')
    val month = monthAndStartDay[0].toInt()
    val startDay = monthAndStartDay[1]

    fun getUntilDays(): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            else -> 28
        }
    }

    fun getPublicHolidays(): List<Int> {
        val holidays = mutableListOf<Int>()
        when (month) {
            1, 3 -> holidays.add(1)
            5 -> holidays.add(5)
            6 -> holidays.add(6)
            8 -> holidays.add(15)
            10 -> {
                holidays.add(3)
                holidays.add(9)
            }

            12 -> holidays.add(25)
        }
        return holidays
    }

    private fun getWeekends(): List<Int> {
        val sevenDays = SevenDays.entries.toList()
        var saturdayIndex = sevenDays.size - (sevenDays.indexOfFirst { it.day == startDay } + 1)
        val weekend = mutableListOf<Int>()
        while (true) {
            if (saturdayIndex > getUntilDays()) break
            weekend.add(saturdayIndex)
            val sundayIndex = saturdayIndex + 1
            weekend.add(sundayIndex)
            saturdayIndex += 7
        }
        return weekend
    }

    fun getFullHolidays(): List<Int> {
        val fullHolidays = mutableSetOf<Int>()
        val publicHolidays = getPublicHolidays()
        val weekends = getWeekends()
        publicHolidays.forEach { publicHoliday ->
            fullHolidays.add(publicHoliday)
        }
        weekends.forEach { weekend ->
            fullHolidays.add(weekend)
        }
        return fullHolidays.toList().sorted()
    }

    fun getWeekdays(): List<Int> {
        val weekday = mutableListOf<Int>()
        val fullHoliday = getFullHolidays()
        for (i in 0 until getUntilDays()) {
            weekday.add(i + 1)
        }
        for (j in fullHoliday.indices) {
            if (weekday.contains(fullHoliday[j])) {
                weekday.remove(fullHoliday[j])
            }
        }
        return weekday
    }
}
