package com.github.hummel.msciit.lab2.lexer

class Num(val int: Int) : Token(Tag.NUM.code) {
	override fun toString(): String = "$int"
}