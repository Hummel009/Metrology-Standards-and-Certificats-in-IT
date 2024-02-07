package com.github.hummel.msciit.lab2.lexer

class Real(private val float: Float) : Token(Tag.FLOAT.code) {
	override fun toString(): String = "$float"
}