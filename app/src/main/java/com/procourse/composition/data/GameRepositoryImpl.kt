package com.procourse.composition.data

import com.procourse.composition.domain.entity.GameSettings
import com.procourse.composition.domain.entity.Level
import com.procourse.composition.domain.entity.Question
import com.procourse.composition.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = min(maxSumValue, rightAnswer + countOfOptions)
        while (options.size < countOfOptions)
            options.add(Random.nextInt(from, to))
        return Question(sum = sum, visibleNumber = visibleNumber, options =  options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level){
            Level.TEST -> GameSettings(
                maxSumValue = 10,
                minCountOfRightAnswers = 3,
                minPercentsOfRightAnswers = 50,
                gameTimeInSeconds = 8
            )
            Level.EASY -> GameSettings(
                maxSumValue = 20,
                minCountOfRightAnswers = 10,
                minPercentsOfRightAnswers = 70,
                gameTimeInSeconds = 60
            )
            Level.NORMAL -> GameSettings(
                maxSumValue = 30,
                minCountOfRightAnswers = 20,
                minPercentsOfRightAnswers = 80,
                gameTimeInSeconds = 40
            )
            Level.HARD -> GameSettings(
                maxSumValue = 30,
                minCountOfRightAnswers = 30,
                minPercentsOfRightAnswers = 90,
                gameTimeInSeconds = 40
            )
        }
    }
}