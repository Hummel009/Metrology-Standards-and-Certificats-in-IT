package hummel.inter.statements

import hummel.inter.Node

open class Statement : Node() {
	var after: Int = 0

	open fun gen(b: Int, a: Int) {}

	companion object {
		var NULL: Statement = Statement()
		var enclosing: Statement = NULL
	}
}