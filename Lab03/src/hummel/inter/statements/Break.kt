package hummel.inter.statements

class Break : Statement() {
	private var state: Statement? = enclosing

	override fun gen(b: Int, a: Int) {
		emit("goto L" + state?.after)
	}
}