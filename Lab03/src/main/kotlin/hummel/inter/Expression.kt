package hummel.inter

import hummel.lexer.Token
import hummel.symbols_types.Type

open class Expression(var token: Token, var type: Type?) : Node() {
	open fun gen(): Expression {
		return this
	}

	open fun reduce(): Expression {
		return this
	}

	open fun jumping(t: Int, f: Int) {
		emitJumps(this.toString(), t, f)
	}

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

	override fun toString(): String {
		return token.toString()
	}
}