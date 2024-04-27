package com.github.hummel.msciit.lab1

import java.io.File
import kotlin.math.log2

class Halstead(private var inputPath: String, private var outputPath: String) {
	private var operators = hashMapOf(
		"puts" to 0,
		"[" to 0,
		"]" to 0,
		"." to 0,
		" " to 0,
		"\t" to 0,
		"\n" to 0,
		"\r" to 0,
		"+" to 0,
		"-" to 0,
		"*" to 0,
		"=" to 0,
		"/" to 0,
		"," to 0,
		"(" to 0,
		")" to 0,
		"for" to 0,
		"loop" to 0,
		"while" to 0,
		"do" to 0,
		"else" to 0,
		"elsif" to 0,
		"==" to 0,
		">" to 0,
		"<" to 0,
		"%" to 0,
		"return" to 0,
		">=" to 0,
		"<=" to 0,
		"end" to 0,
		"begin" to 0,
		"case" to 0,
		"when" to 0,
		"gets" to 0,
		"!" to 0,
		"break" to 0,
		"def" to 0,
		"if" to 0,
		".." to 0
	)

	private var operands = hashMapOf<String, Int>()
	private var ignore = hashSetOf("\r", "do", "end", "\n", "else", "elsif", "when", "in", ")", "]", "\t", " ")

	fun calculate() {
		val code = File(inputPath).readText()
		var prevToken: String
		var currToken = ""
		var dot = false
		val functions = mutableMapOf<String, Boolean>()
		for (symbol in code) {
			prevToken = currToken
			currToken += symbol
			if (operators.containsKey(currToken)) {
				continue
			} else if (operators.containsKey(prevToken)) {
				if (prevToken == ".") {
					dot = true
				}
				if (functions.containsKey(prevToken)) {
					operators["("] = (operators["("] ?: 0) - 1
				}
				operators[prevToken] = (operators[prevToken] ?: 0) + 1
				currToken = "$symbol"
				if (prevToken != ".") {
					dot = false
				}
			} else if (operators.containsKey("$symbol")) {
				if (dot || symbol == '(') {
					if (symbol == '(') {
						operators["("] = (operators["("] ?: 0) - 1
						functions[prevToken] = true
					}
					operators[prevToken] = (operators[prevToken] ?: 0) + 1
				} else {
					operands[prevToken] = (operands[prevToken] ?: 0) + 1
				}
				currToken = "$symbol"
				dot = false
			}
		}
		if (operators.containsKey(currToken)) {
			operators[currToken] = (operators[currToken] ?: 0) + 1
		} else if (dot) {
			operators[currToken] = (operators[currToken] ?: 0) + 1
		} else {
			operands[currToken] = (operands[currToken] ?: 0) + 1
		}
		if (operands.containsKey("ped")) {
			operators["("] = (operators["("] ?: 0) + (operands["ped"] ?: 0)
			operands["swapped"] = (operands["swapped"] ?: 0) + (operands["ped"] ?: 0)
			operators["swap"] = (operators["swap"] ?: 0) + 1 - (operands["ped"] ?: 0)
			operands.remove("ped")
		}
		val sb = StringBuilder()
		sb.append(
			"""
			<style>
				body {
					background: #666;
				}
				table {
					border-collapse: collapse;
					width: 100%;
					max-width: 800px;
					margin: 0 auto;
				}
				th, td {
					padding: 8px;
					text-align: left;
					border-bottom: 1px solid #ddd;
				}
				th {
					background-color: #bcbcbc;
				}
				td {
					background: White;
				}
				tr:hover {
					background-color: #f5f5f5;
				}
			</style>
			""".trimIndent()
		)
		sb.append("<table><tbody>\n")
		sb.append("<tr><th>Number</th><th>Operator</th><th>How many</th></tr>\n")
		var allOperators = 0
		var numOperators = 1

		operators.entries.iterator().let { iterator ->
			while (iterator.hasNext()) {
				val (key, value) = iterator.next()
				if (value == 0 || (key != "if" && ignore.contains(key))) {
					iterator.remove()
					continue
				}
				sb.append("<tr><td>${numOperators++}</td><td>$key</td><td>$value</td></tr>\n")
				allOperators += value
			}
		}
		sb.append("<tr><th>${--numOperators}</th><td></td><th>$allOperators</th></tr>\n")
		sb.append("</tbody></table>\n")
		sb.append("<br>\n<table><tbody>\n")
		sb.append("<tr><th>Number</th><th>Operand</th><th>How many</th></tr>\n")

		operands.remove("in")
		var allOperands = 0
		var numOperands = 1
		for ((key, value) in operands) {
			sb.append("<tr><td>${numOperands++}</td><td>$key</td><td>$value</td></tr>\n")
			allOperands += value
		}
		sb.append("<tr><th>${--numOperands}</th><td></td><th>$allOperands</th></tr>\n")
		sb.append("</tbody></table>\n")
		val num = numOperands + numOperators
		val all = allOperands + allOperators
		sb.append("<br>\n<table><tbody>\n")
		sb.append("<tr><th>Dictionary</th><th>Length</th><th>Volume</th></tr>\n")
		sb.append("<tr><td>$num</td><td>$all</td><td>${all + log2(num.toDouble()).toInt()}</td></tr>\n")
		sb.append("</table></tbody>\n")
		File(outputPath).writeText("$sb")
	}
}