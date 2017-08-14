package aler.key_logger.events

interface LoggerEvent {
    fun getType() : EventType
    fun getData() : LoggerData
}