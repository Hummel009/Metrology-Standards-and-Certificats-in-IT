package com.github.hummel.msciit.lab2.inter

import com.github.hummel.msciit.lab2.lexer.Token
import com.github.hummel.msciit.lab2.symbols_types.Type

class Unary(token: Token, var expr: Expression) : Operation(token, null) {
	init {
		type = Type.max(Type.INT, expr.type)
		if (type == null) {
			error("type error")
		}
	}
}