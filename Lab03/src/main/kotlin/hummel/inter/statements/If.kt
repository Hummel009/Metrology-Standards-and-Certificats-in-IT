package hummel.inter.statements

import hummel.inter.Expression
import hummel.symbols_types.Type

class If(private val expr: Expression, private val state: Statement) : Statement() {
	init {
		if (expr.type != Type.BOOLEAN) {
			expr.error("boolean required in if")
		}
	}

	override fun gen(b: Int, a: Int) {
		val label = newLabel()
		expr.jumping(0, a)
		emitLabel(label)
		state.gen(label, a)
	}
}