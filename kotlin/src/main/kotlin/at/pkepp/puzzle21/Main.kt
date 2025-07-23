package at.pkepp.puzzle21

fun main() {
    val codes = InputParser("21", false).parseFile()

    val codeDecoder = CodeDecoder(2)
    val totalComplexity = codeDecoder.decode(codes)
}