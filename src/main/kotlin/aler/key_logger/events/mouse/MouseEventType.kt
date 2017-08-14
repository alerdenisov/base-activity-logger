package aler.key_logger.events.mouse

enum class MouseEventType(val id : Byte)  {
    Move(0),
    Click(1),
    Scroll(2)
}