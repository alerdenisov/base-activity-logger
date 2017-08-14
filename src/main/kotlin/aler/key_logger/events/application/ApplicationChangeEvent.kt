package aler.key_logger.events.application

import aler.key_logger.events.EventType
import aler.key_logger.events.LoggerData
import aler.key_logger.events.LoggerEvent

class ApplicationChangeEvent(val title : String, val process: String) : LoggerEvent, LoggerData {
    override fun getType(): EventType = EventType.Application
    override fun getData(): LoggerData = this
    override fun toJson(): String = """{ "event": "${getType()}", "code": "change", "data": { "title": "${title}", "process": "${process}" } }"""
}