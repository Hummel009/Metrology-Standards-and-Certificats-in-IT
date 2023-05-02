package hummel.inter.statements

import hummel.inter.Expression
import hummel.symbols_types.Type

class DoWhile : Statement() {
	private var expr: Expression? = null
	private var state: Statement? = null

	fun init(expr: Expression, state: Statement) {
		this.expr = expr
		this.state = state
		if (this.expr!!.type != Type.BOOLEAN) {
			this.expr!!.error("boolean required in while")
		}
	}

	override fun gen(b: Int, a: Int) {
		after = a
		val label = newLabel()
		state?.gen(b, label)
		emitLabel(label)
		expr?.jumping(b, 0)
	}
}