package com.github.hummel.msciit.lab2.inter.statements

class Break : Statement() {
	private var state: Statement? = enclosing

	override fun gen(b: Int, a: Int): Unit = emit("goto L" + state?.after)
}