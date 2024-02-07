package com.github.hummel.msciit.lab2.inter.statements

import com.github.hummel.msciit.lab2.inter.Expression

class Case(var expr: Expression, var state: Statement?) : Statement() {
	private var whens: MutableList<Statement> = ArrayList()

	fun add(state: Statement): Boolean = whens.add(state)

	override fun gen(b: Int, a: Int) {
		emit("CASE $expr")
		var nextlabel = newLabel()
		for (i in whens.size - 1 downTo 1) {
			whens[i].gen(0, nextlabel)
			nextlabel = (whens[i] as When).nextL
		}
		state?.let {
			whens[0].gen(0, nextlabel)
			nextlabel = (whens[0] as When).nextL
			emitLabel(nextlabel)
			emit("ELSE NOT goto L$a")
			it.gen(0, a)
		} ?: run {
			(whens[0] as When).gen2(0, nextlabel, a)
		}
	}
}