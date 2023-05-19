package com.dmitriivoronko.chartsta.core.result

/**
 * Wraps result for Domain layer where
 * S - model for succeed result
 * E - model for failed result
 */
sealed interface DomainResult<S, E> {

    data class Succeed<S, E>(val data: S) : DomainResult<S, E>

    data class Failed<S, E>(val error: E) : DomainResult<S, E>
}

/**
 * Gets succeed result or throw `DomainResultFailedException` if result is failed
 */
fun <S, E> DomainResult<S, E>.getOrThrow(): S {
    return when (this) {
        is DomainResult.Failed  -> throw DomainResultFailedException()
        is DomainResult.Succeed -> data
    }
}

/**
 * Gets succeed result or returns `null` if result is failed
 */
fun <S, E> DomainResult<S, E>.getOrNull(): S? {
    return when (this) {
        is DomainResult.Failed  -> null
        is DomainResult.Succeed -> data
    }
}

/**
 * Gets succeed result or default value if result is failed
 */
fun <S, E> DomainResult<S, E>.getOrDefault(defaultValue: S): S {
    return when (this) {
        is DomainResult.Failed  -> defaultValue
        is DomainResult.Succeed -> data
    }
}

/**
 * Gets succeed result or calls onFailed to handle error and returns succeed result
 */
inline fun <S, E> DomainResult<S, E>.getOrElse(onFailed: (error: E) -> S): S {
    return when (this) {
        is DomainResult.Failed  -> onFailed(error)
        is DomainResult.Succeed -> data
    }
}

/**
 * Transforms succeed result to another type, keeps error in the same type
 */
inline fun <R, S, E> DomainResult<S, E>.map(transform: (data: S) -> R): DomainResult<R, E> {
    return when (this) {
        is DomainResult.Failed  -> DomainResult.Failed(error)
        is DomainResult.Succeed -> DomainResult.Succeed(transform(data))
    }
}

/**
 * Recovers error result ot succeed or returns unchanged result if result is successful
 */
inline fun <S, E> DomainResult<S, E>.recover(transform: (error: E) -> S): DomainResult<S, E> {
    return when (this) {
        is DomainResult.Failed  -> DomainResult.Succeed(transform(error))
        is DomainResult.Succeed -> this
    }
}

/**
 * Transforms error inside result in another type
 */
inline fun <R, S, E> DomainResult<S, E>.errorMap(transform: (error: E) -> R): DomainResult<S, R> {
    return when (this) {
        is DomainResult.Failed  -> DomainResult.Failed(transform(error))
        is DomainResult.Succeed -> DomainResult.Succeed(data)
    }
}

/**
 * Transforms one result to another
 */
inline fun <R, T, S, E> DomainResult<S, E>.flatMap(
    transform: (data: S?, error: E?) -> DomainResult<R, T>,
): DomainResult<R, T> {
    return when (this) {
        is DomainResult.Failed  -> transform(null, error)
        is DomainResult.Succeed -> transform(data, null)
    }
}

inline fun <R, S, E> DomainResult<S, E>.flatMap(
    transform: (data: S) -> DomainResult<R, E>,
): DomainResult<R, E> = when (this) {
    is DomainResult.Failed  -> DomainResult.Failed(error)
    is DomainResult.Succeed -> transform(data)
}

/**
 * Calls action when result is failed and returns unchanged result
 */
suspend inline fun <S, E> DomainResult<S, E>.doOnFailed(
    action: (error: E) -> Unit,
): DomainResult<S, E> {
    if (this is DomainResult.Failed) {
        action(error)
    }

    return this
}

/**
 * Calls action when result is succeed and returns unchanged result
 */
suspend fun <S, E> DomainResult<S, E>.doOnSucceed(action: suspend (data: S) -> Unit):
        DomainResult<S, E> {
    if (this is DomainResult.Succeed) {
        action(data)
    }

    return this
}
