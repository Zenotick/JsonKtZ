package me.zenotick.json

abstract class JsonValue {

    abstract fun get(): Any

    abstract override fun toString(): String

    fun asArray() = this as JsonArray

    fun asObject() = this as JsonObject

    fun asPrimitive() = this as JsonPrimitive

    fun asString() = this as JsonString

    fun asBool() = this as JsonBool

    fun asNumber() = this as JsonNumber

    fun asNull() = this as JsonNull

    fun toPrettyString(): String = toPrettyString(this)

    private fun toPrettyString(json: JsonValue, depth: Int = 1): String {
        return when (json) {
            is JsonArray -> prettyJsonString(
                json.iterator(),
                depth,
                '[',
                ']'
            ) { value, newDepth -> toPrettyString(value, newDepth) }

            is JsonObject -> prettyJsonString(json.iterator(), depth, '{', '}') { kv, newDepth ->
                buildString {
                    append('"').append(kv.key).append('"').append(": ").append(toPrettyString(kv.value, newDepth))
                }
            }

            else -> json.toString()
        }
    }

    private inline fun <T> prettyJsonString(
        iterator: Iterator<T>, depth: Int, open: Char, close: Char, build: (T, Int) -> String
    ): String {
        val openTab = "\t".repeat(depth)
        val closeTab = openTab.substring(1)
        val builder = StringBuilder().append(open)
        if (!iterator.hasNext()) return builder.append(close).toString()
        builder.append('\n')
        while (true) {
            builder.append(openTab).append(build.invoke(iterator.next(), depth + 1))
            if (!iterator.hasNext()) return builder.append('\n').append(closeTab).append(close).toString()
            else builder.append(',').append('\n')
        }
    }
}