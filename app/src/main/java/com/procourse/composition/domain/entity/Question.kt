package com.procourse.composition.domain.entity

data class Question(
    val sum: Int, // сумма
    val visibleNumber: Int, // видимое число
    val options: List<Int> // варианты ответов
)
