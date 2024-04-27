package com.github.hummel.msciit.lab2.inter.statements

import com.github.hummel.msciit.lab2.inter.Expression

class When(var expr: Expression, private val state: Statement) : Statement() {
	var nextL: Int = 0

	override fun gen(b: Int, a: Int) {
		emitLabel(a)
		nextL = newLabel()
		emit("WHEN NOT $expr goto L$nextL")
		state.gen(0, nextL)
	}

	@Suppress("UNUSED_PARAMETER")
	fun gen2(a: Int, c: Int) {
		emitLabel(a)
		emit("WHEN NOT $expr goto L$c")
		state.gen(0, c)
	}
}