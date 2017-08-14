package aler.key_logger.events.mouse

import aler.key_logger.events.EventType
import aler.key_logger.events.LoggerData
import aler.key_logger.events.LoggerEvent

class MouseMoveEvent(var distance: Int) : LoggerEvent, LoggerData {
    override fun toJson(): String = """{ "event": "${getType()}", "code": "move", "data": ${distance} }"""
//    override fun toJson(): String = "not yet"

    //    override fun toJson(): String = "MouseMove"(toStringArray(//(toByteArray(
//            getType(),
//            MouseEventType.Move,
//            distance
//    ))
    override fun getType(): EventType = EventType.Mouse
    override fun getData(): LoggerData = this
}