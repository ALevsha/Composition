package com.procourse.composition.presentation.fragments.EndGameFragment

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.procourse.composition.R
import com.procourse.composition.domain.entity.GameResult

@BindingAdapter("emoji_result")
fun bindEmojiResult(imageView: ImageView, result: Boolean){
    if (result)
        imageView.setImageResource(R.drawable.happy)
    else
        imageView.setImageResource(R.drawable.cry)
}


@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int){
    textView.text = String.format(
        textView.context.getString(
            R.string.src_required_answers,
            count.toString()
        )
    )
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, count: Int){
    textView.text = String.format(
        textView.context.getString(R.string.src_score_answers),
        count.toString()
    )
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, count: Int){
    textView.text = String.format(
        textView.context.getString(R.string.str_required_percentage),
        count.toString()
    )
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult){
    textView.text = String.format(
        textView.context.getString(R.string.str_score_percentage),
        calculateProgressPercent(gameResult).toString()
    )
}
private fun calculateProgressPercent(gameResult: GameResult): Int {
    // подсчет прогресса
    if (gameResult.countOfQuestions == 0)
        return 0
    return ((gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble()) * 100).toInt()
}


