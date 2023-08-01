package hummel.inter.statements

import hummel.inter.Expression

class When(var expr: Expression, private val state: Statement) : Statement() {
	var nextL: Int = 0

	override fun gen(b: Int, a: Int) {
		emitLabel(a)
		nextL = newLabel()
		emit("WHEN NOT $expr goto L$nextL")
		state.gen(0, nextL)
	}

	fun gen2(b: Int, a: Int, c: Int) {
		emitLabel(a)
		emit("WHEN NOT $expr goto L$c")
		state.gen(0, c)
	}
}