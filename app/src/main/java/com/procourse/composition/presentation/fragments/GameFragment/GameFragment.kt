package com.procourse.composition.presentation.fragments.GameFragment

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.procourse.composition.databinding.FragmentGameBinding
import com.procourse.composition.domain.entity.GameResult
import com.procourse.composition.domain.entity.Level
import com.procourse.composition.presentation.viewmodel.GameFragmentViewModel
import com.procourse.composition.presentation.viewmodel.GameViewModelFactory

class GameFragment : Fragment() {

//    private lateinit var level: Level // переменная, хранящая уровень

    // 2 способ получения аргументов при использовании навигации
    private val args by navArgs<GameFragmentArgs>()

    private val modelFactory by lazy { // создание фабрики viewModel с передачей параметров внутрь
        // 1 способ получения аргументов при использовании навигации
        //val args = GameFragmentArgs.fromBundle(requireArguments())
        GameViewModelFactory(args.level, requireActivity().application)
    }

    private val viewModel by lazy { // создание объекта viewModel
        ViewModelProvider(this, modelFactory)[GameFragmentViewModel::class.java]
    }

//    private val tvOptions by lazy {
//        mutableListOf<TextView>().apply {
//            add(binding.tvOption1)
//            add(binding.tvOption2)
//            add(binding.tvOption3)
//            add(binding.tvOption4)
//            add(binding.tvOption5)
//            add(binding.tvOption6)
//        }
//    }

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

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        При применении навигации считывание аргументов выполняется другим способом
        parseArgs()
    }*/

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        * для автоматической подписки на объект liveData из viewModel в dataBinding на вход
        * необходимо подать саму viewModel, а также жизненный цикл прикрепленной activity
        * */
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()
//        setClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun setClickListeners() {
//        for (tvOption in tvOptions)
//            tvOption.setOnClickListener {
//                viewModel.chooseAnswer(tvOption.text.toString().toInt())
//            }
//    }

    private fun observeViewModel() {
        with(viewModel) {
//            question.observe(viewLifecycleOwner) {
//                binding.tvSum.text = it.sum.toString()
//                binding.tvLeftNumber.text = it.visibleNumber.toString()
//
//                for (i in 0 until tvOptions.size)
//                    tvOptions[i].text = it.options[i].toString()
//            }
//            percentOfRightAnswers.observe(viewLifecycleOwner) {
//                binding.progressBar.setProgress(it, true)
//            }
//            enoughCountOfAnswers.observe(viewLifecycleOwner) {
//                val color = getColorByState(it)
//                binding.tvAnswersProgress.setTextColor(color) // установка цвета текста
//            }
//            enoughPercentOfAnswers.observe(viewLifecycleOwner) {
//                val color = getColorByState(it)
//                binding.progressBar.progressTintList = ColorStateList.valueOf(color)
//                // установка цвета progressBar'a
//            }
//            formattedProgress.observe(viewLifecycleOwner) {
//                binding.tvAnswersProgress.text = it
//            }
//            minPercentOfRightAnswers.observe(viewLifecycleOwner) {
//                binding.progressBar.secondaryProgress = it
//            }
//            formattedTime.observe(viewLifecycleOwner) {
//                binding.tvTimer.text = it
//            }
            gameResult.observe(viewLifecycleOwner) {
                launchEndGameFragment(it)
            }
        }
    }



   /* private fun parseArgs() {
        *//* для присвоение используется явное приведение из объекта Serializable к Level.
        * здесь использется requireArguments() чтобы при несоздании фрагмента или неправильного
        * аргумента выбрасывалось исключение
        *//*
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }*/

    private fun launchEndGameFragment(gameResult: GameResult) {
        /*requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, EndGameFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()*/

        /*
        Способ весьма неудобный, т.к необходимо делать константы ключей значений
         другого класса общедоступными
        val args = Bundle().apply {
            putParcelable(EndGameFragment.GAME_RESULT_KEY, gameResult)
        }
        findNavController().navigate(R.id.action_gameFragment_to_endGameFragment, args)*/

        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToEndGameFragment(
                gameResult
            )
        )
    }

//    companion object {
//
//        val NAME = this.javaClass.name
//
//        // ключ для уровня
//        const val KEY_LEVEL = "level"
//
//        fun newInstance(level: Level): GameFragment {
//            // используем apply для работы с экземпляром объекта
//            return GameFragment().apply {
//                // на вход идет новый словарь с уровнем
//                arguments = Bundle().apply {
//                    /*
//                    * чтобы положить в словарь аргументов объект, необходимо привести его
//                    * к набору бит с помощбю интерфейса Serializable b=или Parcelable.
//                    * Enum класс неявно реализует этот интерфейс.
//                    */
//                    putParcelable(KEY_LEVEL, level)
//                }
//            }
//        }
//    }
}