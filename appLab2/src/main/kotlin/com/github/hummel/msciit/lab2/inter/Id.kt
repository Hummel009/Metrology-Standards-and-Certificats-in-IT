package com.github.hummel.msciit.lab2.inter

import com.github.hummel.msciit.lab2.lexer.Word
import com.github.hummel.msciit.lab2.symbols_types.Type

class Id(word: Word, type: Type?) : Expression(word, type)