package me.zenotick.json

import org.junit.Test
import java.io.File
import kotlin.test.*


/**
 * Test files retrieved from:
 *
 * [json.org](http://www.json.org/JSON_checker/)
 * Some fail/pass files are not included due to [rfc8259](https://www.rfc-editor.org/rfc/rfc8259)
 *
 * [code.google.com](https://code.google.com/archive/p/json-test-suite/downloads)
 */

class JsonTest {

    private val parser = JsonParser()

    @Test
    fun passingSample() {
        val file = File("src/test/resources/sample.json").readText()
        val value = parser.parse(file)
        assertIs<JsonObject>(value)
        assertTrue(value.size == 3)
        val keys = value.keys
        assertContains(keys, "a")
        assertContains(keys, "key")
        assertContains(keys, "z")
        assertIs<JsonObject>(value["a"])
        assertIs<JsonString>(value["key"])
        assertIs<JsonObject>(value["z"])
    }

    @Test
    fun pass1() {
        val rawData = File("src/test/resources/pass/pass1.json").readText()
        val json = parser.parse(rawData)
        assertIs<JsonArray>(json)
        assertTrue(json.size == 20)
    }

    @Test
    fun pass3() {
        val rawData = File("src/test/resources/pass/pass3.json").readText()
        val json = parser.parse(rawData)
        assertIs<JsonObject>(json)
        val keys = json.keys
        assertContains(keys, "JSON Test Pattern pass3")
        val elem = json["JSON Test Pattern pass3"]
        assertNotNull(elem)
        assertIs<JsonObject>(elem)
        assertTrue(elem.size == 2)
    }

    @Test
    fun fail2() {
        val rawData = File("src/test/resources/fail/fail2.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Expected ]")
    }

    @Test
    fun fail3() {
        val rawData = File("src/test/resources/fail/fail3.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Expected \"")
    }

    @Test
    fun fail4() {
        val rawData = File("src/test/resources/fail/fail4.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Unexpected character")
    }

    @Test
    fun fail5() {
        val rawData = File("src/test/resources/fail/fail5.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Unexpected character")
    }

    @Test
    fun fail6() {
        val rawData = File("src/test/resources/fail/fail6.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Unexpected character")
    }

    @Test
    fun fail7() {
        val rawData = File("src/test/resources/fail/fail7.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Expected EOF")
    }

    @Test
    fun fail8() {
        val rawData = File("src/test/resources/fail/fail8.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Expected EOF")
    }

    @Test
    fun fail9() {
        val rawData = File("src/test/resources/fail/fail9.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Expected \"")
    }

    @Test
    fun fail10() {
        val rawData = File("src/test/resources/fail/fail10.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Expected EOF")
    }

    @Test
    fun fail11() {
        val rawData = File("src/test/resources/fail/fail11.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Expected }")
    }

    @Test
    fun fail12() {
        val rawData = File("src/test/resources/fail/fail12.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Unexpected character")
    }


    @Test
    fun fail13() {
        val rawData = File("src/test/resources/fail/fail13.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Unexpected digit")
    }


    @Test
    fun fail14() {
        val rawData = File("src/test/resources/fail/fail14.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Unexpected hex")
    }


    @Test
    fun fail15() {
        val rawData = File("src/test/resources/fail/fail15.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Illegal backslash")
    }


    @Test
    fun fail16() {
        val rawData = File("src/test/resources/fail/fail16.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Unexpected character")
    }


    @Test
    fun fail17() {
        val rawData = File("src/test/resources/fail/fail17.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Illegal backslash")
    }

    @Test
    fun fail19() {
        val rawData = File("src/test/resources/fail/fail19.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Expected :")
    }

    @Test
    fun fail20() {
        val rawData = File("src/test/resources/fail/fail20.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Unexpected character")
    }

    @Test
    fun fail21() {
        val rawData = File("src/test/resources/fail/fail21.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Expected :")
    }

    @Test
    fun fail22() {
        val rawData = File("src/test/resources/fail/fail22.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Expected ]")
    }

    @Test
    fun fail23() {
        val rawData = File("src/test/resources/fail/fail23.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Failed to parse \"true\"")
    }

    @Test
    fun fail24() {
        val rawData = File("src/test/resources/fail/fail24.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Unexpected character")
    }

    @Test
    fun fail25() {
        val rawData = File("src/test/resources/fail/fail25.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Illegal control character")
    }

    @Test
    fun fail26() {
        val rawData = File("src/test/resources/fail/fail26.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Illegal backslash")
    }

    @Test
    fun fail27() {
        val rawData = File("src/test/resources/fail/fail27.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Illegal control character")
    }

    @Test
    fun fail28() {
        val rawData = File("src/test/resources/fail/fail28.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Illegal backslash")
    }

    @Test
    fun fail29() {
        val rawData = File("src/test/resources/fail/fail29.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Expected a digit")
    }

    @Test
    fun fail30() {
        val rawData = File("src/test/resources/fail/fail30.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Expected a digit")
    }

    @Test
    fun fail31() {
        val rawData = File("src/test/resources/fail/fail31.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Expected a digit")
    }

    @Test
    fun fail32() {
        val rawData = File("src/test/resources/fail/fail32.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Expected \"")
    }

    @Test
    fun fail33() {
        val rawData = File("src/test/resources/fail/fail33.json").readText()
        val exception = assertFailsWith<JsonParseException> { parser.parse(rawData) }
        assertContains(exception.localizedMessage, "Expected ]")
    }

}