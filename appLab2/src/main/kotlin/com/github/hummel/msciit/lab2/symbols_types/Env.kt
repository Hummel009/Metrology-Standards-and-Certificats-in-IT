package com.github.hummel.msciit.lab2.symbols_types

import com.github.hummel.msciit.lab2.inter.Id
import com.github.hummel.msciit.lab2.lexer.Token
import java.util.*


open class Env(env: Env?) {
	private val table = Hashtable<Token, Id>()
	private var prev: Env? = env

	fun put(token: Token, id: Id) {
		table[token] = id
	}

	open operator fun get(token: Token?): Id? {
		var e: Env? = this
		while (e != null) {
			if (e.table.containsKey(token)) {
				return e.table[token]
			}
			e = e.prev
		}
		return null
	}
}