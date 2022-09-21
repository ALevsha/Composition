package com.procourse.composition.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.procourse.composition.domain.entity.Level

/*
* Фабрика нужна для того, чтобы во viewModel можно было передавать параметры, а также чтобы она
* создавалась отдельно от экрана, т.к при его перевороте экран пересоздается.
* */
class GameViewModelFactory( // перечисляются параметры
    private val level: Level,
    private val application: Application
): ViewModelProvider.Factory {

    // переопределяется 1 метод create
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        // производится проверка на соответствие создаваемого класса viewModel
        if (modelClass.isAssignableFrom(GameFragmentViewModel::class.java)) {
            // если да, возвращается объект viewModel
            return GameFragmentViewModel(level = level, application = application) as T
        }
        throw RuntimeException("Unknown view model class $modelClass")
    }
}