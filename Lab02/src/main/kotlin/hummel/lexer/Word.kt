package hummel.lexer

open class Word(var str: String, tag: Tag) : Token(tag.code) {
	override fun toString(): String = str

	companion object {
		val AND: Word = Word("&&", Tag.AND)
		val OR: Word = Word("||", Tag.OR)
		val EQUAL: Word = Word("==", Tag.EQUAL)
		val NOT_EQUAL: Word = Word("!=", Tag.NOT_EQUAL)
		val LOWER_EQUAL: Word = Word("<=", Tag.LOWER_EQUAL)
		val GREAT_EQUAL: Word = Word(">=", Tag.GREAT_EQUAL)
		val MINUS: Word = Word("minus", Tag.MINUS)
		val TRUE: Word = Word("true", Tag.TRUE)
		val FALSE: Word = Word("false", Tag.FALSE)
		val TEMP: Word = Word("temp", Tag.TEMP)
	}
}