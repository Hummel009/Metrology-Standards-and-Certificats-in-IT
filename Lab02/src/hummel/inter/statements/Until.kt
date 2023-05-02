package hummel.inter.statements

import hummel.inter.Expression

class Until : Statement() {
	private var expr: Expression? = null
	private var state: Statement? = null

	fun init(expr: Expression, state: Statement) {
		this.expr = expr
		this.state = state
	}

	override fun gen(b: Int, a: Int) {
		after = a
		val label = newLabel()
		expr?.jumping(a, 0)
		emitLabel(label)
		state?.gen(b, label)
		emit("goto L$b")
	}
}
