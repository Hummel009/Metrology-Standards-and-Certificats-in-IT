package hummel.lexer

open class Token(val tag: Int) {
	override fun toString(): String = tag.toChar().toString()
}