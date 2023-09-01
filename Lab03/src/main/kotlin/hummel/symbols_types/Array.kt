package hummel.symbols_types

import hummel.lexer.Tag

class Array(var int: Int, var type: Type) : Type("[]", Tag.INDEX, int * type.width) {
	override fun toString(): String = "[$int] $type"
}