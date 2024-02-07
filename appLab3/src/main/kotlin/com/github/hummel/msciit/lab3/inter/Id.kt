package com.github.hummel.msciit.lab3.inter

import com.github.hummel.msciit.lab3.lexer.Word
import com.github.hummel.msciit.lab3.symbols_types.Type

class Id(word: Word, type: Type?) : Expression(word, type)