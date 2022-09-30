package me.zenotick.json

class JsonParser private constructor() {

    companion object {
        private val escapeChars = setOf('"', '\\', '/', 'b', 'f', 'n', 'r', 't')
        private val parser = JsonParser()

        fun parse(string: String): JsonValue = parser.parse(string)
    }

    private val builder = StringBuilder()
    private var reader = "".reader()
    private var line = 0
    private var column = 0

    private fun parse(string: String): JsonValue {
        try {
            this.reader = string.reader()
            if (peek() == -1) return JsonNull
            val jsonValue = readValue()
            if (read() != -1) throw fail("Expected EOF")
            return jsonValue
        } finally {
            line = 0
            column = 0
            builder.clear()
        }
    }

    private fun readObject(): JsonObject {
        readOrFail('{')
        val jsonObject = JsonObject()
        readWhitespace()
        if (!peek('}')) {
            while (true) {
                val name = readString().get()
                readWhitespace()
                readOrFail(':')
                jsonObject.add(name, readValue())
                if (!peek(',')) break
                read()
                readWhitespace()
            }
        }
        readOrFail('}')
        return jsonObject
    }

    private fun readArray(): JsonArray {
        readOrFail('[')
        val jsonArray = JsonArray()
        readWhitespace()
        if (!peek(']')) {
            while (true) {
                jsonArray.add(readValue())
                if (!peek(',')) break
                read()
            }
        }
        readOrFail(']')
        return jsonArray
    }

    private fun readValue(): JsonValue {
        readWhitespace()
        val jsonValue = when (peekAsChar()) {
            '"' -> readString()
            '-', in '0'..'9' -> readNumber()
            '{' -> readObject()
            '[' -> readArray()
            't', 'f', 'n' -> readBooleanOrNull()
            else -> throw fail("Unexpected character", true)
        }
        readWhitespace()
        return jsonValue
    }

    private fun readString(): JsonString {
        readOrFail('"')
        builder.clear()
        while (peek() != -1) {
            val char = readAsChar()
            if (char == '"') return JsonString(builder.toString())
            if (char <= '\u001F') throw fail("Illegal control character")
            if (char == '\\') {
                builder.append(char)
                val esc = peekAsChar()
                if (escapeChars.contains(esc)) builder.append(readAsChar())
                else {
                    if (esc != 'u') throw fail("Illegal backslash")
                    builder.append(readAsChar())
                    repeat(4) {
                        val hex = readAsChar()
                        if (Character.digit(hex, 16) == -1) throw fail("Expected a hex")
                        builder.append(hex)
                    }
                }
            } else builder.append(char)
        }
        throw fail("Expected \"")
    }

    private fun readNumber(): JsonNumber {
        builder.clear()
        if (peek('-')) builder.append(readAsChar())

        val digit = peekAsChar()
        if (digit in '1'..'9') {
            do builder.append(readAsChar())
            while (Character.isDigit(peek()))
        } else if (digit == '0') {
            builder.append(readAsChar())
            if (Character.isDigit(peek())) throw fail("Unexpected digit")
        } else throw fail("Unexpected character")

        var isWhole = true
        if (peek('.')) {
            builder.append(readAsChar())
            if (!Character.isDigit(peek())) throw fail("Expected a digit")
            do builder.append(readAsChar()) while (Character.isDigit(peek()))
            isWhole = false
        }

        val exp = peekAsChar()
        if (exp == 'E' || exp == 'e') {
            builder.append(readAsChar()) //E,e
            val sign = peekAsChar()
            if (sign == '-' || sign == '+') builder.append(readAsChar()) //-,+
            if (!Character.isDigit(peek())) throw fail("Expected a digit")
            do builder.append(readAsChar()) while (Character.isDigit(peek()))
            isWhole = false
        }

        val numString = builder.toString()
        return if (isWhole) JsonNumber(numString.toLong()) else JsonNumber(numString.toDouble())
    }


    private fun readBooleanOrNull(): JsonPrimitive {
        return when (readAsChar()) {
            't' -> if (readAsChar() != 'r' || readAsChar() != 'u' || readAsChar() != 'e')
                throw fail("Failed to parse \"true\"") else JsonBool.TRUE
            'f' -> if (readAsChar() != 'a' || readAsChar() != 'l' || readAsChar() != 's' || readAsChar() != 'e')
                throw fail("Failed to parse \"false\"") else JsonBool.FALSE
            'n' -> if (readAsChar() != 'u' || readAsChar() != 'l' || readAsChar() != 'l')
                throw fail("Failed to parse \"null\"") else JsonNull
            else -> throw fail("Unexpected character")
        }
    }

    /**
     * Read until a non-white-space character is encountered.
     */
    private fun readWhitespace() {
        while (Character.isWhitespace(peek())) {
            if (readAsChar() == '\n') {
                line++
                column = 0
            }
        }
    }

    private fun readOrFail(match: Char) {
        if (readAsChar() != match) throw fail("Expected $match")
    }

    private fun fail(reason: String, peek: Boolean = false): Throwable {
        return JsonParseException(buildString(reason.length + 16) {
            append(reason).append(" at line ").append(line + 1).append(':').append(if (peek) column + 1 else column)
        })
    }

    private fun peek(): Int {
        reader.mark(1)
        val read = reader.read()
        reader.reset()
        return read
    }

    private fun read(): Int {
        column++
        return reader.read()
    }

    private fun peek(match: Char) = peekAsChar() == match

    private fun peekAsChar() = peek().toChar()

    private fun readAsChar() = read().toChar()
}