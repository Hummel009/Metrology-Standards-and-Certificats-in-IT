package hummel.inter.statements

import hummel.inter.Expression

class Puts(var expr: Expression?, private var str: String) : Statement() {
	override fun gen(b: Int, a: Int) {
		emit("gets ${expr ?: str}")
	}
}