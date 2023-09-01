package hummel.lexer

class Real(private val float: Float) : Token(Tag.FLOAT.code) {
	override fun toString(): String = float.toString()
}