package com.github.hummel.msciit.lab3.inter

import com.github.hummel.msciit.lab3.lexer.Num
import com.github.hummel.msciit.lab3.lexer.Token
import com.github.hummel.msciit.lab3.lexer.Word
import com.github.hummel.msciit.lab3.symbols_types.Type

class Constant(token: Token, type: Type?) : Expression(token, type) {
	constructor(value: Int) : this(Num(value), Type.INT)

	override fun jumping(t: Int, f: Int) {
		if (this == TRUE && t != 0) {
			emit("goto L$t")
		} else if (this == FALSE && f != 0) {
			emit("goto L$f")
		}
	}

	companion object {
		var TRUE: Constant = Constant(Word.TRUE, Type.BOOLEAN)
		var FALSE: Constant = Constant(Word.FALSE, Type.BOOLEAN)
	}
}