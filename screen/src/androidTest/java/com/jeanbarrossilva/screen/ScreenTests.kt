package com.jeanbarrossilva.screen

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.jeanbarrossilva.screen.test.TestScreen
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ScreenTests {
    private val activity = ApplicationProvider.getApplicationContext<AppCompatActivity>()
    private val navController = TestNavHostController(activity)

    @Test
    fun parentWithChildrenIsNotEmpty() {
        listOf(
            TestScreen.Parent({ activity }, { navController }, "parent1") {
                listOf(
                    child("1"),
                    child("2"),
                    child("3")
                )
            },
            TestScreen.Parent({ activity }, { navController }, "parent2") {
                listOf(
                    child("1"),
                    child("2"),
                    child("3") {
                        listOf(
                            child("1"),
                            child("2"),
                            child("3")
                        )
                    }
                )
            }
        ).forEach { parent ->
            assertFalse {
                parent.children.isEmpty()
            }
        }
    }

    @Test
    fun findsScreensDeepDownInTheTree() {
        TestScreen.Parent({ activity }, { navController }, "1") {
            listOf(
                child("1"),
                child("2"),
                child("3") {
                    listOf(
                        child("1"),
                        child("2"),
                        child("3") {
                            listOf(
                                child("1"),
                                child("2"),
                                child("3"),
                                child("4"),
                                child("5") {
                                    listOf(
                                        child("1"),
                                        child("2"),
                                        child("3")
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }.run {
            assertTrue(hasScreenAt("1/1"))
            assertTrue(hasScreenAt("1/2"))
            assertTrue(hasScreenAt("1/3"))
            assertTrue(hasScreenAt("1/3/1"))
            assertTrue(hasScreenAt("1/3/2"))
            assertTrue(hasScreenAt("1/3/3"))
            assertTrue(hasScreenAt("1/3/3/1"))
            assertTrue(hasScreenAt("1/3/3/2"))
            assertTrue(hasScreenAt("1/3/3/3"))
            assertTrue(hasScreenAt("1/3/3/4"))
            assertTrue(hasScreenAt("1/3/3/5"))
            assertTrue(hasScreenAt("1/3/3/5/1"))
            assertTrue(hasScreenAt("1/3/3/5/2"))
            assertTrue(hasScreenAt("1/3/3/5/3"))
        }
    }
}