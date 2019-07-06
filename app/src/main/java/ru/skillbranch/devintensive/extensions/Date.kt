package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.round

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time

    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val isBeforeNow = this.before(date)
    val diffTime = abs(this.time - date.time)

    val result = when {
        diffTime <= 1.07 * SECOND -> "только что"
        diffTime <= 45 * SECOND -> if (isBeforeNow) "несколько секунд назад" else "через несколько секунд"
        diffTime <= 75 * SECOND -> if (isBeforeNow) "минуту назад" else "через минуту"
        diffTime <= 45 * MINUTE -> {
            val minute = round(diffTime.toDouble() / MINUTE).toInt()
            val plurals = TimeUnits.MINUTE.plural(minute)
            if (isBeforeNow) "$plurals назад" else "через $plurals"
        }
        diffTime <= 75 * MINUTE -> if (isBeforeNow) "час назад" else "через час"
        diffTime <= 22 * HOUR -> {
            val hours = round(diffTime.toDouble() / HOUR).toInt()
            val plurals = TimeUnits.HOUR.plural(hours)
            if (isBeforeNow) "$plurals назад" else "через $plurals"
        }
        diffTime <= 26 * HOUR -> if (isBeforeNow) "день назад" else "через день"
        diffTime <= 360 * DAY -> {
            val days = round(diffTime.toDouble() / DAY).toInt()
            val plurals = TimeUnits.DAY.plural(days)
            if (isBeforeNow) "$plurals назад" else "через $plurals"
        }
        else -> if (isBeforeNow) "более года назад" else "более чем через год"
    }

    return result
}

enum class TimeUnits {
    SECOND, MINUTE, HOUR, DAY;

    fun plural(value: Int): String {
        return when {
            value % 10 == 1 && value / 10 != 1 -> {
                when (this) {
                    SECOND -> "$value секунду"
                    MINUTE -> "$value минуту"
                    HOUR -> "$value час"
                    DAY -> "$value день"
                }
            }
            value % 10 > 0 && value % 10 < 5 && value / 10 != 1 -> {
                when (this) {
                    SECOND -> "$value секунды"
                    MINUTE -> "$value минуты"
                    HOUR -> "$value часа"
                    DAY -> "$value дня"
                }
            }
            else -> {
                when (this) {
                    SECOND -> "$value секунд"
                    MINUTE -> "$value минут"
                    HOUR -> "$value часов"
                    DAY -> "$value дней"
                }
            }
        }
    }
}