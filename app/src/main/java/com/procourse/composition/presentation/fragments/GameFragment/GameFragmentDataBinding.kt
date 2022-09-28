package com.procourse.composition.presentation.fragments.GameFragment

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

interface OnOptionClickListener {
    /*
    * Также такой интерфейс называют функциональным
    * */
    fun OnOptionClick(option: Int)
}

@BindingAdapter("sum")
fun bindSum(textView: TextView, count: Int) {
    textView.text = count.toString()
}

@BindingAdapter("leftNumber")
fun bindLeftNumber(textView: TextView, count: Int) {
    textView.text = count.toString()
}

@BindingAdapter("progressBar")
fun bindProgressBar(prgressBar: ProgressBar, count: Int) {
    prgressBar.setProgress(count, true)
}

//@BindingAdapter("secondaryProgressBar")
//fun bindSecondaryProgressBar(progressBar: ProgressBar, count: Int){
//    progressBar.secondaryProgress = count
//}

@BindingAdapter("enoughCountOfAnswers")
fun bindEnoughCountOfAnswers(textView: TextView, state: Boolean) {
    val color = getColorByState(textView.context, state)
    textView.setTextColor(color)
}

@BindingAdapter("enoughPercentOfAnswers")
fun bindEnoughPercentOfAnswers(progressBar: ProgressBar, state: Boolean) {
    val color = getColorByState(progressBar.context, state)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}

/*
* для установки слушателей на view внутрь BindingAdapter передается view, на который
* необходимо установить слушатель и функцию, вида fun: (<входные_параметры_через_запятую>)
* Unit значит, что функция ничего не возвращает. Именно по этому эта функция является
* вторым параметром.
*
* В BindingAdapter нельзя передавать лямбды, т.к он считает, что метода из viewModel
* нет, а есть другой с таким же названием, который на вход принимает object.
* Для обхода используется явное приведение к методу интерфейса с 1 методом,
* где явно указывается Int
* */
@BindingAdapter("onOptionClickListener")
fun bindOfOptionClickListener(
    textView: TextView,
    clickListener: /*(Int) -> Unit)*/ OnOptionClickListener
) {
    textView.setOnClickListener {
        clickListener.OnOptionClick(textView.text.toString().toInt())
    }
}

