package com.github.hummel.msciit.lab3.inter.boolean

import com.github.hummel.msciit.lab3.inter.Expression
import com.github.hummel.msciit.lab3.lexer.Token

class Not(token: Token, expr: Expression) : Logical(token, expr, expr) {
	override fun jumping(t: Int, f: Int): Unit = expr2.jumping(f, t)

	override fun toString(): String = "$token $expr2"
}