package at.pkepp.puzzle21

class Button(val value: Char) {
    var left: Button? = null
    var right: Button? = null
    var up: Button? = null
    var down: Button? = null
}