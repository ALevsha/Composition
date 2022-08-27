package com.procourse.composition.domain.usecase

import com.procourse.composition.domain.entity.GameSettings
import com.procourse.composition.domain.entity.Level
import com.procourse.composition.domain.entity.Question
import com.procourse.composition.domain.repository.GameRepository

// получает настройки игры в зависимости от уровня
class GenerateQuestionUseCase(
    private val repository: GameRepository
) {
    /*Так как метод юзкейса является единичным (больше класс ничего не делает)
    и называется также как и класс, в Котлин сделали возможность работать с объектом
     как с методом с помощью оператора invoke*/
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6 // количество вариантов ответов
        // хранится в константе, т.к является частью бизнес-логики
    }

}
