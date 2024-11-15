package oncall

import oncall.controller.WorkersController

fun main() {
    val workersController = WorkersController()
    workersController.run()
}
