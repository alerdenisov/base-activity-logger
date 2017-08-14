package aler.key_logger.hook

import aler.key_logger.events.keyboard.*
import aler.key_logger.events.LoggerEvent
import aler.key_logger.events.mouse.MouseClickEvent
import aler.key_logger.events.mouse.MouseMoveEvent
import aler.key_logger.events.mouse.MouseScrollEvent
import org.jnativehook.GlobalScreen

import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import org.jnativehook.mouse.NativeMouseEvent
import org.jnativehook.mouse.NativeMouseInputListener
import org.jnativehook.mouse.NativeMouseWheelEvent
import org.jnativehook.mouse.NativeMouseWheelListener
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger


class NativeHook : NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener, LoggerEventsProvider {
    var lastMoveX : Int = 0
    var lastMoveY : Int = 0
//    val listeners = LinkedList<LoggerEventsListener>()

    var moveEvent = MouseMoveEvent(0)
    var scrollEvent = MouseScrollEvent(0)
    var clickEvent = MouseClickEvent(0)
    var keyEvent = KeyboardZoneActivityEvent()

    init {
        GlobalScreen.registerNativeHook()
        GlobalScreen.addNativeKeyListener(this)
        GlobalScreen.addNativeMouseListener(this)
        GlobalScreen.addNativeMouseMotionListener(this)
        GlobalScreen.addNativeMouseWheelListener(this)

        // Get the logger for "org.jnativehook" and set the level to off.
        val logger = Logger.getLogger(GlobalScreen::class.java.`package`.name)
        logger.level = Level.OFF

        // Change the level for all handlers attached to the default logger.
        val handlers = Logger.getLogger("").handlers
        for (i in handlers.indices) {
            handlers[i].level = Level.OFF
        }

        resetEvents()
    }

    private fun resetEvents() {
        moveEvent = MouseMoveEvent(0)
        scrollEvent = MouseScrollEvent(0)
        clickEvent = MouseClickEvent(0)
        keyEvent = KeyboardZoneActivityEvent()
//        zoneEvents = HashMap<KeyboardZoneType, KeyboardZoneActivityEvent>()

//        for (zone in KeyboardZoneType.values()) {
//            zoneEvents.put(zone, KeyboardZoneActivityEvent(zone, 0))
//        }
    }

    override fun nativeMouseWheelMoved(event: NativeMouseWheelEvent?) {
        event?.let {
                scrollEvent.delta += Math.abs(event.wheelRotation)
        }
    }

    override fun pullEvents(): LinkedList<LoggerEvent> {
        val r = LinkedList<LoggerEvent>()
        synchronized(this) {
            r.add(moveEvent)
            r.add(scrollEvent)
            r.add(clickEvent)
//
//            for ((zone, event) in zoneEvents) {
//                r.add(event)
//            }

            r.add(keyEvent)

            // make new events to collect future data
            resetEvents()
        }

        return r
    }

    override fun nativeMouseDragged(event: NativeMouseEvent?) {
    }

    override fun nativeMouseReleased(event: NativeMouseEvent?) {
        event?.let {
            clickEvent.count++
        }
    }

    override fun nativeMouseClicked(event: NativeMouseEvent?) {
    }

    override fun nativeMouseMoved(event: NativeMouseEvent?) {
        event?.let {
            val difX = lastMoveX - event.x
            val difY = lastMoveY - event.y

            lastMoveX = event.x
            lastMoveY = event.y

            val distance = Math.max(Math.abs(difX), Math.abs(difY))
            moveEvent.distance += distance
        }
    }

    override fun nativeMousePressed(event: NativeMouseEvent?) {
    }

    override fun nativeKeyPressed(e: NativeKeyEvent) {
    }

    override fun nativeKeyReleased(e: NativeKeyEvent) {
        val zone = e.keyCode.toZone()
        keyEvent.counts[zone.ordinal]++
    }

    override fun nativeKeyTyped(e: NativeKeyEvent) {
    }
}