package hummel

import hummel.inter.Id

enum class ChepinGroups {
	T, P, M, C, NULL
}

class Chepin {
	private val ids = HashMap<Id, ChepinGroups>()
	private val span = HashMap<Id, Int>()
	var groupTag = ChepinGroups.NULL
	val pBuffer = HashSet<Id>()
	var isP = false

	fun getQ(isIO: Boolean = false): Float {
		val p = countInDict(ChepinGroups.P, isIO)
		val m = countInDict(ChepinGroups.M, isIO)
		val c = countInDict(ChepinGroups.C, isIO)
		val t = countInDict(ChepinGroups.T, isIO)
		return p + (2 * m) + (3 * c) + (t.toFloat() / 2)
	}

	private fun countInDict(currentGroup: ChepinGroups, isIO: Boolean = false): Int {
		return if (isIO) {
			ids.count { pBuffer.contains(it.key) && it.value == currentGroup }
		} else {
			ids.count { it.value == currentGroup }
		}
	}

	fun printIOMetrics(): String {
		val st = StringBuilder()
		for ((id, value) in ids) {
			if (pBuffer.contains(id)) {
				st.appendLine("\$: $id -> $value")
			}
		}
		return st.toString()
	}

	fun tryAddToPBuffer(id: Id): Boolean {
		return if (isP) {
			pBuffer.add(id)
			true
		} else {
			false
		}
	}

	fun setIndex(id: Id, ind: ChepinGroups) {
		if (ind == ChepinGroups.P && pBuffer.contains(id)) {
			ids[id] = ChepinGroups.P
		} else if (!ids.containsKey(id) || ind > ids[id]!!) {
			ids[id] = ind
		}

		incSpan(id)
	}

	private fun incSpan(id: Id) {
		if (span.containsKey(id)) span[id] = span[id]!! + 1 else span[id] = 0
	}

	fun getIds(): Iterable<Map.Entry<Id, ChepinGroups>> {
		return ids.entries
	}

	fun getSpans(): Iterable<Map.Entry<Id, Int>> {
		return span.entries
	}
}