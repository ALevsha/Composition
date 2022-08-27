package com.procourse.composition.domain.usecase

import com.procourse.composition.domain.entity.GameSettings
import com.procourse.composition.domain.entity.Level
import com.procourse.composition.domain.entity.Question
import com.procourse.composition.domain.repository.GameRepository

// генерирует вопрос
class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}