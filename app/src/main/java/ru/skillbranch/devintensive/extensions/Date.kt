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
            val minute = round(diffTime.toDouble() / MINUTE).toLong()
            when (getSuffix(minute)) {
                NumberSuffix.ONE -> if (isBeforeNow) "${minute} минуту назад" else "через ${minute} минуту"
                NumberSuffix.PAIR -> if (isBeforeNow) "${minute} минуты назад" else "через ${minute} минуты"
                NumberSuffix.SEVERAL -> if (isBeforeNow) "${minute} минут назад" else "через ${minute} минут"
            }
        }
        diffTime <= 75 * MINUTE -> if (isBeforeNow) "час назад" else "через час"
        diffTime <= 22 * HOUR -> {
            val hours = round(diffTime.toDouble() / HOUR).toLong()
            when (getSuffix(hours)) {
                NumberSuffix.ONE -> if (isBeforeNow) "${hours} час назад" else "через ${hours} час"
                NumberSuffix.PAIR -> if (isBeforeNow) "${hours} часа назад" else "через ${hours} часа"
                NumberSuffix.SEVERAL -> if (isBeforeNow) "${hours} часов назад" else "через ${hours} часов"
            }
        }
        diffTime <= 26 * HOUR -> if (isBeforeNow) "день назад" else "через день"
        diffTime <= 360 * DAY -> {
            val days = round(diffTime.toDouble() / DAY).toLong()
            when (getSuffix(days)) {
                NumberSuffix.ONE -> if (isBeforeNow) "${days} день назад" else "через ${days} день"
                NumberSuffix.PAIR -> if (isBeforeNow) "${days} дня назад" else "через ${days} дня"
                NumberSuffix.SEVERAL -> if (isBeforeNow) "${days} дней назад" else "через ${days} дней"
            }
        }
        else -> if (isBeforeNow) "более года назад" else "более чем через год"
    }

    return result
}

private fun getSuffix(number: Long): NumberSuffix{
    val num = abs(number)
    return when {
        num % 10 == 1L && num / 10 != 1L -> NumberSuffix.ONE
        num % 10 > 0 && num % 10 < 5 && num / 10 != 1L -> NumberSuffix.PAIR
        else -> NumberSuffix.SEVERAL
    }
}

enum class NumberSuffix {
    ONE, PAIR, SEVERAL
}

enum class TimeUnits {
    SECOND, MINUTE, HOUR, DAY
}

fun main() {
    for (i in 0L .. 30) {
        println("$i ${getSuffix(i)}")
    }
}