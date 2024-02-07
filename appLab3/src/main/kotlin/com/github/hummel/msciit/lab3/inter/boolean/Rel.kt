package com.github.hummel.msciit.lab3.inter.boolean

import com.github.hummel.msciit.lab3.inter.Expression
import com.github.hummel.msciit.lab3.lexer.Token
import com.github.hummel.msciit.lab3.symbols_types.Array
import com.github.hummel.msciit.lab3.symbols_types.Type

class Rel(token: Token, expr1: Expression, expr2: Expression) : Logical(token, expr1, expr2) {
	init {
		type = check(expr1.type!!, expr2.type!!)
		if (type == null) {
			error("type error")
		}
	}

	private fun check(t1: Type?, t2: Type?): Type? = if (t1 is Array || t2 is Array) null else if (isNumeric(t1) && isNumeric(t2)) Type.BOOLEAN else null

	private fun isNumeric(p: Type?): Boolean = p == Type.CHAR || p == Type.INT || p == Type.FLOAT

	override fun jumping(t: Int, f: Int) {
		val a = expr1.reduce()
		val b = expr2.reduce()
		val test = "$a $token $b"
		emitJumps(test, t, f)
	}
}