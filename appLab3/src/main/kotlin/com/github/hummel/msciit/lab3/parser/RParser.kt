package com.github.hummel.msciit.lab3.parser

import com.github.hummel.msciit.lab3.Chepin
import com.github.hummel.msciit.lab3.ChepinGroups
import com.github.hummel.msciit.lab3.inter.*
import com.github.hummel.msciit.lab3.inter.boolean.*
import com.github.hummel.msciit.lab3.inter.statements.*
import com.github.hummel.msciit.lab3.inter.statements.Set
import com.github.hummel.msciit.lab3.lexer.Lexer
import com.github.hummel.msciit.lab3.lexer.Tag
import com.github.hummel.msciit.lab3.lexer.Token
import com.github.hummel.msciit.lab3.lexer.Word
import com.github.hummel.msciit.lab3.symbols_types.Array
import com.github.hummel.msciit.lab3.symbols_types.Env
import com.github.hummel.msciit.lab3.symbols_types.Type

private const val SYNTAX_ERROR: String = "syntax error"

class RParser(private val lexer: Lexer, private val metrics: Chepin) {
	private var look: Token? = null
	private var top: Env? = null
	private var used = 0

	init {
		move()
	}

	private fun move() {
		look = lexer.scan()
	}

	private fun error(msg: String) {
		throw Exception("Near line " + Lexer.line + " " + look + ": " + msg)
	}

	private fun match(t: Int) = if (look?.tag == t) move() else error(SYNTAX_ERROR)

	fun parse(print: Boolean) {
		val s = block()
		if (print) {
			val begin = s.newLabel()
			val after = s.newLabel()
			s.emitLabel(begin)
			s.gen(begin, after)
			s.emitLabel(after)
		}
	}

	private fun block(): Statement {
		match(Tag.BEGIN.code)
		match(Tag.OPERATOR_END.code)
		val savedEnv = top
		top = Env(top)
		val s = statements()
		match(Tag.END.code)
		match(Tag.OPERATOR_END.code)
		top = savedEnv
		return s
	}

	private fun isEndOfBlock(): Boolean = look!!.tag == Tag.END.code || look!!.tag == Tag.ELSE.code

	private fun blockWithEnd(): Statement {
		val savedEnv = top
		top = Env(top)
		val s = statements()
		match(Tag.END.code)
		match(Tag.OPERATOR_END.code)
		top = savedEnv
		return s
	}

	private fun blockWithoutEnd(): Statement {
		val savedEnv = top
		top = Env(top)
		val s = statements()
		top = savedEnv
		return s
	}

	private fun statements(): Statement {
		return if (isEndOfBlock()) {
			Statement.NULL
		} else {
			Sequence(pStatement()!!, statements())
		}
	}

	private fun pStatement(): Statement? {
		val x: Expression?
		var s: Statement?
		val s1: Statement?
		val s2: Statement
		val savedStatement: Statement
		return when ((look ?: return null).tag) {
			Tag.OPERATOR_END.code -> {
				move()
				Statement.NULL
			}

			Tag.CASE.code -> {
				match(Tag.CASE.code)
				x = pExpr()
				match(Tag.OPERATOR_END.code)
				val casenode = Case(x ?: return null, null)

				while ((look ?: return null).tag == Tag.WHEN.code) {
					s = whens()
					casenode.add(s ?: return null)
				}

				if ((look ?: return null).tag != Tag.ELSE.code) {
					match(Tag.END.code)
					match(Tag.OPERATOR_END.code)
					return casenode
				}
				match(Tag.ELSE.code)
				match(Tag.OPERATOR_END.code)
				s = blockWithEnd()
				try {
					match(Tag.OPERATOR_END.code)
				} catch (ignored: Exception) {
				}
				casenode.state = s
				casenode
			}

			Tag.IF.code -> {
				match(Tag.IF.code)
				match('('.code)
				metrics.groupTag = ChepinGroups.C
				x = pBool()
				match(')'.code)
				match(Tag.OPERATOR_END.code)

				s1 = blockWithoutEnd()

				if ((look ?: return null).tag != Tag.ELSE.code) {
					match(Tag.END.code)
					return If(x ?: return null, s1)
				}
				match(Tag.ELSE.code)
				if ((look ?: return null).tag != Tag.IF.code) {
					match(Tag.OPERATOR_END.code)
				}

				s2 = blockWithEnd()

				Else(x ?: return null, s1, s2)
			}

			Tag.WHILE.code -> {
				val whilenode = While()
				savedStatement = Statement.enclosing
				Statement.enclosing = whilenode
				match(Tag.WHILE.code)
				match('('.code)
				metrics.groupTag = ChepinGroups.C
				x = pBool()
				match(')'.code)

				s1 = pStatement()

				whilenode.init(x ?: return null, s1 ?: return null)
				Statement.enclosing = savedStatement
				whilenode
			}

			Tag.UNTIL.code -> {
				val untilnode = Until()
				Statement.enclosing = untilnode
				match(Tag.UNTIL.code)
				match('('.code)
				metrics.groupTag = ChepinGroups.C
				x = pBool()
				match(')'.code)
				s1 = pStatement()
				untilnode.init(x ?: return null, s1 ?: return null)
				untilnode
			}

			Tag.LOOP.code -> {
				val loopnode = Loop()
				savedStatement = Statement.enclosing
				Statement.enclosing = loopnode
				match(Tag.LOOP.code)
				s = pStatement()
				loopnode.init(s ?: return null)
				Statement.enclosing = savedStatement
				loopnode
			}

			Tag.BREAK.code -> {
				match(Tag.BREAK.code)
				match(Tag.OPERATOR_END.code)
				Break()
			}

			Tag.GETS.code -> {
				metrics.groupTag = ChepinGroups.P
				match(Tag.GETS.code)
				try {
					metrics.isP = true
					val ex = pExpr()
					metrics.isP = false
					Gets(ex, "")
				} catch (e: Exception) {
					val str = StringBuilder()
					do {
						str.append(look.toString())
						move()
					} while ((look ?: return null).tag != Tag.OPERATOR_END.code)
					match(Tag.OPERATOR_END.code)
					Gets(null, "$str")
				}
			}

			Tag.PUTS.code -> {
				metrics.groupTag = ChepinGroups.P
				match(Tag.PUTS.code)
				try {
					metrics.isP = true
					val ex = pExpr()
					metrics.isP = false
					Puts(ex, "")
				} catch (e: Exception) {
					val str = StringBuilder()
					do {
						str.append(look.toString())
						move()
					} while ((look ?: return null).tag != Tag.OPERATOR_END.code)
					match(Tag.OPERATOR_END.code)
					Puts(null, "$str")
				}
			}

			Tag.BEGIN.code -> block()
			else -> {
				metrics.groupTag = ChepinGroups.M
				assign()
			}
		}
	}

	private fun whenStatements(): Statement {
		return if (look!!.tag == Tag.WHEN.code || look!!.tag == Tag.ELSE.code || look!!.tag == Tag.END.code) Statement.NULL else Sequence(
			pStatement()!!, whenStatements()
		)
	}

	private fun whens(): Statement? {
		val s: Statement
		val x: Expression?
		if ((look ?: return null).tag == Tag.WHEN.code) {
			move()
			x = pExpr()
			match(Tag.OPERATOR_END.code)
			s = whenStatements()
			return When(x ?: return null, s)
		}
		error("Case error: 'when' undefined")
		return null
	}

	private fun assign(): Statement? {
		var statement: Statement? = null
		val t = look
		match(Tag.ID.code)
		val id = (top ?: return null)[t]
		if ((look ?: return null).tag == '='.code) {
			move()
			val temp = metrics.groupTag
			metrics.groupTag = ChepinGroups.M

			val ex = pBool()
			id?.let {
				metrics.setIndex(it, temp)
				statement = Set(it, ex ?: return@assign null)
			} ?: run {
				val type = (ex ?: return@assign null).type
				val newId = Id((t as Word?) ?: return@assign null, type)
				(top ?: return@assign null).put(t ?: return@assign null, newId)
				used += (type ?: return@assign null).width
				statement = Set(newId, ex)
				metrics.setIndex(newId, ChepinGroups.T)
				metrics.tryAddToPBuffer(newId)
				metrics.isP = false
			}
			match(Tag.OPERATOR_END.code)
			return statement
		}
		error(" '=' expected (or you've tried to write Key word but make mistake ;0")
		return null
	}

	private fun pBool(): Expression? {
		var x = join()
		while ((look ?: return null).tag == Tag.OR.code) {
			val token = look
			move()
			x = Or(token ?: return null, x ?: return null, join() ?: return null)
		}
		return x
	}

	private fun join(): Expression? {
		var x = equality()
		while ((look ?: return null).tag == Tag.AND.code) {
			val token = look
			move()
			x = And(token ?: return null, x ?: return null, equality() ?: return null)
		}
		return x
	}

	private fun equality(): Expression? {
		var x = pRel()
		while ((look ?: return null).tag == Tag.EQUAL.code || (look ?: return null).tag == Tag.NOT_EQUAL.code) {
			val token = look
			move()
			x = Rel(token ?: return null, x ?: return null, pRel() ?: return null)
		}
		return x
	}

	private fun pRel(): Expression? {
		val x = pExpr()
		return when ((look ?: return null).tag) {
			'<'.code, Tag.LOWER_EQUAL.code, Tag.GREAT_EQUAL.code, '>'.code -> {
				val token = look
				move()
				Rel(token ?: return null, x ?: return null, pExpr() ?: return null)
			}

			else -> x
		}
	}

	private fun pExpr(): Expression? {
		var x = pTerm()
		while ((look ?: return null).tag == '+'.code || (look ?: return null).tag == '-'.code) {
			val token = look
			move()
			x = Arithmetic(token ?: return null, x ?: return null, pTerm() ?: return null)
		}
		return x
	}

	private fun pTerm(): Expression? {
		var x = pUnary()
		while ((look ?: return null).tag == '*'.code || (look ?: return null).tag == '/'.code) {
			val token = look
			move()
			x = Arithmetic(token ?: return null, x ?: return null, pUnary() ?: return null)
		}
		return x
	}

	private fun pUnary(): Expression? {
		return when ((look ?: return null).tag) {
			'-'.code -> {
				move()
				Unary(Word.MINUS, pUnary() ?: return null)
			}

			'!'.code, Tag.NOT.code -> {
				val token = look
				move()
				Not(token ?: return null, pUnary() ?: return null)
			}

			else -> factor()
		}
	}

	private fun factor(): Expression? {
		val x: Expression?
		when ((look ?: return null).tag) {
			'('.code -> {
				move()
				x = pBool()
				match(')'.code)
				return x
			}

			Tag.NUM.code -> {
				x = Constant(look ?: return null, Type.INT)
				move()
				return x
			}

			Tag.FLOAT.code -> {
				x = Constant(look ?: return null, Type.FLOAT)
				move()
				return x
			}

			Tag.TRUE.code -> {
				x = Constant.TRUE
				move()
				return x
			}

			Tag.FALSE.code -> {
				x = Constant.FALSE
				move()
				return x
			}

			Tag.GETS.code -> {
				x = Constant(look ?: return null, Type.INT)
				metrics.isP = true
				move()
				return x
			}

			Tag.ID.code -> {
				run {
					val id = (top ?: return@run)[look]
					id?.let {
						move()
						return@factor if ((look ?: return@run).tag != '['.code) {
							metrics.setIndex(it, metrics.groupTag)
							metrics.tryAddToPBuffer(it)
							id
						} else offset(it)
					} ?: run {
						error("$look undeclared")
					}
				}
				run { error(SYNTAX_ERROR) }
			}

			else -> {
				error(SYNTAX_ERROR)
			}
		}
		return null
	}

	private fun offset(id: Id): Access? {
		var i: Expression?
		var w: Expression
		var t1: Expression
		var t2: Expression
		var loc: Expression
		val type = id.type
		match('['.code)
		i = pBool()
		match(']'.code)
		type?.let {
			val shadType = (type as Array).type
			w = Constant(shadType.width)
			t1 = Arithmetic(Token('*'.code), i ?: return@offset null, w)
			loc = t1
			while ((look ?: return@offset null).tag == '['.code) {
				match('['.code)
				i = pBool() //index
				match(']'.code)
				w = Constant(shadType.width)
				t1 = Arithmetic(Token('*'.code), i ?: return@offset null, w)
				t2 = Arithmetic(Token('+'.code), loc, t1)
				loc = t2
			}
			return@offset Access(id, loc, shadType)
		} ?: run {
			error("type error")
		}
		return null
	}

}