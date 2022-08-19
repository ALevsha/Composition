package com.procourse.composition.domain.repository

import com.procourse.composition.domain.entity.GameSettings
import com.procourse.composition.domain.entity.Level
import com.procourse.composition.domain.entity.Question
import com.procourse.composition.domain.usecase.GetGameSettingsUseCase

interface GameRepository {
    fun generateQuestion(
        maxSumValue : Int,
        countOfOptions: Int
        ) : Question

    fun getGameSettings(level: Level) : GameSettings
}