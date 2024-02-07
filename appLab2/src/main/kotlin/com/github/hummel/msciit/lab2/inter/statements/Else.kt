package com.github.hummel.msciit.lab2.inter.statements

import com.github.hummel.msciit.lab2.inter.Expression
import com.github.hummel.msciit.lab2.symbols_types.Type

class Else(private val expr: Expression, private val state1: Statement, private val state2: Statement) : Statement() {
	init {
		if (expr.type != Type.BOOLEAN) {
			expr.error("boolean required in if")
		}
	}

	override fun gen(b: Int, a: Int) {
		val label1 = newLabel()
		val label2 = newLabel()
		expr.jumping(0, label2)
		emitLabel(label1)
		state1.gen(label1, a)
		emit("goto L$a")
		emitLabel(label2)
		state2.gen(label2, a)
	}
}