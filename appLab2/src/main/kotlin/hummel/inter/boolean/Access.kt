package hummel.inter.boolean

import hummel.inter.Expression
import hummel.inter.Id
import hummel.inter.Operation
import hummel.lexer.Tag
import hummel.lexer.Word
import hummel.symbols_types.Type

class Access(private var id: Id, var expr: Expression, type: Type?) : Operation(Word("[]", Tag.INDEX), type) {
	override fun gen(): Expression = Access(id, expr.reduce(), type)

	override fun jumping(t: Int, f: Int): Unit = emitJumps("${reduce()}", t, f)

	override fun toString(): String = "$id [ $expr ] "
}