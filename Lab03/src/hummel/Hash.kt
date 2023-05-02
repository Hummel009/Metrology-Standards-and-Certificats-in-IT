
import java.math.BigInteger

object Hash {
	private const val ALPHABET_ENG = " ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	private const val ALPHABET_RUS = " АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"

	fun getHashCharsEng(input: String, mod: BigInteger): BigInteger {
		var h = 100.toBigInteger()

		for (i in input.indices) {
			val pos = ALPHABET_ENG.indexOf(input[i]).toBigInteger()
			h = ((h + pos).pow(2)) % mod
		}

		return h
	}

	fun getHashCharsRus(input: String, mod: BigInteger): BigInteger {
		var h = 100.toBigInteger()

		for (i in input.indices) {
			val pos = ALPHABET_RUS.indexOf(input[i]).toBigInteger()
			h = ((h + pos).pow(2)) % mod
		}

		return h
	}

	fun getHashCharsAsc(input: String, mod: BigInteger): BigInteger {
		var h = 100.toBigInteger()

		for (i in input.indices) {
			val pos = input[i].code.toBigInteger()
			h = ((h + pos).pow(2)) % mod
		}

		return h
	}

	fun getHashBinary(input: ByteArray, mod: BigInteger): BigInteger {
		var h = 100.toBigInteger()

		for (i in input.indices) {
			val pos = input[i].toInt().toBigInteger()
			h = ((h + pos).pow(2)) % mod
		}

		return h
	}
}