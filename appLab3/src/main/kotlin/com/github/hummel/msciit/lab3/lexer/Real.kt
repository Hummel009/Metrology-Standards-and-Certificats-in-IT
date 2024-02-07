package com.github.hummel.msciit.lab3.lexer

class Real(private val float: Float) : Token(Tag.FLOAT.code) {
	override fun toString(): String = "$float"
}