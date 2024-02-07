package com.github.hummel.msciit.lab2.inter.boolean

import com.github.hummel.msciit.lab2.inter.Expression
import com.github.hummel.msciit.lab2.inter.Id
import com.github.hummel.msciit.lab2.inter.Operation
import com.github.hummel.msciit.lab2.lexer.Tag
import com.github.hummel.msciit.lab2.lexer.Word
import com.github.hummel.msciit.lab2.symbols_types.Type

class Access(private var id: Id, var expr: Expression, type: Type?) : Operation(Word("[]", Tag.INDEX), type) {
	override fun gen(): Expression = Access(id, expr.reduce(), type)

	override fun jumping(t: Int, f: Int): Unit = emitJumps("${reduce()}", t, f)

	override fun toString(): String = "$id [ $expr ] "
}