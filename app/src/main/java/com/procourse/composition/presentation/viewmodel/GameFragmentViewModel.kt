package com.procourse.composition.presentation.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.procourse.composition.R
import com.procourse.composition.data.GameRepositoryImpl
import com.procourse.composition.domain.entity.GameResult
import com.procourse.composition.domain.entity.GameSettings
import com.procourse.composition.domain.entity.Level
import com.procourse.composition.domain.entity.Question
import com.procourse.composition.domain.usecase.GenerateQuestionUseCase
import com.procourse.composition.domain.usecase.GetGameSettingsUseCase

class GameFragmentViewModel(application: Application) : AndroidViewModel(
    application
) {
    private val repository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private lateinit var gameSettings: GameSettings // настройки сложности
    private lateinit var level: Level // уровень сложности
    private var timer: CountDownTimer? = null // таймер отсчета времени по уровню сложности
    private val context = application // контекст для получения строки из строковых ресурсов

    private val _formattedTime = MutableLiveData<String>() // лайвдата строки таймера
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _question = MutableLiveData<Question>() // лайвдата вопроса
    val question: LiveData<Question>
        get() = _question

    private var countOfRightAnswers = 0 // количество правильных ответов
    private var countOfAnswers = 0 // количество ответов

    private val _percentOfRightAnswers = MutableLiveData<Int>() // процент правильных ответов
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _minPercentOfRightAnswers = MutableLiveData<Int>() // минимальный процент

    // правильных ответов для вторичного прогресса (второй граничный прогресс)
    val minPercentOfRightAnswers: LiveData<Int>
        get() = _minPercentOfRightAnswers

    private val _enoughCountOfAnswers = MutableLiveData<Boolean>() // достигнуто ли

    // достаточное количество правильных ответов
    val enoughCountOfAnswers: LiveData<Boolean>
        get() = _enoughCountOfAnswers

    private val _enoughPercentOfAnswers = MutableLiveData<Boolean>() // достигнут ли

    // достаточный процент правильных ответов
    val enoughPercentOfAnswers: LiveData<Boolean>
        get() = _enoughPercentOfAnswers

    private val _formattedProgress = MutableLiveData<String>() // строка прогресса
    val formattedProgress: LiveData<String>
        get() = _formattedProgress

    private val _gameResult = MutableLiveData<GameResult>() // результат выполнения
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    /* Сперва при создании экрана в качестве параметра прилетит уровень.
    * Необходимо получить этот уровень и сгенерить настройки для этого уровня.*/

    fun startGame(level: Level) {
        getSettings(level) // получение настроек
        startTimer() // запуск таймера
        generateQuestion() // генерация первого вопроса
        updateProgress()
    }

    fun chooseAnswer(number: Int) { // выбор ответа
        checkAnswer(number) // проверка ответа
        generateQuestion() // получение нового вопроса
        updateProgress() // обновление прогресса
    }

    private fun updateProgress() { // обновление прогресса
        val percent = calculateProgressPercent()
        _percentOfRightAnswers.value = percent
        _formattedProgress.value = String.format(
            context.getString(R.string.right_answer_strings),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCountOfAnswers.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercentOfAnswers.value = percent >= gameSettings.minPercentsOfRightAnswers
    }

    private fun calculateProgressPercent(): Int {
        // подсчет прогресса
        if (countOfAnswers == 0)
            return 0
        return ((countOfRightAnswers / countOfAnswers.toDouble()) * PERCENT_CONST).toInt()
    }

    private fun generateQuestion() { // получение нового вопроса
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun checkAnswer(number: Int) { // проверка ответа на правильность
        val rightAnswer = _question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfAnswers++
    }

    private fun getSettings(level: Level) { // получение настроек при старте игры
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level = level)
        _minPercentOfRightAnswers.value = gameSettings.minPercentsOfRightAnswers
    }

    private fun startTimer() { // запуск таймера
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS, // количество миллисекунд
            MILLIS_IN_SECONDS // интервал 1 тика
        ) {
            override fun onTick(p0: Long) { // действие при каждом тике
                _formattedTime.value = getFormatTimer(p0)
            }

            override fun onFinish() { // действие при окончании таймера
                gameFinished()
            }
        }
        timer?.start()
    }

    private fun getFormatTimer(millisInFuture: Long): String { // возврат строки
        // с оставшимся временем
        val seconds = millisInFuture / MILLIS_IN_SECONDS
        val minuets = seconds / SECONDS_IN_MINUETS
        val leftSeconds = seconds % SECONDS_IN_MINUETS
        return String.format("%02d:%02d", minuets, leftSeconds)
    }

    private fun gameFinished() { // окончание игры, фиксирование результатов в LD _gameResult
        _gameResult.value = GameResult(
            winner = _enoughCountOfAnswers.value == true && _enoughPercentOfAnswers.value == true,
            countOfRightAnswers = countOfRightAnswers,
            countOfQuestions = countOfAnswers,
            gameSettings = gameSettings
        )
    }

    override fun onCleared() { // действие при уходе с экрана
        super.onCleared()
        timer?.cancel() // остановка таймера
    }

    companion object {

        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUETS = 60
        private const val PERCENT_CONST = 100

    }
}