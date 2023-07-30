package hummel.inter.statements

import hummel.inter.Node

open class Statement : Node() {
	var after = 0

	open fun gen(b: Int, a: Int) {}

	companion object {
		var NULL = Statement()
		var enclosing = NULL
	}
}