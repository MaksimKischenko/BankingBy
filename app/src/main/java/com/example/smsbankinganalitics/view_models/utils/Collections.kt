package com.example.smsbankinganalitics.view_models.utils

fun <K, V, R> Map<K, V>.mapIndexed(transform: (index: Int, entry: Map.Entry<K, V>) -> R): List<R> {
    var index = 0
    return this.map { entry ->
        val result = transform(index, entry)
        index++
        result
    }
}