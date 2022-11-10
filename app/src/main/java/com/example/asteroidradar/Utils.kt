package com.example.asteroidradar

import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {
        fun formatDate(date: Date) : String {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return formatter.format(date)
        }

        fun addAWeekToDate(date: Date) : Date {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.DATE, 7)
            return calendar.time
        }
    }

}