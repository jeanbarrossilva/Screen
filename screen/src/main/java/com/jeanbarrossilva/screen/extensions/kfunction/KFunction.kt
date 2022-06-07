package com.jeanbarrossilva.screen.extensions.kfunction

import com.jeanbarrossilva.screen.extensions.kparameter.toKClass
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter

/** [KClass]es of the given [KFunction]'s parameters. **/
val <T> KFunction<T>.parameterClasses
    get() = parameters.map(KParameter::toKClass)