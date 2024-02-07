package com.github.hummel.msciit.lab3.inter

import com.github.hummel.msciit.lab3.lexer.Token
import com.github.hummel.msciit.lab3.symbols_types.Type

open class Expression(var token: Token, var type: Type?) : Node() {
	open fun gen(): Expression = this

	open fun reduce(): Expression = this

	open fun jumping(t: Int, f: Int): Unit = emitJumps(this.toString(), t, f)

	fun emitJumps(test: String, t: Int, f: Int) {
		if (t != 0 && f != 0) {
			emit("if ($test) goto L$t")
			emit("goto L$f")
		} else if (t != 0) {
			emit("ifTrue ($test) goto L$t")
		} else if (f != 0) {
			emit("ifFalse ($test) goto L$f")
		}
	}

	override fun toString(): String = "$token"
}