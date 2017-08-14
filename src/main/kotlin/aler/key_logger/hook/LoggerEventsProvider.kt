package aler.key_logger.hook

import aler.key_logger.events.LoggerEvent
import java.util.*

interface LoggerEventsProvider {
    fun pullEvents() : LinkedList<LoggerEvent>
}