package hummel.inter.statements

class Sequence(private val state1: Statement, private val state2: Statement) : Statement() {
	override fun gen(b: Int, a: Int) {
		if (state1 == NULL) state2.gen(b, a) else if (state2 == NULL) state1.gen(b, a) else {
			val label = newLabel()
			state1.gen(b, label)
			emitLabel(label)
			state2.gen(label, a)
		}
	}
}