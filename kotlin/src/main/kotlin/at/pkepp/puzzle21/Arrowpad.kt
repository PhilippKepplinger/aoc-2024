package at.pkepp.puzzle21

class Arrowpad : Pad {
    val A = Button('A')
    val left = Button('<')
    val right = Button('<')
    val up = Button('^')
    val down = Button('v')

    init {
        A.left = up
        A.down = right
        up.right = A
        up.down = down
        left.right  = down
        down.left = left
        down.right = right
        down.up = up
        right.up = A
        right.left = down
    }

    override fun getButton(value: Char): Button {
        if (value == '<') return left
        if (value == '>') return right
        if (value == '^') return up
        if (value == 'v') return down
        return A
    }
}