package com.procourse.composition.presentation.fragments.EndGameFragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.procourse.composition.databinding.FragmentEndGameBinding
import com.procourse.composition.domain.entity.GameResult

class EndGameFragment : Fragment() {

    //private lateinit var gameResult: GameResult

    private val args by navArgs<com.procourse.composition.presentation.fragments.EndGameFragmentArgs>()
    private val gameResult by lazy { args.gameResult }

    private var _binding: FragmentEndGameBinding? = null
    private val binding: FragmentEndGameBinding
        get() = _binding ?: throw RuntimeException("FragmentEndGameBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEndGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgument()
    }*/

    @SuppressLint("StringFormatMatches")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /* в фрагментах нет обработчика нажатия на кнопку "назад", для этого используется
        * объект прикрепленной активити onBackPressedDispatcher и его метода addCallback(),
        * который может принимать жизненный цикл фрагмента (может и не принимать, если не принимает,
        * то при уничтожении фрагмента ссылка на него также будет доступна, что приводит к утечке
        * памяти) и объект анонимного класса OnBackPressedCallback, в котором будет переопределен
        * метод handleOnBackPressed()*/
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        setClickListeners(callback)
        bindViews()
    }

    private fun setClickListeners(callback: OnBackPressedCallback) {
        /*
        Устанавливать слушатель на нажатие кнопки назад более не нужно, т.к в бекстек значение
        кладет AndroidNavigation:

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            callback
        )*/
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun bindViews() {
        // прикол dataBinding в том, чтобы посылать объект с данными напрямую в макет формы,
        /*
        * в макете создается переменная:
        * <data>
        <variable
            name="gameResult"
            type="com.procourse.composition.domain.entity.GameResult" />
    </data>
    * и внутрь этой переменной через dataBinding подается нужный объект
        * */
        binding.gameResult = args.gameResult
//        with(binding) {
//            if (gameResult.winner)
//                emojiResult.setImageResource(R.drawable.happy)
//            else
//                emojiResult.setImageResource(R.drawable.cry)
//            tvRequiredAnswers.text = String.format(
//                requireContext().getString(
//                    R.string.src_required_answers,
//                    gameResult.gameSettings.minCountOfRightAnswers
//                )
//            )
//            tvScoreAnswers.text = String.format(
//                requireContext().getString(R.string.src_score_answers),
//                gameResult.countOfRightAnswers
//            )
//            tvRequiredPercentage.text = String.format(
//                requireContext().getString(R.string.str_required_percentage),
//                gameResult.gameSettings.minPercentsOfRightAnswers
//            )
//            tvScorePercentage.text = String.format(
//                requireContext().getString(R.string.str_score_percentage),
//                calculateProgressPercent()
//            )
//        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*private fun parseArgument() {
        *//* при использовании getParcelable приходит нуллабельный объект, => для получения
        * значений можно использовать оператор let {lambda}. В угловых скобках необходимо
        * указать получаемый тип*//*
        requireArguments().getParcelable<GameResult>(GAME_RESULT_KEY)?.let {
            gameResult = it
        }
    }*/

    private fun retryGame() {
        /* чтобы пропустить предидущий фрагмент, при нажатии кнопки назад сперва переходят к нему
        * по заданному имени фрагмента с флагом включения на очистку backStack'а
        * FragmentManager.POP_BACK_STACK_INCLUSIVE*//*
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )*/
        findNavController().popBackStack()
    }

    companion object {

        const val GAME_RESULT_KEY = "result"

        fun newInstance(gameResult: GameResult): EndGameFragment {
            return EndGameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(GAME_RESULT_KEY, gameResult)
                }
            }
        }
    }
}