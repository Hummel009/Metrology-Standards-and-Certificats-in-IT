package hummel.inter

import hummel.lexer.Token
import hummel.symbols_types.Type

open class Operation(token: Token, type: Type?) : Expression(token, type) {
	override fun reduce(): Expression {
		val x = gen()
		val t = Temp(type)
		emit("$t = $x")
		return t
	}
}