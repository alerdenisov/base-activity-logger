package aler.key_logger.events.mouse

import aler.key_logger.events.EventType
import aler.key_logger.events.LoggerData
import aler.key_logger.events.LoggerEvent

class MouseScrollEvent(var delta: Int) : LoggerEvent, LoggerData {
    override fun toJson(): String = """{ "event": "${getType()}", "code": "scroll", "data": ${delta} }"""

    //    override fun toHex(): String = (toStringArray(//(toByteArray(
//            getType(),
//            MouseEventType.Scroll
//    ))
    override fun getType(): EventType = EventType.Mouse
    override fun getData(): LoggerData = this
}