package hummel.inter.statements

import hummel.inter.Expression

class Gets(var expr: Expression?, private var str: String) : Statement() {
	override fun gen(b: Int, a: Int) {
		if (expr != null) emit("gets $expr") else emit("gets $str")
	}
}