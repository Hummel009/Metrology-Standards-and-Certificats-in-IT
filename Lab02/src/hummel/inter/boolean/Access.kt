package hummel.inter.boolean

import hummel.inter.Expression
import hummel.inter.Id
import hummel.inter.Operation
import hummel.lexer.Tag
import hummel.lexer.Word
import hummel.symbols_types.Type

class Access(var id: Id, var expr: Expression, type: Type?) : Operation(Word("[]", Tag.INDEX), type) {
	override fun gen(): Expression {
		return Access(id, expr.reduce(), type)
	}

	override fun jumping(t: Int, f: Int) {
		emitJumps(reduce().toString(), t, f)
	}

	override fun toString(): String {
		return "$id [ $expr ] "
	}
}