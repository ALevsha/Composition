package com.procourse.composition.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is null")

    private val repository = GameRepositoryImpl

    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GameFragmentViewModel::class.java]
        binding.tvOption1.setOnClickListener{
            launchEndGameFragment(GameResult(
                winner = true,
                countOfRightAnswers = 2,
                countOfQuestions = 3,
                getGameSettingsUseCase(level)
            ))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs(){
        /* для присвоение используется явное приведение из объекта Serializable к Level.
        * здесь использется requireArguments() чтобы при несоздании фрагмента или неправильного
        * аргумента выбрасывалось исключение
        */
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    private fun launchEndGameFragment(gameResult: GameResult){
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