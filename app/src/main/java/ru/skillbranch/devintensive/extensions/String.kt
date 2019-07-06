package ru.skillbranch.devintensive.extensions

fun String.truncate(count: Int = 16):String {
    val trimmed = this.trim()
    return if (trimmed.length > count) {
        val substr = trimmed.substring(0..count-1)
        if (substr.last() == ' ') {
            substr.dropLast(1).plus("...")
        } else {
            substr.plus("...")
        }
    } else trimmed
}

fun String.stripHtml() = this.replace(Regex("<[^<]*?>|&#\\d+;"), "").replace(Regex("[^\\S\\r\\n]+"), " ")