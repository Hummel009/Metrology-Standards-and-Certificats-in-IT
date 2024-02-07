package com.github.hummel.msciit.lab3.inter.statements

import com.github.hummel.msciit.lab3.inter.Expression

class Puts(var expr: Expression?, private var str: String) : Statement() {
	override fun gen(b: Int, a: Int) {
		emit("gets ${expr ?: str}")
	}
}