package hummel.inter

import hummel.lexer.Token
import hummel.symbols_types.Type

class Unary(token: Token, var expr: Expression) : Operation(token, null) {
	init {
		type = Type.max(Type.INT, expr.type)
		if (type == null) {
			error("Type error")
		}
	}
}