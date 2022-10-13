package me.zenotick.json

class JsonObject : JsonValue() {

    private val nameValueMap = mutableMapOf<String, JsonValue>()

    val size
        get() = this.nameValueMap.size

    val keys
        get() = this.nameValueMap.keys

    operator fun get(index: String) = nameValueMap[index] ?: throw IndexOutOfBoundsException()

    operator fun set(name: String, value: JsonValue) {
        nameValueMap[name] = value
    }

    operator fun set(name: String, value: String) = set(name, JsonString(value))

    operator fun set(name: String, value: Number) = set(name, JsonNumber(value))

    operator fun set(name: String, value: Boolean) = set(name, JsonBool(value))

    operator fun iterator() = nameValueMap.iterator()

    fun add(name: String, value: JsonValue) = set(name, value)

    fun add(name: String, value: String) = set(name, value)

    fun add(name: String, value: Number) = set(name, value)

    fun add(name: String, value: Boolean) = set(name, value)

    override fun get() = nameValueMap

    override fun toString(): String {
        val builder = StringBuilder().append('{')
        val iterator = nameValueMap.iterator()
        if (!iterator.hasNext()) return builder.append('}').toString()
        while (true) {
            val entry = iterator.next()
            builder.append('"').append(entry.key).append('"')
                .append(':').append(entry.value.toString())
            if (!iterator.hasNext()) return builder.append('}').toString()
            else builder.append(',')
        }
    }

    fun getArray(index: String) = get(index) as JsonArray

    fun getString(index: String) = get(index) as JsonString
}