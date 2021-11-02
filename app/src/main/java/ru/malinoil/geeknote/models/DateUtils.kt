package ru.malinoil.geeknote.models

import java.util.*

class DateUtils {
    companion object {
        fun getStringDate(long: Long): String {
            val calendar = GregorianCalendar.getInstance()
            calendar.time = Date(long)
            return String.format(
                "%d.%d.%d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR)
            )
        }
    }
}