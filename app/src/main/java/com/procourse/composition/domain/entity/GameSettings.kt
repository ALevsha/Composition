package com.procourse.composition.domain.entity

data class GameSettings(
    val maxSumValue: Int,// максимальное количество суммы
    val minCountOfRightAnswers: Int,// минимальное количество правильных ответов
    val minPercentsOfRightAnswers: Int,// минимальный процент правильных ответов
    val gameTimeInSeconds: Int// время игры в секунду
)
