package oncall.domain

import oncall.utils.SevenDays
import oncall.utils.Validate.isInt
import oncall.utils.Validate.validateMonth
import oncall.utils.Validate.validateStartDay

class Calendar(rawMonthAndStartDay: String) {
    private val monthAndStartDay = rawMonthAndStartDay.split(',')
    val month = isInt(monthAndStartDay[0]).validateMonth()
    val startDay = monthAndStartDay[1].validateStartDay()

    fun getUntilDays(): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            else -> 28
        }
    }

    fun getPublicHolidays(): List<Int> {
        return when (month) {
            1, 3 -> listOf(1)
            5 -> listOf(5)
            6 -> listOf(6)
            8 -> listOf(15)
            10 -> listOf(3, 9)
            12 -> listOf(25)
            else -> listOf()
        }
    }

    private fun getWeekends(): List<Int> {
        val sevenDays = SevenDays.entries.toList()
        var saturdayIndex = sevenDays.size - (sevenDays.indexOfFirst { it.day == startDay } + 1)
        val weekend = mutableListOf<Int>()

        while (saturdayIndex <= getUntilDays()) {
            addSaturdays(saturdayIndex, weekend)
            addSundays(saturdayIndex, weekend)
            saturdayIndex += sevenDays.size
        }
        return weekend
    }

    private fun addSaturdays(saturdayIndex: Int, weekend: MutableList<Int>): Boolean {
        return weekend.add(saturdayIndex)
    }

    private fun addSundays(saturdayIndex: Int, weekend: MutableList<Int>): Boolean {
        val sundayIndex = saturdayIndex + 1
        return weekend.add(sundayIndex)
    }

    private fun addPublicHolidays(): MutableSet<Int> {
        val fullHolidays = mutableSetOf<Int>()
        val publicHolidays = getPublicHolidays()

        publicHolidays.forEach { publicHoliday ->
            fullHolidays.add(publicHoliday)
        }
        return fullHolidays
    }

    private fun addWeekends(): MutableSet<Int> {
        val fullHolidays = mutableSetOf<Int>()
        val weekends = getWeekends()

        weekends.forEach { weekend ->
            fullHolidays.add(weekend)
        }
        return fullHolidays
    }

    fun getFullHolidays(): List<Int> {
        return (addPublicHolidays() + addWeekends()).toList().sorted()
    }

    fun getWeekdays(): List<Int> {
        val fullHolidays = getFullHolidays()
        val weekday = MutableList(getUntilDays()) { it + 1 }
        return removeHolidays(fullHolidays, weekday)
    }

    private fun removeHolidays(fullHolidays: List<Int>, weekday: MutableList<Int>): MutableList<Int> {
        fullHolidays.forEach { fullHoliday ->
            if (weekday.contains(fullHoliday)) weekday.remove(fullHoliday)
        }
        return weekday
    }
}
