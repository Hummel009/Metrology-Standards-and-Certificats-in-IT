package hummel.inter.boolean

import hummel.inter.Expression
import hummel.lexer.Token

class And(token: Token, expr1: Expression, expr2: Expression) : Logical(token, expr1, expr2) {
	override fun jumping(t: Int, f: Int) {
		val label = if (f != 0) f else newLabel()
		expr1.jumping(0, label)
		expr2.jumping(t, f)
		if (f == 0) {
			emitLabel(label)
		}
	}
}