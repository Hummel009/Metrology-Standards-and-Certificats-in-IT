package hummel.symbols_types

import hummel.lexer.Tag
import hummel.lexer.Word

open class Type(str: String, tag: Tag, var width: Int) : Word(str, tag) {
	companion object {
		var INT = Type("int", Tag.BASIC, 4)
		var FLOAT = Type("float", Tag.BASIC, 8)
		var CHAR = Type("char", Tag.BASIC, 1)
		var BOOLEAN = Type("bool", Tag.BASIC, 1)

		fun isNumeric(p: Type?): Boolean {
			return p == CHAR || p == INT || p == FLOAT
		}

		fun max(p1: Type?, p2: Type?): Type? {
			return if (!isNumeric(p1) || !isNumeric(p2)) {
				null
			} else if (p1 == FLOAT || p2 == FLOAT) {
				FLOAT
			} else if (p1 == INT || p2 == INT) {
				INT
			} else {
				CHAR
			}
		}
	}
}