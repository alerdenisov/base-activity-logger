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

package aler.key_logger.events.keyboard

enum class KeyboardZoneType(val id: Byte) {
    Functions(0),

    Numbers(1),
    Arrows(2),
    Special(3),

    LeftPinky(10),
    LeftRing(11),
    LeftMiddle(12),
    LeftIndex(13),
    LeftThumb(14),

    RightPinky(20),
    RightRing(21),
    RightMiddle(22),
    RightIndex(23),
    RightThumb(24),

    Modificators(30),

    Unknown(Byte.MAX_VALUE)
}

fun Int.toZone(): KeyboardZoneType {
    return when (this) {
        0 -> KeyboardZoneType.Unknown
        1 -> KeyboardZoneType.Special
        in 2..11   -> KeyboardZoneType.Numbers
        in 12..15  -> KeyboardZoneType.Special
        16 -> KeyboardZoneType.LeftPinky  //"Q"
        17 -> KeyboardZoneType.LeftRing   //"W"
        18 -> KeyboardZoneType.LeftMiddle //"E"
        19 -> KeyboardZoneType.LeftIndex  //"R"
        20 -> KeyboardZoneType.LeftIndex  //"T"
        21 -> KeyboardZoneType.RightIndex //"Y"
        22 -> KeyboardZoneType.RightIndex //"U"
        23 -> KeyboardZoneType.RightMiddle//"I"
        24 -> KeyboardZoneType.RightRing  //"O"
        25 -> KeyboardZoneType.RightPinky //"P"
        26 -> KeyboardZoneType.RightPinky //return Toolkit.getProperty("AWT.openBracket", "Open Bracket")
        27 -> KeyboardZoneType.RightPinky //return Toolkit.getProperty("AWT.closeBracket", "Close Bracket")
        28 -> KeyboardZoneType.Special //return Toolkit.getProperty("AWT.enter", "Enter")
        29 -> KeyboardZoneType.Modificators //return Toolkit.getProperty("AWT.control", "Control")
        30 -> KeyboardZoneType.LeftPinky  //"A"
        31 -> KeyboardZoneType.LeftRing   //"S"
        32 -> KeyboardZoneType.LeftMiddle //"D"
        33 -> KeyboardZoneType.LeftIndex  //"F"
        34 -> KeyboardZoneType.LeftIndex  //"G"
        35 -> KeyboardZoneType.RightIndex //"H"
        36 -> KeyboardZoneType.RightIndex //"J"
        37 -> KeyboardZoneType.RightMiddle//"K"
        38 -> KeyboardZoneType.RightRing  //"L"
        39 -> KeyboardZoneType.RightPinky //return Toolkit.getProperty("AWT.semicolon", "Semicolon")
        40 -> KeyboardZoneType.RightPinky //return Toolkit.getProperty("AWT.quote", "Quote")
        41 -> KeyboardZoneType.RightPinky //return Toolkit.getProperty("AWT.backQuote", "Back Quote")
        42 -> KeyboardZoneType.Modificators //return Toolkit.getProperty("AWT.shift", "Shift")
        43 -> KeyboardZoneType.RightPinky //return Toolkit.getProperty("AWT.backSlash", "Back Slash")
        44 -> KeyboardZoneType.LeftPinky  //"Z"
        45 -> KeyboardZoneType.LeftRing   //"X"
        46 -> KeyboardZoneType.LeftMiddle //"C"
        47 -> KeyboardZoneType.LeftIndex  //"V"
        48 -> KeyboardZoneType.LeftIndex  //"B"
        49 -> KeyboardZoneType.RightIndex //"N"
        50 -> KeyboardZoneType.RightIndex //"M"
        51 -> KeyboardZoneType.RightMiddle//return Toolkit.getProperty("AWT.comma", "Comma")
        52 -> KeyboardZoneType.RightRing  //return Toolkit.getProperty("AWT.period", "Period")
        53 -> KeyboardZoneType.RightPinky //return Toolkit.getProperty("AWT.slash", "Slash")
        56 -> KeyboardZoneType.Modificators //return Toolkit.getProperty("AWT.alt", "Alt")
        57 -> KeyboardZoneType.Special //return Toolkit.getProperty("AWT.space", "Space")
        58 -> KeyboardZoneType.Modificators //return Toolkit.getProperty("AWT.capsLock", "Caps Lock")
        in 59..83 -> KeyboardZoneType.Numbers
        in 87..125 -> KeyboardZoneType.Functions // return Toolkit.getProperty("AWT.f11", "F11")
        in 3639..3677 -> KeyboardZoneType.Special
        else -> KeyboardZoneType.Unknown //return Toolkit.getProperty("AWT.unknown", "Unknown") + " keyCode: 0x" + Integer.toString(var0, 16)
    }
}