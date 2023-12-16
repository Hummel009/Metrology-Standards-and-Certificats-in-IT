package hummel.inter.statements

import hummel.inter.Expression
import hummel.symbols_types.Type

class While : Statement() {
	private var expr: Expression? = null
	private var state: Statement? = null

	fun init(expr: Expression, state: Statement) {
		this.expr = expr
		this.state = state
		if ((this.expr ?: return).type !== Type.BOOLEAN) {
			(this.expr ?: return).error("boolean required in while expression")
		}
	}

	override fun gen(b: Int, a: Int) {
		after = a
		expr?.jumping(0, a)
		val label = newLabel()
		emitLabel(label)
		state?.gen(label, b)
		emit("goto L$b")
	}
}