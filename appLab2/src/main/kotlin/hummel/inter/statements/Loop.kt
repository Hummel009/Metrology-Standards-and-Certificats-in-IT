package hummel.inter.statements

class Loop : Statement() {
	private var state: Statement? = null

	fun init(state: Statement) {
		this.state = state
	}

	override fun gen(b: Int, a: Int) {
		after = a
		state?.gen(a, b)
		emit("goto L$b")
	}
}