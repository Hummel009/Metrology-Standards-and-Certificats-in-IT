package hummel.lexer

open class Word(var str: String, tag: Tag) : Token(tag.code) {
	override fun toString(): String {
		return str
	}

	companion object {
		val AND = Word("&&", Tag.AND)
		val OR = Word("||", Tag.OR)
		val EQUAL = Word("==", Tag.EQUAL)
		val NOT_EQUAL = Word("!=", Tag.NOT_EQUAL)
		val LOWER_EQUAL = Word("<=", Tag.LOWER_EQUAL)
		val GREAT_EQUAL = Word(">=", Tag.GREAT_EQUAL)
		val MINUS = Word("minus", Tag.MINUS)
		val TRUE = Word("true", Tag.TRUE)
		val FALSE = Word("false", Tag.FALSE)
		val TEMP = Word("temp", Tag.TEMP)
	}
}