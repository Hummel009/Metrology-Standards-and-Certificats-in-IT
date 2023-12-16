package hummel.inter

import hummel.lexer.Token
import hummel.symbols_types.Type

class Arithmetic(token: Token, private var expr1: Expression, private var expr2: Expression) : Operation(token, null) {
	init {
		type = Type.max(expr1.type, expr2.type)
		if (type == null) {
			error("Type error")
		}
	}

	override fun gen(): Expression = Arithmetic(token, expr1.reduce(), expr2.reduce())

	override fun toString(): String = "$expr1 $token $expr2"
}
