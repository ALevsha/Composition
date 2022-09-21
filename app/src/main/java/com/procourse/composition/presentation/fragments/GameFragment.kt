package com.procourse.composition.presentation.fragments

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.procourse.composition.R
import com.procourse.composition.data.GameRepositoryImpl
import com.procourse.composition.databinding.FragmentGameBinding
import com.procourse.composition.domain.entity.GameResult
import com.procourse.composition.domain.entity.Level
import com.procourse.composition.domain.usecase.GetGameSettingsUseCase
import com.procourse.composition.presentation.viewmodel.GameFragmentViewModel

class GameFragment : Fragment() {

    private lateinit var level: Level // переменная, хранящая уровень

    private lateinit var viewModel: GameFragmentViewModel

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GameFragmentViewModel::class.java]
        viewModel.startGame(level)
        with(binding) {
            // подписка на изменения таймера
            viewModel.formattedTime.observe(viewLifecycleOwner, Observer { newTime ->
                tvTimer.text = newTime.toString()
            })

            // подписка на вопрос, установка слушателей на нажатие ответов
            viewModel.question.observe(viewLifecycleOwner, Observer { newQuestion ->
                tvSum.text = newQuestion.sum.toString()
                tvLeftNumber.text = newQuestion.visibleNumber.toString()

                tvOption1.text = newQuestion.options[0].toString()
                tvOption1.setOnClickListener {
                    viewModel.chooseAnswer(tvOption1.text.toString().toInt())
                }

                tvOption2.text = newQuestion.options[1].toString()
                tvOption2.setOnClickListener {
                    viewModel.chooseAnswer(tvOption2.text.toString().toInt())
                }

                tvOption3.text = newQuestion.options[2].toString()
                tvOption3.setOnClickListener {
                    viewModel.chooseAnswer(tvOption3.text.toString().toInt())
                }

                tvOption4.text = newQuestion.options[3].toString()
                tvOption4.setOnClickListener {
                    viewModel.chooseAnswer(tvOption4.text.toString().toInt())
                }

                tvOption5.text = newQuestion.options[4].toString()
                tvOption5.setOnClickListener {
                    viewModel.chooseAnswer(tvOption5.text.toString().toInt())
                }

                tvOption6.text = newQuestion.options[5].toString()
                tvOption6.setOnClickListener {
                    viewModel.chooseAnswer(tvOption6.text.toString().toInt())
                }
            })

            viewModel.percentOfRightAnswers.observe(viewLifecycleOwner, Observer { newPercent ->
                viewModel.minPercentOfRightAnswers.observe(
                    viewLifecycleOwner,
                    Observer { newMinPercent ->
                        progressBar.secondaryProgress = newMinPercent
                    })
                viewModel.enoughCountOfAnswers.observe(
                    viewLifecycleOwner,
                    Observer { enoughtCount ->
                        viewModel.enoughPercentOfAnswers.observe(
                            viewLifecycleOwner,
                            Observer { enoughtPercent ->
                                progressBar.setProgress(
                                    newPercent,
                                    true
                                )
                                if (enoughtPercent)
                                    progressBar.progressTintList =
                                        ColorStateList.valueOf(Color.GREEN)
                                else
                                    progressBar.progressTintList = ColorStateList.valueOf(Color.RED)
                                if (enoughtCount)
                                    tvAnswersProgress.setTextColor(Color.GREEN)
                                else
                                    tvAnswersProgress.setTextColor(Color.RED)
                            })
                    })
            })

            viewModel.formattedProgress.observe(
                viewLifecycleOwner,
                Observer { newFormattedProgress ->
                    tvAnswersProgress.text = newFormattedProgress.toString()
                })

            viewModel.gameResult.observe(viewLifecycleOwner, Observer { newGameResult ->
                gameResult = newGameResult
                launchEndGameFragment(gameResult)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        /* для присвоение используется явное приведение из объекта Serializable к Level.
        * здесь использется requireArguments() чтобы при несоздании фрагмента или неправильного
        * аргумента выбрасывалось исключение
        */
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    private fun launchEndGameFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, EndGameFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object {

        val NAME = this.javaClass.name

        // ключ для уровня
        private const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {
            // используем apply для работы с экземпляром объекта
            return GameFragment().apply {
                // на вход идет новый словарь с уровнем
                arguments = Bundle().apply {
                    /*
                    * чтобы положить в словарь аргументов объект, необходимо привести его
                    * к набору бит с помощбю интерфейса Serializable b=или Parcelable.
                    * Enum класс неявно реализует этот интерфейс.
                    */
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}