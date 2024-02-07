package com.github.hummel.msciit.lab2.inter.statements

import com.github.hummel.msciit.lab2.inter.Node

open class Statement : Node() {
	var after: Int = 0

	open fun gen(b: Int, a: Int) {}

	companion object {
		var NULL: Statement = Statement()
		var enclosing: Statement = NULL
	}
}