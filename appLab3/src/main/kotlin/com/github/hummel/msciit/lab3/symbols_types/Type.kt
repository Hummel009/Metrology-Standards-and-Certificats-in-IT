package com.github.hummel.msciit.lab3.symbols_types

import com.github.hummel.msciit.lab3.lexer.Tag
import com.github.hummel.msciit.lab3.lexer.Word

open class Type(str: String, tag: Tag, var width: Int) : Word(str, tag) {
	companion object {
		var INT: Type = Type("int", Tag.BASIC, 4)
		var FLOAT: Type = Type("float", Tag.BASIC, 8)
		var CHAR: Type = Type("char", Tag.BASIC, 1)
		var BOOLEAN: Type = Type("bool", Tag.BASIC, 1)

		fun isNumeric(p: Type?): Boolean = p == CHAR || p == INT || p == FLOAT

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