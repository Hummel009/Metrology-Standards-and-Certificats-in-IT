package com.github.hummel.msciit.lab2.lexer

open class Token(val tag: Int) {
	override fun toString(): String = tag.toChar().toString()
}