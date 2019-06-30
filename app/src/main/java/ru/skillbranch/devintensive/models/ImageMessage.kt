package ru.skillbranch.devintensive.models

import java.util.*

class ImageMessage(
    id: String,
    fromUser: User?,
    chat: Chat,
    isIncomming: Boolean = false,
    date: Date = Date(),
    var image: String?
) : BaseMessage(id, fromUser, chat, isIncomming, date) {
    override fun formatMessage(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}