package com.github.hummel.msciit.lab3.inter

import com.github.hummel.msciit.lab3.lexer.Word
import com.github.hummel.msciit.lab3.symbols_types.Type

class Temp(type: Type?) : Expression(Word.TEMP, type) {
	private var number = 0

	init {
		number = ++count
	}

	override fun toString(): String = "t$number"

	companion object {
		private var count = 0
	}
}