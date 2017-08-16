package aler.key_logger.hooks.application

import aler.key_logger.events.LoggerEvent
import aler.key_logger.events.application.ApplicationChangeEvent
import aler.key_logger.hooks.LoggerEventsProvider
import com.sun.jna.Native
import com.sun.jna.Platform
import com.sun.jna.Pointer
import com.sun.jna.platform.unix.X11
import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.Tlhelp32
import com.sun.jna.platform.win32.User32
import com.sun.jna.ptr.IntByReference
import com.sun.jna.win32.StdCallLibrary
import java.util.*
import javax.script.ScriptEngineManager

class ApplicationHook : Thread(), LoggerEventsProvider {
    var activeApplication : ApplicationChangeEvent = ApplicationChangeEvent("n/a", "none")
    init {
        resetEvents()
        start()
    }

    override fun run() {
        while(true) {
            // Check active app in a cycle
//            sb.appendln("${getActiveProccess()} ${getForegroundWindow()}")
            synchronized(activeApplication) {
                activeApplication.process = getActiveProccess()
                activeApplication.title = getActiveProccessTitle()
            }

            sleep(100)
        }
    }

    private fun getActiveProccessTitle(): String {
        if(Platform.isWindows())
            return getActiveProccessTitleWin()

        if(Platform.isMac())
            return getActiveProccessTitleMac()

        if(Platform.isX11())
            return getActiveProccessTitleUnix()

        return "n/a"
    }

    interface XLib : StdCallLibrary {
        fun XGetInputFocus(display: X11.Display , focus_return: X11.Window , revert_to_return: Pointer) : Int

        companion object {
            val INSTANCE = Native.loadLibrary("XLib", XLib::class.java)
        }
    }

    private fun getActiveProccessTitleUnix(): String {
        val display = X11.INSTANCE.XOpenDisplay(null)
        val window = X11.Window()

        XLib.INSTANCE.XGetInputFocus(display, window, Pointer.NULL)

        val name = X11.XTextProperty()

        X11.INSTANCE.XGetWMName(display, window, name)

        return name.toString()
    }

    private fun  getActiveProccessTitleMac(): String {
        val script = """
tell application "System Events"
    name of application processes whose frontmost is true
end
""";

        val appleScript = ScriptEngineManager().getEngineByName("AppleScript")
        val result = appleScript.eval(script) as String

        return result
    }

    private fun getActiveProccessTitleWin(): String {
        val fgWindow = User32.INSTANCE.GetForegroundWindow();
        val titleLength = User32.INSTANCE.GetWindowTextLength(fgWindow) + 1;
        val title = CharArray(titleLength);

        User32.INSTANCE.GetWindowText(fgWindow, title, titleLength);

        return Native.toString(title);
    }

    private fun getActiveProccess(): String {
        if (Platform.isWindows())
            return getActiveProccessWin()
        if (Platform.isMac())
            return getActiveProccessMac()
        if (Platform.isLinux())
            return getActiveProccessLinux()

        return "none"
    }

    private fun getActiveProccessMac(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getActiveProccessLinux(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getActiveProccessWin(): String {
        val fgWindow = User32.INSTANCE.GetForegroundWindow();
        val pid : IntByReference = IntByReference()
        User32.INSTANCE.GetWindowThreadProcessId(fgWindow, pid)

        val kernel = Kernel32.INSTANCE
        val snapshot = kernel.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, null)
        val processEntry = Tlhelp32.PROCESSENTRY32.ByReference();

        if(kernel.Process32First(snapshot, processEntry)) {
            while(kernel.Process32Next(snapshot, processEntry)) {
                if(processEntry.th32ProcessID.toInt() == pid.value) {
                    return Native.toString(processEntry.szExeFile)
                }
            }
        }

        return "none";
    }

    override fun pullEvents(): LinkedList<LoggerEvent> {
        val result = LinkedList<LoggerEvent>()
        result.add(activeApplication)
        resetEvents()

        return result
    }

    private fun resetEvents() {
        activeApplication = ApplicationChangeEvent("n/a", "none")
    }
}