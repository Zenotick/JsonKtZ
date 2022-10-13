package me.zenotick.json

class JsonArray : JsonValue() {

    private val values = mutableListOf<JsonValue>()

    val size
        get() = this.values.size

    operator fun get(index: Int) = values[index]

    operator fun set(index: Int, value: JsonValue) {
        values[index] = value
    }

    operator fun set(index: Int, value: Number) = set(index, JsonNumber(value))

    operator fun set(index: Int, value: String) = set(index, JsonString(value))

    operator fun set(index: Int, value: Boolean) = set(index, JsonBool(value))

    operator fun iterator() = values.iterator()

    fun add(value: JsonValue) = values.add(value)

    fun add(value: String) = add(JsonString(value))

    fun add(value: Number) = add(JsonNumber(value))

    fun add(value: Boolean) = add(JsonBool(value))

    override fun get() = values

    override fun toString(): String {
        val builder = StringBuilder().append('[')
        val iterator = values.iterator()
        if (!iterator.hasNext()) return builder.append(']').toString()
        while (true) {
            builder.append(iterator.next())
            if (!iterator.hasNext()) return builder.append(']').toString()
            else builder.append(',')
        }
    }

    fun getObject(i: Int) = get(i) as JsonObject
}