package oncall.controller

import oncall.domain.Calendar
import oncall.domain.Schedule
import oncall.domain.Workers
import oncall.utils.Validate.validateWorkers
import oncall.view.InputView.inputHolidayWorkersMessage
import oncall.view.InputView.inputMonthAndStartDayMessage
import oncall.view.InputView.inputWeekdayWorkersMessage
import oncall.view.OutputView.scheduleView

class WorkersController {
    fun run() {
        val input = input()
        val calendar = input.first
        val schedule = input.second
        scheduleView(
            calendar.month,
            calendar.getUntilDays(),
            calendar.startDay,
            calendar.getPublicHolidays(),
            schedule.getTotalWorkers()
        )
    }

    private fun input(): Pair<Calendar, Schedule> {
        val calendar = validCalendar()
        val workers = validWorkers()
        return Pair(calendar, Schedule(calendar, workers))
    }

    private fun validWorkers(): Workers {
        while (true) {
            try {
                val rawWeekdayWorkers = inputWeekdayWorkersMessage()
                validateWorkers(rawWeekdayWorkers)
                val rawHolidayWorkers = inputHolidayWorkersMessage()
                validateWorkers(rawHolidayWorkers)
                return Workers(rawWeekdayWorkers, rawHolidayWorkers)
            } catch (e: IllegalArgumentException) {
                println(e)
            }
        }
    }

    private fun validCalendar(): Calendar {
        while (true) {
            try {
                val monthAndStartDay = inputMonthAndStartDayMessage()
                return Calendar(monthAndStartDay)
            } catch (e: IllegalArgumentException) {
                println(e)
            }
        }
    }
}
