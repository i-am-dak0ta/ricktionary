@file:Suppress(
    "TooManyFunctions",
    "TooGenericExceptionCaught",
    "LongParameterList",
)

package com.dak0ta.ricktionary.core.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException

inline fun <R> runSuspendCatching(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (c: CancellationException) {
        throw c
    } catch (e: Throwable) {
        Result.failure(e)
    }
}

inline fun <R, T : R> Result<T>.recoverSuspendCatching(transform: (exception: Throwable) -> R): Result<R> {
    return when (val exception = exceptionOrNull()) {
        null -> this
        else -> runSuspendCatching { transform(exception) }
    }
}

suspend inline fun <T1, T2, R> asyncAwait(
    noinline s1: suspend CoroutineScope.() -> T1,
    noinline s2: suspend CoroutineScope.() -> T2,
    crossinline transform: suspend (T1, T2) -> R,
): R {
    return coroutineScope {
        val s1Async = async(block = s1)
        val s2Async = async(block = s2)
        transform(
            s1Async.await(),
            s2Async.await(),
        )
    }
}

suspend inline fun <T1, T2, T3, R> asyncAwait(
    noinline s1: suspend CoroutineScope.() -> T1,
    noinline s2: suspend CoroutineScope.() -> T2,
    noinline s3: suspend CoroutineScope.() -> T3,
    crossinline transform: suspend (T1, T2, T3) -> R,
): R {
    return coroutineScope {
        val s1Async = async(block = s1)
        val s2Async = async(block = s2)
        val s3Async = async(block = s3)
        transform(
            s1Async.await(),
            s2Async.await(),
            s3Async.await(),
        )
    }
}

suspend inline fun <T1, T2, T3, T4, R> asyncAwait(
    noinline s1: suspend CoroutineScope.() -> T1,
    noinline s2: suspend CoroutineScope.() -> T2,
    noinline s3: suspend CoroutineScope.() -> T3,
    noinline s4: suspend CoroutineScope.() -> T4,
    crossinline transform: suspend (T1, T2, T3, T4) -> R,
): R {
    return coroutineScope {
        val s1Async = async(block = s1)
        val s2Async = async(block = s2)
        val s3Async = async(block = s3)
        val s4Async = async(block = s4)
        transform(
            s1Async.await(),
            s2Async.await(),
            s3Async.await(),
            s4Async.await(),
        )
    }
}

suspend inline fun <T1, T2, T3, T4, T5, R> asyncAwait(
    noinline s1: suspend CoroutineScope.() -> T1,
    noinline s2: suspend CoroutineScope.() -> T2,
    noinline s3: suspend CoroutineScope.() -> T3,
    noinline s4: suspend CoroutineScope.() -> T4,
    noinline s5: suspend CoroutineScope.() -> T5,
    crossinline transform: suspend (T1, T2, T3, T4, T5) -> R,
): R {
    return coroutineScope {
        val s1Async = async(block = s1)
        val s2Async = async(block = s2)
        val s3Async = async(block = s3)
        val s4Async = async(block = s4)
        val s5Async = async(block = s5)
        transform(
            s1Async.await(),
            s2Async.await(),
            s3Async.await(),
            s4Async.await(),
            s5Async.await(),
        )
    }
}

suspend inline fun <T1, T2, T3, T4, T5, T6, R> asyncAwait(
    noinline s1: suspend CoroutineScope.() -> T1,
    noinline s2: suspend CoroutineScope.() -> T2,
    noinline s3: suspend CoroutineScope.() -> T3,
    noinline s4: suspend CoroutineScope.() -> T4,
    noinline s5: suspend CoroutineScope.() -> T5,
    noinline s6: suspend CoroutineScope.() -> T6,
    crossinline transform: suspend (T1, T2, T3, T4, T5, T6) -> R,
): R {
    return coroutineScope {
        val s1Async = async(block = s1)
        val s2Async = async(block = s2)
        val s3Async = async(block = s3)
        val s4Async = async(block = s4)
        val s5Async = async(block = s5)
        val s6Async = async(block = s6)
        transform(
            s1Async.await(),
            s2Async.await(),
            s3Async.await(),
            s4Async.await(),
            s5Async.await(),
            s6Async.await(),
        )
    }
}

suspend inline fun <T1, T2, T3, T4, T5, T6, T7, R> asyncAwait(
    noinline s1: suspend CoroutineScope.() -> T1,
    noinline s2: suspend CoroutineScope.() -> T2,
    noinline s3: suspend CoroutineScope.() -> T3,
    noinline s4: suspend CoroutineScope.() -> T4,
    noinline s5: suspend CoroutineScope.() -> T5,
    noinline s6: suspend CoroutineScope.() -> T6,
    noinline s7: suspend CoroutineScope.() -> T7,
    crossinline transform: suspend (T1, T2, T3, T4, T5, T6, T7) -> R,
): R {
    return coroutineScope {
        val s1Async = async(block = s1)
        val s2Async = async(block = s2)
        val s3Async = async(block = s3)
        val s4Async = async(block = s4)
        val s5Async = async(block = s5)
        val s6Async = async(block = s6)
        val s7Async = async(block = s7)
        transform(
            s1Async.await(),
            s2Async.await(),
            s3Async.await(),
            s4Async.await(),
            s5Async.await(),
            s6Async.await(),
            s7Async.await(),
        )
    }
}

suspend inline fun <T1, T2, T3, T4, T5, T6, T7, T8, R> asyncAwait(
    noinline s1: suspend CoroutineScope.() -> T1,
    noinline s2: suspend CoroutineScope.() -> T2,
    noinline s3: suspend CoroutineScope.() -> T3,
    noinline s4: suspend CoroutineScope.() -> T4,
    noinline s5: suspend CoroutineScope.() -> T5,
    noinline s6: suspend CoroutineScope.() -> T6,
    noinline s7: suspend CoroutineScope.() -> T7,
    noinline s8: suspend CoroutineScope.() -> T8,
    crossinline transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8) -> R,
): R {
    return coroutineScope {
        val s1Async = async(block = s1)
        val s2Async = async(block = s2)
        val s3Async = async(block = s3)
        val s4Async = async(block = s4)
        val s5Async = async(block = s5)
        val s6Async = async(block = s6)
        val s7Async = async(block = s7)
        val s8Async = async(block = s8)
        transform(
            s1Async.await(),
            s2Async.await(),
            s3Async.await(),
            s4Async.await(),
            s5Async.await(),
            s6Async.await(),
            s7Async.await(),
            s8Async.await(),
        )
    }
}

suspend inline fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> asyncAwait(
    noinline s1: suspend CoroutineScope.() -> T1,
    noinline s2: suspend CoroutineScope.() -> T2,
    noinline s3: suspend CoroutineScope.() -> T3,
    noinline s4: suspend CoroutineScope.() -> T4,
    noinline s5: suspend CoroutineScope.() -> T5,
    noinline s6: suspend CoroutineScope.() -> T6,
    noinline s7: suspend CoroutineScope.() -> T7,
    noinline s8: suspend CoroutineScope.() -> T8,
    noinline s9: suspend CoroutineScope.() -> T9,
    crossinline transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R,
): R {
    return coroutineScope {
        val s1Async = async(block = s1)
        val s2Async = async(block = s2)
        val s3Async = async(block = s3)
        val s4Async = async(block = s4)
        val s5Async = async(block = s5)
        val s6Async = async(block = s6)
        val s7Async = async(block = s7)
        val s8Async = async(block = s8)
        val s9Async = async(block = s9)
        transform(
            s1Async.await(),
            s2Async.await(),
            s3Async.await(),
            s4Async.await(),
            s5Async.await(),
            s6Async.await(),
            s7Async.await(),
            s8Async.await(),
            s9Async.await(),
        )
    }
}

suspend inline fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> asyncAwait(
    noinline s1: suspend CoroutineScope.() -> T1,
    noinline s2: suspend CoroutineScope.() -> T2,
    noinline s3: suspend CoroutineScope.() -> T3,
    noinline s4: suspend CoroutineScope.() -> T4,
    noinline s5: suspend CoroutineScope.() -> T5,
    noinline s6: suspend CoroutineScope.() -> T6,
    noinline s7: suspend CoroutineScope.() -> T7,
    noinline s8: suspend CoroutineScope.() -> T8,
    noinline s9: suspend CoroutineScope.() -> T9,
    noinline s10: suspend CoroutineScope.() -> T10,
    crossinline transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R,
): R {
    return coroutineScope {
        val s1Async = async(block = s1)
        val s2Async = async(block = s2)
        val s3Async = async(block = s3)
        val s4Async = async(block = s4)
        val s5Async = async(block = s5)
        val s6Async = async(block = s6)
        val s7Async = async(block = s7)
        val s8Async = async(block = s8)
        val s9Async = async(block = s9)
        val s10Async = async(block = s10)
        transform(
            s1Async.await(),
            s2Async.await(),
            s3Async.await(),
            s4Async.await(),
            s5Async.await(),
            s6Async.await(),
            s7Async.await(),
            s8Async.await(),
            s9Async.await(),
            s10Async.await(),
        )
    }
}

suspend inline fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> asyncAwait(
    noinline s1: suspend CoroutineScope.() -> T1,
    noinline s2: suspend CoroutineScope.() -> T2,
    noinline s3: suspend CoroutineScope.() -> T3,
    noinline s4: suspend CoroutineScope.() -> T4,
    noinline s5: suspend CoroutineScope.() -> T5,
    noinline s6: suspend CoroutineScope.() -> T6,
    noinline s7: suspend CoroutineScope.() -> T7,
    noinline s8: suspend CoroutineScope.() -> T8,
    noinline s9: suspend CoroutineScope.() -> T9,
    noinline s10: suspend CoroutineScope.() -> T10,
    noinline s11: suspend CoroutineScope.() -> T11,
    crossinline transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> R,
): R {
    return coroutineScope {
        val s1Async = async(block = s1)
        val s2Async = async(block = s2)
        val s3Async = async(block = s3)
        val s4Async = async(block = s4)
        val s5Async = async(block = s5)
        val s6Async = async(block = s6)
        val s7Async = async(block = s7)
        val s8Async = async(block = s8)
        val s9Async = async(block = s9)
        val s10Async = async(block = s10)
        val s11Async = async(block = s11)
        transform(
            s1Async.await(),
            s2Async.await(),
            s3Async.await(),
            s4Async.await(),
            s5Async.await(),
            s6Async.await(),
            s7Async.await(),
            s8Async.await(),
            s9Async.await(),
            s10Async.await(),
            s11Async.await(),
        )
    }
}

suspend fun <T, R> Iterable<T>.asyncMap(transform: suspend (T) -> R): List<R> {
    return coroutineScope {
        map { async { transform(it) } }.awaitAll()
    }
}

suspend fun <T> Iterable<T>.asyncForEach(block: suspend (T) -> Unit) {
    coroutineScope {
        map { async { block(it) } }.awaitAll()
    }
}

suspend fun asyncComplete(vararg blocks: suspend CoroutineScope.() -> Unit) {
    asyncComplete(blocks.toList())
}

suspend fun asyncComplete(block: List<suspend CoroutineScope.() -> Unit>) {
    coroutineScope {
        block.map { async(block = it) }.awaitAll()
    }
}

/**
 * Checks if exception is not a CancellationException and of true calls block lambda.
 * Comfortable to use in case it is not obligatory to rethrow CancellationException,
 * but other exceptions should be processed.
 *
 * Example:
 * ```
 * async {
 *     doSomething()
 * }.invokeOnCompletion { error ->
 *     error?.ifNonCancellation {
 *         log(this)
 *     }
 * }
 * ```
 *
 * @see <a href="https://kotlinlang.org/docs/exception-handling.html#cancellation-and-exceptions">
 *     Cancellation and exception handling</a>
 */
fun Throwable.ifNonCancellation(block: Throwable.() -> Unit) {
    if (this !is CancellationException) {
        block()
    }
}

@Suppress("IncorrectCoroutineScopeUsage")
fun CoroutineScope.childScope(context: CoroutineContext = EmptyCoroutineContext): CoroutineScope {
    return CoroutineScope(coroutineContext + context + Job(coroutineContext[Job]))
}

@Suppress("IncorrectCoroutineScopeUsage")
fun CoroutineScope.childSupervisorScope(context: CoroutineContext = EmptyCoroutineContext): CoroutineScope {
    return CoroutineScope(coroutineContext + context + SupervisorJob(coroutineContext[Job]))
}

suspend fun Mutex.waitUnlock() {
    lock()
    unlock()
}

/**
 * Guarantees delay before return data or throw error
 * @param delay Time in ms for delaying block() result sending
 * @param block Block to be executed
 */
suspend inline fun <T> withMinExecutionTime(delay: Long, crossinline block: suspend () -> T): T {
    return coroutineScope {
        val minExecutionTime = async { delay(delay) }
        val result = runSuspendCatching { block() }.onFailure { minExecutionTime.await() }.getOrThrow()
        minExecutionTime.await()
        return@coroutineScope result
    }
}
