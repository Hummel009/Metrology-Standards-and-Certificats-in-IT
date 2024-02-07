package com.github.hummel.msciit.lab2.inter

import com.github.hummel.msciit.lab2.lexer.Lexer

open class Node {
	private var lexerLine = 0

	init {
		lexerLine = Lexer.line
	}

	fun error(s: String): Unit = println("Error near line $lexerLine: $s")

	fun newLabel(): Int = ++labels

	fun emitLabel(i: Int): Unit = println("L$i:")

	fun emit(codeline: String): Unit = println("\t" + codeline)

	companion object {
		private var labels = 0
	}
}