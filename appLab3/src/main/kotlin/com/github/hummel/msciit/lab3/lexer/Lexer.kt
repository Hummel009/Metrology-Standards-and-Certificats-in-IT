package com.github.hummel.msciit.lab3.lexer

import com.github.hummel.msciit.lab3.symbols_types.Type
import java.io.BufferedReader
import java.io.FileReader
import java.util.*

class Lexer(file: String) {
	private var peek = ' '
	private val words: Hashtable<String, Word> = Hashtable()
	private var reader: BufferedReader = BufferedReader(FileReader(file))

	private fun reserve(word: Word) {
		words[word.str] = word
	}

	init {
		reserve(Word("if", Tag.IF))
		reserve(Word("else", Tag.ELSE))
		reserve(Word("while", Tag.WHILE))
		reserve(Word("loop", Tag.LOOP))
		reserve(Word("do", Tag.BEGIN))
		reserve(Word("until", Tag.UNTIL))
		reserve(Word("break", Tag.BREAK))
		reserve(Word("case", Tag.CASE))
		reserve(Word("when", Tag.WHEN))
		reserve(Word("puts", Tag.PUTS))
		reserve(Word("gets", Tag.GETS))
		reserve(Word("and", Tag.AND))
		reserve(Word("or", Tag.OR))
		reserve(Word("not", Tag.NOT))
		reserve(Word("begin", Tag.BEGIN))
		reserve(Word("end", Tag.END))
		reserve(Word(";", Tag.OPERATOR_END))
		reserve(Word("\n", Tag.OPERATOR_END))
		reserve(Word.TRUE)
		reserve(Word.FALSE)
		reserve(Type.INT)
		reserve(Type.BOOLEAN)
		reserve(Type.CHAR)
		reserve(Type.FLOAT)
	}

	private fun readch() {
		try {
			peek = reader.read().toChar()
		} catch (e: Exception) {
			reader.close()
			println("Lexer error in file reading:" + e.message)
		}
	}

	private fun readch(char: Char): Boolean {
		readch()
		if (peek != char) {
			return false
		}
		peek = ' '
		return true
	}

	fun scan(): Token? {
		while (true) {
			if (peek == ' ' || peek == '\t' || peek == '\r') {
				readch()
				continue
			}
			break
		}
		when (peek) {
			'\n' -> {
				do {
					line++
				} while (readch('\n'))
				return Token(Tag.OPERATOR_END.code)
			}

			';' -> {
				readch()
				while (true) {
					if (peek == ' ' || peek == '\t' || peek == '\r') {
						readch()
						continue
					} else if (peek == '\n') {
						line++
					} else {
						break
					}
					readch()
				}
				return Token(Tag.OPERATOR_END.code)
			}

			else -> {}
		}
		when (peek) {
			'&' -> return if (readch('&')) Word.AND else Token('&'.code)
			'|' -> return if (readch('|')) Word.OR else Token('|'.code)
			'=' -> return if (readch('=')) Word.EQUAL else Token('='.code)
			'!' -> return if (readch('=')) Word.NOT_EQUAL else Token('!'.code)
			'<' -> return if (readch('=')) Word.LOWER_EQUAL else Token('<'.code)
			'>' -> return if (readch('=')) Word.GREAT_EQUAL else Token('>'.code)
			else -> {}
		}

		if (Character.isDigit(peek)) {
			var value = 0
			do {
				value = 10 * value + Character.getNumericValue(peek)
				readch()
			} while (Character.isDigit(peek))
			if (peek != '.') {
				return Num(value)
			}
			var float = value.toFloat()
			var divider = 10f
			while (true) {
				readch()
				if (!Character.isDigit(peek)) {
					break
				}
				float += Character.getNumericValue(peek).toByte() / divider
				divider *= 10f
			}
			return Real(float)
		}
		if (Character.isLetter(peek)) {
			val builder = StringBuilder()
			do {
				builder.append(peek)
				readch()
			} while (Character.isLetterOrDigit(peek))
			val terminal = "$builder"
			if (words.containsKey(terminal)) {
				return words[terminal]
			}
			val word = Word(terminal, Tag.ID)
			words[terminal] = word
			return word
		}

		val token = Token(peek.code)
		peek = ' '
		return token
	}

	companion object {
		var line = 1
	}
}