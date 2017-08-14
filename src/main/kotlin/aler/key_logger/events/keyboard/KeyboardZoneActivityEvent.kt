package aler.key_logger.events.keyboard

import aler.key_logger.events.EventType
import aler.key_logger.events.LoggerData
import aler.key_logger.events.LoggerEvent


class KeyboardZoneActivityEvent() : LoggerEvent, LoggerData {
    val counts = Array(KeyboardZoneType.values().size, { i -> 0 })

    override fun toJson(): String = """{ "event": "${getType()}", "code": "activity", "data": [ ${counts.joinToString()} ] }"""
//    override fun toHex(): String = (toStringArray(//(toByteArray(
//            getType(),
//            KeyboardEventType.Press,
//            keyZone,
//            count
//    ))

    override fun getType(): EventType = EventType.Keyboard
    override fun getData(): LoggerData = this
}