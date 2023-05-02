package hummel.inter

import hummel.lexer.Word
import hummel.symbols_types.Type

class Id(word: Word, type: Type?) : Expression(word, type)