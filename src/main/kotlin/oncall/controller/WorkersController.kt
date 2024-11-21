package oncall.controller

import oncall.domain.Calendar
import oncall.domain.Schedule
import oncall.domain.Workers
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
        val monthAndStartDay = inputMonthAndStartDayMessage()
        val rawWeekdayWorkers = inputWeekdayWorkersMessage()
        val rawHolidayWorkers = inputHolidayWorkersMessage()

        val calendar = Calendar(monthAndStartDay)
        val workers = Workers(rawWeekdayWorkers, rawHolidayWorkers)
        return Pair(Calendar(monthAndStartDay), Schedule(calendar, workers))
    }
}
