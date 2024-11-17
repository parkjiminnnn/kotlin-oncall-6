package oncall.domain

class Calendar(rawMonthAndStartDay: String) {
    private val monthAndStartDay = rawMonthAndStartDay.split(',')
     val month = monthAndStartDay[0].toInt()
     val startDay = monthAndStartDay[1]

    fun days(): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            else -> 28
        }
    }

    fun holidays(): List<Int> {
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
}
