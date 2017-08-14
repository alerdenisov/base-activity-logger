package aler.key_logger.events.mouse

import aler.key_logger.events.EventType
import aler.key_logger.events.LoggerData
import aler.key_logger.events.LoggerEvent

class MouseClickEvent(var count : Int) : LoggerEvent, LoggerData {
    override fun toJson(): String = """{ "event": "${getType()}", "code": "clicks", "data": ${count} }"""
    //    override fun toHex(): String = (toStringArray(//(toByteArray(
//            getType(),
//            MouseEventType.Click,
//            x,
//            y
//    ))
    override fun getType(): EventType = EventType.Mouse
    override fun getData(): LoggerData = this
}