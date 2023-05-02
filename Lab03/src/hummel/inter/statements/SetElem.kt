package hummel.inter.statements

import hummel.inter.boolean.Access
import hummel.inter.Expression
import hummel.inter.Id
import hummel.symbols_types.Array
import hummel.symbols_types.Type


class SetElem(access: Access, var expr: Expression) : Statement() {
	private var array: Id
	private var index: Expression?

	init {
		array = access.id
		index = access.expr
		if (check(access.type!!, expr.type!!) == null) {
			error("type error")
		}
	}

	private fun check(type1: Type?, type2: Type?): Type? {
		return if (type1 is Array || type2 is Array) {
			null
		} else if (type1 === type2) {
			type2
		} else if (Type.isNumeric(type1) && Type.isNumeric(type2)) {
			type2
		} else {
			null
		}
	}

	override fun gen(b: Int, a: Int) {
		val s1 = index?.reduce().toString()
		val s2 = expr.reduce().toString()
		emit("$array [ $s1 ] = $s2")
	}
}