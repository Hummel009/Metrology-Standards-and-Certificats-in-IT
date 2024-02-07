package com.github.hummel.msciit.lab2.inter

import com.github.hummel.msciit.lab2.lexer.Token
import com.github.hummel.msciit.lab2.symbols_types.Type

open class Operation(token: Token, type: Type?) : Expression(token, type) {
	override fun reduce(): Expression {
		val x = gen()
		val t = Temp(type)
		emit("$t = $x")
		return t
	}
}