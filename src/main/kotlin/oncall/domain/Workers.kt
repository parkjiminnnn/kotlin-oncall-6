package oncall.domain

class Workers(rawHolidayWorkers: String, rawWeekdayWorkers: String) {
    val weekdayWorkerNames = rawWeekdayWorkers.split(',')
    val holidayWorkerNames = rawHolidayWorkers.split(',')
}