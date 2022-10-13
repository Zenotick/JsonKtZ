# JSON for Kotlin
> A simple JSON tool written in Kotlin for parsing and building JSON data.

## Usage

#### Parsing
```kotlin
val rawData = """{ "msg": "Hello World", "nums": [0, 2.7e2, 3.3] }"""
val parser = JsonParser()
val jsonObject = parser.parse(rawData).asObject()
val msg = jsonObject["msg"].asString().get()

println(msg) // Hello World

val jsonArray = jsonObject["nums"].asArray()

println(jsonArray.size) // 3

val num = jsonArray[1].asNumber().get()
println(num) // 270.0
```

#### Building
```kotlin
val jsonObject = JsonObject()
jsonObject["msg"] = "Hello World"

val jsonArray = JsonArray().apply {
    add(0)
    add(2.7e2)
    add(3.3)
}

jsonObject.add("nums", jsonArray)

println(jsonObject.toString()) // {"msg":"Hello World","nums":[0,270.0,3.3]}

println(jsonObject.toPrettyString())
/*
{
    "msg": "Hello World",
    "nums": [
        0,
        270.0,
        3.3
    ]
}
*/
```