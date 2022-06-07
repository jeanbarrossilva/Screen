package com.jeanbarrossilva.screen.extensions.kparameter

import kotlin.reflect.KClass
import kotlin.reflect.KParameter

/** Converts the given [KParameter] to a [KClass]. **/
fun KParameter.toKClass(): KClass<*>? {
    return type.classifier as? KClass<*>
}