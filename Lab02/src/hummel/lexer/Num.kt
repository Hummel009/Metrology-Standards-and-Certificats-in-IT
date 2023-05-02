package hummel.lexer

class Num(val int: Int) : Token(Tag.NUM.code) {
	override fun toString(): String {
		return int.toString()
	}
}