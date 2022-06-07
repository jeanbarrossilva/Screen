package com.jeanbarrossilva.screen

import com.jeanbarrossilva.screen.extensions.array.joining
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayExtensionsTests {
    @Test
    fun `joining - GIVEN an array WHEN joining an object THEN it should join the array`() {
        assertEquals(
            arrayOf("was", "left", "to", "my", "own", "devices").joining("I" to 0),
            arrayOf("I", "was", "left", "to", "my", "own", "devices")
        )
        assertEquals(
            arrayOf("Many", "days", "fell", "away", "nothing", "to", "show").joining("with" to 4),
            arrayOf("Many", "days", "fell", "away", "with", "nothing", "to", "show")
        )
        assertEquals(
            arrayOf("And", "the", "walls", "kept", "tumbling").joining("down" to 5),
            arrayOf("And", "the", "walls", "kept", "tumbling", "down")
        )
        assertEquals(arrayOf(0, 1, 3, 4).joining(2 to 2), arrayOf(0, 1, 2, 3, 4))
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `joining - GIVEN an array WHEN joining to an index out of bounds THEN it should throw`() {
        arrayOf(0, 1, 2, 3).joining(10 to 9)
    }
}