
import java.math.BigInteger

object ValuesChecker {
	fun checkQ(q: BigInteger) {
		if (!q.isProbablePrime(95)) {
			throw Exception()
		}
	}

	fun checkP(p: BigInteger, q: BigInteger) {
		if (!p.isProbablePrime(95)) {
			throw Exception()
		}

		if ((p - BigInteger.ONE) % q != BigInteger.ZERO) {
			throw Exception()
		}
	}

	fun checkH(q: BigInteger, p: BigInteger, h: BigInteger) {
		if (h < 2.toBigInteger() || h > (p - 2.toBigInteger())) {
			throw Exception()
		}
		val g = h.modPow((p - BigInteger.ONE) / q, p)

		if (g <= BigInteger.ONE) {
			throw Exception()
		}
	}

	fun checkInterval(leftBound: BigInteger, rightBound: BigInteger, value: BigInteger) {
		if (value < leftBound || value > rightBound) {
			throw Exception()
		}
	}
}