package com.jeanbarrossilva.screen.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Serializes an object and deserializes a given JSON in the background.
 *
 * Prevents the creation of multiple [Gson]s in the project and, since it's an object, has the
 * advantage of having its only instance survive throughout the entire application process.
 **/
object JsonConverter {
    @PublishedApi
    internal const val TAG = "JsonConverter"

    /** [CoroutineContext] in which the conversions will be performed. **/
    @PublishedApi
    internal val coroutineContext = Dispatchers.Default

    /** [Gson] through which the conversions are performed. **/
    @PublishedApi
    internal val gson = Gson()

    /**
     * Tries to deserialize the given [json].
     *
     * @param json JSON to try to deserialize.
     **/
    @PublishedApi
    internal suspend fun <T: Any> tryToDeserialize(
        deserialization: Pair<String, KClass<T>>
    ): T? {
        return try {
            deserializeOrThrow(deserialization)
        } catch (_: JsonSyntaxException) {
            null
        }
    }

    /**
     * Deserializes the given [json] or throws a [JsonSyntaxException] if it fails.
     *
     * @param json JSON to be deserialized.
     * @throws JsonSyntaxException If the deserialization fails.
     */
    @PublishedApi
    internal suspend fun <T: Any> deserializeOrThrow(deserialization: Pair<String, KClass<T>>): T {
        val (json, kClass) = deserialization
        return withContext(coroutineContext) {
            gson.fromJson(json, kClass.java)
        }
    }

    /**
     * Serializes the given [something], converting its contents to JSON.
     *
     * @param something Object to be serialized.
     **/
    suspend infix fun serialize(something: Any?): String {
        return withContext(coroutineContext) {
            gson.toJson(something).also {
                Log.d(TAG, "serialize: $it")
            }
        }
    }

    /**
     * Deserializes the given JSON, converting it to an object of type [T].
     *
     * @param deserialization [Pair] with the JSON and the [KClass] with which the deserialization
     * will be performed.
     **/
    suspend infix fun <T: Any> deserialize(deserialization: Pair<String, KClass<T>>): T? {
        return tryToDeserialize(deserialization).also {
            Log.d(TAG, "deserialize: $it")
        }
    }

    suspend inline infix fun <reified T: Any> deserialize(json: String): T? {
        return deserialize(json to T::class)
    }
}