package com.github.hummel.msciit.lab2.symbols_types

import com.github.hummel.msciit.lab2.lexer.Tag

class Array(var int: Int, var type: Type) : Type("[]", Tag.INDEX, int * type.width) {
	override fun toString(): String = "[$int] $type"
}