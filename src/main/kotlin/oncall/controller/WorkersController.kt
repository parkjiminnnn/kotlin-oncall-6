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
        val monthAndStartDay = inputMonthAndStartDayMessage()
        val calendar = Calendar(monthAndStartDay)
        val rawWeekdayWorkers = inputWeekdayWorkersMessage()
        val rawHolidayWorkers = inputHolidayWorkersMessage()
        val workers = Workers(rawWeekdayWorkers, rawHolidayWorkers)
        val schedule = Schedule(calendar, workers)

        scheduleView(
            calendar.month,
            calendar.getUntilDays(),
            calendar.startDay,
            calendar.getPublicHolidays(),
            schedule.getTotalWorkers()
        )
    }
}