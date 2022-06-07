package com.jeanbarrossilva.screen.test

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jeanbarrossilva.screen.Screen
import com.jeanbarrossilva.screen.extensions.screen.viewModel
import com.jeanbarrossilva.screen.test.interop.TestScreenViewModel

interface TestScreen: Screen {
    override val viewModel
        get() = viewModel<TestScreenViewModel>().value

    @Composable
    override fun Content(modifier: Modifier) {
    }

    interface Parent: TestScreen, Screen.Parent {
        fun child(resource: String, children: Parent.() -> List<Child> = { emptyList() }):
            Child {
            return object: Parent, Child {
                override val children = children()
                override val parent = this@Parent
                override val resource = resource

                override fun isRelatedTo(other: Screen): Boolean {
                    return super<Child>.isRelatedTo(other)
                }
            }
        }

        fun hasScreenAt(route: String): Boolean {
            return searchFor(route) != null
        }

        companion object {
            operator fun invoke(
                getActivity: () -> AppCompatActivity,
                getNavController: () -> NavController,
                route: String,
                children: Parent.() -> List<Child>
            ): Parent {
                return object: Parent {
                    override val getActivity = getActivity
                    override val getNavController = getNavController
                    override val route = route
                    override val children = children()
                }
            }
        }
    }

    interface Child: TestScreen, Screen.Child
}