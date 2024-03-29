package com.github.hummel.msciit.lab2.inter.statements

import com.github.hummel.msciit.lab2.inter.Expression
import com.github.hummel.msciit.lab2.inter.Id
import com.github.hummel.msciit.lab2.symbols_types.Type

class Set(private var id: Id, var expr: Expression) : Statement() {
	init {
		if (check(id.type!!, expr.type!!) == null) {
			error("type error")
		}
	}

	private fun check(type1: Type?, type2: Type?): Type? {
		return if (Type.isNumeric(type1) && Type.isNumeric(type2)) {
			type2
		} else if (type1 == Type.BOOLEAN && type2 == Type.BOOLEAN) {
			type2
		} else {
			null
		}
	}

	override fun gen(b: Int, a: Int): Unit = emit("$id" + " = " + expr.gen().toString())
}