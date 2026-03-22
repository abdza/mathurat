package com.abdullahsolutions.mathurat.model

enum class CompletionLevel { NONE, SUGHRA, KUBRA }

data class CalendarDay(
    val dateString: String,   // "yyyy-MM-dd", empty for padding cells
    val dayOfMonth: Int,      // 0 for padding cells
    val sughraMorning: Boolean = false,
    val sughraEvening: Boolean = false,
    val kubraMorning: Boolean = false,
    val kubraEvening: Boolean = false,
    val isToday: Boolean = false
) {
    val isPadding: Boolean get() = dateString.isEmpty()

    fun morningLevel(): CompletionLevel = when {
        kubraMorning -> CompletionLevel.KUBRA
        sughraMorning -> CompletionLevel.SUGHRA
        else -> CompletionLevel.NONE
    }

    fun eveningLevel(): CompletionLevel = when {
        kubraEvening -> CompletionLevel.KUBRA
        sughraEvening -> CompletionLevel.SUGHRA
        else -> CompletionLevel.NONE
    }
}
