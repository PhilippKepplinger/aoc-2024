package at.pkepp.puzzle21

class Numpad : Pad {
    val _A = Button('A')
    val _0 = Button('0')
    val _1 = Button('1')
    val _2 = Button('2')
    val _3 = Button('3')
    val _4 = Button('4')
    val _5 = Button('5')
    val _6 = Button('6')
    val _7 = Button('7')
    val _8 = Button('8')
    val _9 = Button('9')

    init {
        _A.left = _0
        _A.up = _3
        _0.up = _2
        _0.right = _A
        _1.up = _4
        _1.right = _2
        _2.left = _1
        _2.up = _5
        _2.right = _3
        _2.down = _0
        _3.left = _2
        _3.up = _6
        _3.down = _A
        _4.up = _7
        _4.right = _5
        _4.down = _1
        _5.left = _4
        _5.up = _8
        _5.right = _6
        _5.down = _2
        _6.left = _5
        _6.up = _9
        _6.down = _3
        _7.down = _4
        _7.right = _8
        _8.left = _7
        _8.down = _5
        _8.right = _9
        _9.left = _8
        _9.down = _6
    }

    override fun getButton(value: Char): Button {
        if (value == '0') return _0
        if (value == '1') return _1
        if (value == '2') return _2
        if (value == '3') return _3
        if (value == '4') return _4
        if (value == '5') return _5
        if (value == '6') return _6
        if (value == '7') return _7
        if (value == '8') return _8
        if (value == '9') return _9
        return _A
    }
}