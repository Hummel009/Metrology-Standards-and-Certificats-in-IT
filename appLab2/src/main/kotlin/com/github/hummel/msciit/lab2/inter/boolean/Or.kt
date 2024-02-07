package com.github.hummel.msciit.lab2.inter.boolean

import com.github.hummel.msciit.lab2.inter.Expression
import com.github.hummel.msciit.lab2.lexer.Token

class Or(token: Token, expr1: Expression, expr2: Expression) : Logical(token, expr1, expr2) {
	override fun jumping(t: Int, f: Int) {
		val label = if (t != 0) t else newLabel()
		expr1.jumping(label, 0)
		expr2.jumping(t, f)
		if (t == 0) {
			emitLabel(label)
		}
	}
}