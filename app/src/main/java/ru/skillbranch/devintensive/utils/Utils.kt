package ru.skillbranch.devintensive.utils

val translitMap = mapOf<String, String>(
    "а" to "a",
    "б" to "b",
    "в" to "v",
    "г" to "g",
    "д" to "d",
    "е" to "e",
    "ё" to "e",
    "ж" to "zh",
    "з" to "z",
    "и" to "i",
    "й" to "i",
    "к" to "k",
    "л" to "l",
    "м" to "m",
    "н" to "n",
    "о" to "o",
    "п" to "p",
    "р" to "r",
    "с" to "s",
    "т" to "t",
    "у" to "u",
    "ф" to "f",
    "х" to "h",
    "ц" to "c",
    "ч" to "ch",
    "ш" to "sh",
    "щ" to "sh'",
    "ъ" to "",
    "ы" to "i",
    "ь" to "",
    "э" to "e",
    "ю" to "yu",
    "я" to "ya"
)

object Utils {
    fun parseFullName(fullName: String?) = when (fullName) {
        null, "", " " -> null to null
        else -> {
            val parts: List<String>? = fullName.split(" ")
            val firstName = parts?.getOrNull(0)
            val lastName = parts?.getOrNull(1)
            firstName to lastName
        }
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstNameInitial = getInitial(firstName)
        val lastNameInitial = getInitial(lastName)

        return when {
            firstNameInitial == null && lastNameInitial == null -> null
            firstNameInitial == null -> lastNameInitial
            lastNameInitial == null -> firstNameInitial
            else -> "$firstNameInitial$lastNameInitial"
        }
    }

    fun transliteration(payload: String, divider: String = " ") = payload
        .replace(" ", divider)
        .map {
            if (it.isLowerCase()) {
                translitMap.getOrElse(it.toString(), { it.toString() })
            } else {
                translitMap.getOrElse(it.toString().toLowerCase(), { it.toString() }).toUpperCase()
            }
        }
        .joinToString("")

    private fun getInitial(name: String?) = when (name) {
        null, "", " " -> null
        else -> name[0].toString().toUpperCase()
    }
}