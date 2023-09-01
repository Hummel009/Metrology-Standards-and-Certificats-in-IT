package hummel.inter.boolean

import hummel.inter.Expression
import hummel.inter.Temp
import hummel.lexer.Token
import hummel.symbols_types.Type

open class Logical(token: Token, var expr1: Expression, var expr2: Expression) : Expression(token, null) {
	init {
		type = check(expr1.type!!, expr2.type!!)
		if (type == null) {
			error("type error")
		}
	}

	private fun check(t1: Type?, t2: Type?): Type? {
		return if (t1 == Type.BOOLEAN && t2 == Type.BOOLEAN) Type.BOOLEAN else null
	}

	override fun gen(): Expression {
		val f = newLabel()
		val a = newLabel()
		val temp = Temp(type)
		jumping(0, f)
		emit("$temp = true")
		emit("goto L$a")
		emitLabel(f)
		emit("$temp = false ")
		emitLabel(a)
		return temp
	}

	override fun toString(): String = "$expr1 $token $expr2"
}