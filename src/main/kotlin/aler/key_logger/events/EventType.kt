package aler.key_logger.events

enum class EventType(val id : Byte) {
    Keyboard(0),
    Mouse(1),
    GRPS(2),
    Application(3)
}