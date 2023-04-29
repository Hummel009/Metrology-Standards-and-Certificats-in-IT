package hummel.inter.statements

import hummel.inter.Expression

class Case(var expr: Expression, var state: Statement?) : Statement() {
	private var whens: MutableList<Statement> = ArrayList()

	fun add(state: Statement) {
		whens.add(state)
	}

	override fun gen(b: Int, a: Int) {
		emit("CASE $expr")
		var nextlabel = newLabel()
		for (i in whens.size - 1 downTo 1) {
			whens[i].gen(0, nextlabel)
			nextlabel = (whens[i] as When).nextL
		}
		if (state != null) {
			whens[0].gen(0, nextlabel)
			nextlabel = (whens[0] as When).nextL
			emitLabel(nextlabel)
			emit("ELSE NOT goto L$a")
			state!!.gen(0, a)
		} else {
			(whens[0] as When).gen2(0, nextlabel, a)
		}
	}
}