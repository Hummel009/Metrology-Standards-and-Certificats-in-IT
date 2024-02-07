package com.github.hummel.msciit.lab2.inter.boolean

import com.github.hummel.msciit.lab2.inter.Expression
import com.github.hummel.msciit.lab2.lexer.Token

class Not(token: Token, expr: Expression) : Logical(token, expr, expr) {
	override fun jumping(t: Int, f: Int): Unit = expr2.jumping(f, t)

	override fun toString(): String = "$token $expr2"
}