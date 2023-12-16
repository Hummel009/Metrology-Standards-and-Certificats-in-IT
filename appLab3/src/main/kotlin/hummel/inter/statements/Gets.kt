package hummel.inter.statements

import hummel.inter.Expression

class Gets(var expr: Expression?, private var str: String) : Statement() {
	override fun gen(b: Int, a: Int) {
		emit("gets ${expr ?: str}")
	}
}