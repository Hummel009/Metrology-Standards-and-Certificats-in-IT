package com.github.hummel.msciit.lab3.inter.boolean

import com.github.hummel.msciit.lab3.inter.Expression
import com.github.hummel.msciit.lab3.inter.Temp
import com.github.hummel.msciit.lab3.lexer.Token
import com.github.hummel.msciit.lab3.symbols_types.Type

open class Logical(token: Token, var expr1: Expression, var expr2: Expression) : Expression(token, null) {
	init {
		type = check(expr1.type!!, expr2.type!!)
		if (type == null) {
			error("type error")
		}
	}

	private fun check(t1: Type?, t2: Type?): Type? =
		if (t1 == Type.BOOLEAN && t2 == Type.BOOLEAN) Type.BOOLEAN else null

	override fun gen(): Expression {
		val f = newLabel()
		val a = newLabel()
		val temp = Temp(type)
		jumping(0, f)
		emit("$temp = true")
		emit("goto L$a")
		emitLabel(f)
		emit("$temp = false ")
		emitLabel(a)
		return temp
	}

	override fun toString(): String = "$expr1 $token $expr2"
}