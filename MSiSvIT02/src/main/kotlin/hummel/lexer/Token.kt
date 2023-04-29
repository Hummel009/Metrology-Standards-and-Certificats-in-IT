package hummel.lexer

open class Token(val tag: Int) {
	override fun toString(): String {
		return tag.toChar().toString()
	}
}