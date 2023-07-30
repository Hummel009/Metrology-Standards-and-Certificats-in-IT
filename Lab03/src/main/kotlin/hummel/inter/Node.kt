package hummel.inter

import hummel.lexer.Lexer

open class Node {
	private var lexerLine = 0

	init {
		lexerLine = Lexer.line
	}

	fun error(s: String) {
		println("Error near line $lexerLine: $s")
	}

	fun newLabel(): Int {
		return ++labels
	}

	fun emitLabel(i: Int) {
		println("L$i:")
	}

	fun emit(codeline: String) {
		println("\t" + codeline)
	}

	companion object {
		private var labels = 0
	}
}