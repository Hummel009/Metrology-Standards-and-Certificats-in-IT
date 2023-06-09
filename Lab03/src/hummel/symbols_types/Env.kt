package hummel.symbols_types

import hummel.inter.Id
import hummel.lexer.Token
import java.util.*


open class Env(env: Env?) {
	private val table = Hashtable<Token, Id>()
	protected var prev: Env? = env

	fun put(token: Token, id: Id) {
		table[token] = id
	}

	open fun get(token: Token?): Id? {
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