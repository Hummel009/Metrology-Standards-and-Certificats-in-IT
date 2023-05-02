package hummel.inter.statements

import hummel.inter.Node
import java.util.*

open class Statement : Node() {
	var after = 0

	open fun gen(b: Int, a: Int) {}

	companion object {
		var NULL = Statement()
		var enclosing = NULL
	}
}