package com.sddev.weather.utils

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.Locale
import kotlin.math.abs

class DateUtils {

    fun formatTimeDifference(timestamp: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
        val currentTime = Calendar.getInstance().time
        val parsedTime = sdf.parse(timestamp)

        val timeDifferenceMillis = currentTime.time - parsedTime.time
        val timeDifferenceSeconds = abs(timeDifferenceMillis) / 1000
        val timeDifferenceMinutes = timeDifferenceSeconds / 60
        val timeDifferenceHours = timeDifferenceMinutes / 60
        val timeDifferenceDays = timeDifferenceHours / 24
        val timeDifferenceWeeks = timeDifferenceDays / 7
        val timeDifferenceMonths = timeDifferenceDays / 30

        return when {
            timeDifferenceMonths >= 1 -> {
                "$timeDifferenceMonths ${if (timeDifferenceMonths == 1L) "month" else "months"} ago"
            }
            timeDifferenceWeeks >= 1 -> {
                "$timeDifferenceWeeks ${if (timeDifferenceWeeks == 1L) "week" else "weeks"} ago"
            }
            timeDifferenceDays >= 1 -> {
                "$timeDifferenceDays ${if (timeDifferenceDays == 1L) "day" else "days"} ago"
            }
            timeDifferenceHours >= 1 -> {
                "$timeDifferenceHours ${if (timeDifferenceHours == 1L) "hour" else "hours"} ago"
            }
            timeDifferenceMinutes >= 1 -> {
                "$timeDifferenceMinutes ${if (timeDifferenceMinutes == 1L) "minute" else "minutes"} ago"
            }
            else -> {
                "$timeDifferenceSeconds ${if (timeDifferenceSeconds == 1L) "second" else "seconds"} ago"
            }
        }
    }
}