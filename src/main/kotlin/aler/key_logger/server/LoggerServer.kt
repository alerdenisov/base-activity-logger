// Copyright (c) 2017 Aler Denisov <aler.zampillo@gmail.com>

// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:

// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.


package aler.key_logger.server

import aler.key_logger.hooks.LoggerEventsProvider

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer

import java.lang.Exception
import java.net.InetSocketAddress
import java.util.*


class LoggerServer : WebSocketServer {
    constructor(port : Int) : super(InetSocketAddress(port))
    constructor(address : InetSocketAddress) : super (address)

    val listeners = LinkedList<WebSocket>()
    val pusher = ServerPusher(1000L, this)
    val providers = LinkedList<LoggerEventsProvider>()

    fun AddProvider(provider : LoggerEventsProvider) {
        providers.add(provider)
    }

    override fun onOpen(connection: WebSocket?, p1: ClientHandshake?) {
        connection?.let {
            listeners.add(connection)
            println("open ${p1}");
        }
    }

    override fun onClose(connection: WebSocket?, p1: Int, p2: String?, p3: Boolean) {
        connection?.let {
            listeners.remove(connection)
            println("close ${p2}");
        }
    }

    override fun onMessage(connection: WebSocket?, message: String?) {
        connection?.let {
            when (message) {
                "pull" -> this.pullLogs(connection)
            }
        }
    }

    private fun pullLogs(connection: WebSocket) {
//        val sb = LinkedList<String>()
//
//        // TODO: prepare when received!
//        synchronized(events) {
//            val mouseMove = MouseMoveEvent(0)
//            val mouseScroll = MouseScrollEvent(0)
//            val keyPressZones = HashMap<KeyboardZoneType, KeyboardZoneActivityEvent>()
//            for (zone in KeyboardZoneType.values()) {
//                keyPressZones.put(zone, KeyboardZoneActivityEvent(zone, 0))
//            }
//
//            while(events.size > 0) {
//                val event = events.poll()
//                when (event) {
//                    is KeyboardReleaseEvent -> keyPressZones[event.keyCode.toZone()]!!.count++
//                    is MouseMoveEvent -> mouseMove.distance += event.distance
//                    is MouseScrollEvent -> mouseScroll.delta += event.delta
//                    else -> sb.add(event.getData().toJson())
//                }
//            }
//
//            sb.add(mouseMove.getData().toJson())
//            sb.add(mouseScroll.getData().toJson())
//
//            for((k, v) in keyPressZones) {
//                if(v.count > 0) {
//                    sb.add(v.toJson())
//                }
//            }
////            sb.appendln("${getActiveProccess()} ${getForegroundWindow()}")
//        }
//
//        connection.send("""
//{
//    "response": "events",
//    "status": "ok",
//    "data": [
//        ${sb.joinToString()}
//    ]
//}""")
    }

    override fun onStart() {
        pusher.start()
    }

    override fun onError(p0: WebSocket?, p1: Exception?) {
        println("err ${p1}");
    }

    fun SendToAll(message: String) {
        synchronized(listeners) {
            for ( receiver in listeners ) {
                receiver.send(message)
            }
        }
    }
}