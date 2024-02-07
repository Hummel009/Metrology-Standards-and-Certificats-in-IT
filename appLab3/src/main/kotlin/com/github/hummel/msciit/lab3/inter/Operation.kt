package com.github.hummel.msciit.lab3.inter

import com.github.hummel.msciit.lab3.lexer.Token
import com.github.hummel.msciit.lab3.symbols_types.Type

open class Operation(token: Token, type: Type?) : Expression(token, type) {
	override fun reduce(): Expression {
		val x = gen()
		val t = Temp(type)
		emit("$t = $x")
		return t
	}
}