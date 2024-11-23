package oncall.utils

object Validate {

    fun isInt(rawMonth: String): Int {
        val month = rawMonth.toIntOrNull()
        if (month != null) return month
        throw IllegalArgumentException("[ERROR] 숫자를 입력해주세요.")
    }

    fun Int.validateMonth(): Int {
        require(this in 1..12) { "[ERROR] 1부터 12월 사이를 입력해주세요." }
        return this
    }

    fun String.validateStartDay(): String {
        val startDays = SevenDays.entries.map { it.day }
        require(startDays.contains(this)) { "[ERROR] 존재하지 않는 요일입니다." }
        require(this.isNotEmpty()) { "[ERROR] 요일을 입력해주세요." }
        return this
    }

    fun validateWorkers(rawWorkers: String) {
        val workerNames = rawWorkers.split(',')
        if (workerNames.toSet().size != workerNames.size)  throw IllegalArgumentException("[ERROR] 근무자명은 중복되지 않아야 합니다.")
        workerNames.forEach { workerName ->
            if (workerName.length > 5) throw IllegalArgumentException("[ERROR] 근무자명은 5자리 이하여야 합니다." )
            if (workerName.isEmpty()) throw IllegalArgumentException("[ERROR] 근무자명을 입력해주세요")
        }
    }
}