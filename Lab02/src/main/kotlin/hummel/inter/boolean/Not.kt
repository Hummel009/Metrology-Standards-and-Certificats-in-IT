package hummel.inter.boolean

import hummel.inter.Expression
import hummel.lexer.Token

class Not(token: Token, expr: Expression) : Logical(token, expr, expr) {
	override fun jumping(t: Int, f: Int) {
		expr2.jumping(f, t)
	}

	override fun toString(): String {
		return "$token $expr2"
	}
}