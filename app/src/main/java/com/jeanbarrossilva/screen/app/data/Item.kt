package com.jeanbarrossilva.screen.app.data

import java.io.Serializable

data class Item(val title: String): Serializable {
    companion object {
        val empty = Item("")
        val samples = 0.rangeTo(64).map { index -> Item at index }
        val sample
            get() = samples.random()

        infix fun at(index: Int): Item {
            return Item("Item #${index + 1}")
        }
    }
}