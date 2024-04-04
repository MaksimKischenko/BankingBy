package com.example.smsbankinganalitics.models

data class SmsArgs(
    val addressArray: Array<String>,
    val dateFrom:Long? = null,
    val dateTo: Long? = null

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SmsArgs

        if (!addressArray.contentEquals(other.addressArray)) return false
        if (dateFrom != other.dateFrom) return false
        if (dateTo != other.dateTo) return false

        return true
    }

    override fun hashCode(): Int {
        var result = addressArray.contentHashCode()
        result = 31 * result + (dateFrom?.hashCode() ?: 0)
        result = 31 * result + (dateTo?.hashCode() ?: 0)
        return result
    }
}

