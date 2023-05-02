
import java.io.File
import java.math.BigInteger

class Signer(
	private var inputPath: String,
	private var outputPath: String,
	private var q: BigInteger,
	private var p: BigInteger,
	private var h: BigInteger,
	private var k: BigInteger,
	private var m: BigInteger
) {

	fun ensign(arg: SignMode, x: BigInteger): CortegeFour {
		val msg: Any
		val hash: BigInteger
		when (arg) {
			SignMode.RUS -> {
				msg = File(inputPath).readText().uppercase()
				hash = Hash.getHashCharsRus(msg, m)
			}

			SignMode.ENG -> {
				msg = File(inputPath).readText().uppercase()
				hash = Hash.getHashCharsEng(msg, m)
			}

			SignMode.ASC -> {
				msg = File(inputPath).readText().uppercase()
				hash = Hash.getHashCharsAsc(msg, m)
			}

			SignMode.BIN -> {
				msg = File(inputPath).readBytes()
				hash = Hash.getHashBinary(msg, m)
			}
		}

		val g = h.modPow((p - BigInteger.ONE) / q, p)
		val y = g.modPow(x, p)
		val r = g.modPow(k, p).mod(q)
		val s = k.modPow(q - BigInteger.valueOf(2), q).multiply((hash + x * r)).mod(q)

		if (r != BigInteger.ZERO && s != BigInteger.ZERO) {
			when (arg) {
				SignMode.RUS, SignMode.ENG, SignMode.ASC -> {
					val sb = StringBuilder()
					sb.append(msg)
					sb.append(";;")
					sb.append("($r, $s)")
					File(outputPath).writeText(sb.toString())
				}

				SignMode.BIN -> {
					val msgBytes = msg as ByteArray
					val signBytes = ";;($r, $s)".toByteArray()
					val combinedBytes = msgBytes + signBytes
					File(outputPath).writeBytes(combinedBytes)
				}
			}
		}
		return CortegeFour(hash, r, s, y)
	}

	fun design(arg: SignMode, y: BigInteger): CortegeSeven {
		val msg: Any
		val hash: BigInteger
		val r: BigInteger
		val s: BigInteger

		when (arg) {
			SignMode.RUS -> {
				val fileContents = File(inputPath).readText().uppercase()
				val regex = Regex("""^(.*);;\((\d+), (\d+)\)$""")
				val matchResult = regex.find(fileContents)!!

				msg = matchResult.groupValues[1]
				r = matchResult.groupValues[2].toInt().toBigInteger()
				s = matchResult.groupValues[3].toInt().toBigInteger()

				hash = Hash.getHashCharsRus(msg, m)
			}

			SignMode.ENG -> {
				val fileContents = File(inputPath).readText().uppercase()
				val regex = Regex("""^(.*);;\((\d+), (\d+)\)$""")
				val matchResult = regex.find(fileContents)!!

				msg = matchResult.groupValues[1]
				r = matchResult.groupValues[2].toInt().toBigInteger()
				s = matchResult.groupValues[3].toInt().toBigInteger()

				hash = Hash.getHashCharsEng(msg, m)
			}

			SignMode.ASC -> {
				val fileContents = File(inputPath).readText().uppercase()
				val regex = Regex("""^(.*);;\((\d+), (\d+)\)$""")
				val matchResult = regex.find(fileContents)!!

				msg = matchResult.groupValues[1]
				r = matchResult.groupValues[2].toInt().toBigInteger()
				s = matchResult.groupValues[3].toInt().toBigInteger()

				hash = Hash.getHashCharsAsc(msg, m)
			}

			SignMode.BIN -> {
				val file = File(inputPath)
				val buffer = file.readBytes()
				val parts = String(buffer).split(";;")
				r = BigInteger(parts[1].substring(1, parts[1].indexOf(',')))
				s = BigInteger(parts[1].substring(parts[1].indexOf(',') + 2, parts[1].length - 1))

				val lastIndex = buffer.findLastIndex()
				msg = buffer.sliceArray(0 until lastIndex)

				hash = Hash.getHashBinary(msg, m)
			}
		}

		val g = h.modPow((p - BigInteger.ONE) / q, p)
		val w = s.modPow(q - 2.toBigInteger(), q)
		val u1 = hash * w % q
		val u2 = r * w % q
		val v = (g.pow(u1.toInt()).multiply(y.pow(u2.toInt())).mod(p)).mod(q)

		if (r == v) {
			when (arg) {
				SignMode.RUS, SignMode.ENG, SignMode.ASC -> {
					File(outputPath).writeText(msg as String)
				}

				SignMode.BIN -> {
					File(outputPath).writeBytes(msg as ByteArray)
				}
			}
		}
		return CortegeSeven(hash, r, s, w, u1, u2, v)
	}


	private fun ByteArray.findLastIndex(): Int {
		for (i in this.size - 2 downTo 0) {
			if (this[i] == 59.toByte() && this[i + 1] == 59.toByte()) {
				return i
			}
		}
		return this.size
	}
}