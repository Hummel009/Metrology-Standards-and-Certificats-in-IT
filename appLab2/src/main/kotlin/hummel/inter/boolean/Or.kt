package hummel.inter.boolean

import hummel.inter.Expression
import hummel.lexer.Token

class Or(token: Token, expr1: Expression, expr2: Expression) : Logical(token, expr1, expr2) {
	override fun jumping(t: Int, f: Int) {
		val label = if (t != 0) t else newLabel()
		expr1.jumping(label, 0)
		expr2.jumping(t, f)
		if (t == 0) {
			emitLabel(label)
		}
	}
}