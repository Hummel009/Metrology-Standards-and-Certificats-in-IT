package com.github.hummel.msciit.lab3.lexer

open class Token(val tag: Int) {
	override fun toString(): String = tag.toChar().toString()
}