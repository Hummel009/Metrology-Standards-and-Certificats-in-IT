package hummel

import java.util.*

class Jilbe {
	private var maxNest = 0
	var nest: Int = 0
	var savedNest: Stack<Int> = Stack<Int>()
	var operatorAmount: Int = 0
	var conditionAmount: Int = 0

	fun getMaxNest(): Int {
		return maxNest - 1
	}

	fun checkMax(n: Int) {
		if (maxNest < n) {
			maxNest = n
		}
	}

	fun getConditionSaturation(): Float {
		return if (operatorAmount != 0) {
			conditionAmount.toFloat() / operatorAmount.toFloat()
		} else {
			-1f
		}
	}
}