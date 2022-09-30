package me.zenotick.json

sealed class JsonPrimitive : JsonValue()

class JsonString(private val string: String) : JsonPrimitive() {

    override fun get() = string

    override fun toString() = buildString(string.length + 2) { append('"').append(string).append('"') }
}

class JsonNumber(private val number: Number) : JsonPrimitive() {

    override fun get() = number

    override fun toString() = number.toString()
}

class JsonBool (private val bool: Boolean) : JsonPrimitive() {

    companion object {
        val FALSE = JsonBool(false)
        val TRUE = JsonBool(true)
    }

    override fun get() = bool

    override fun toString() = bool.toString()
}

object JsonNull : JsonPrimitive() {

    override fun get() = this

    override fun toString() = "null"
}