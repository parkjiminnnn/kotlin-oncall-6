package oncall.domain

class Workers(rawWeekdayWorkers: String, rawHolidayWorkers: String) {
    val weekdayWorkerNames = rawWeekdayWorkers.split(',')
    val holidayWorkerNames = rawHolidayWorkers.split(',')
}