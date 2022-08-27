package com.procourse.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.procourse.composition.R
import com.procourse.composition.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    /*
    * Для использования viewBinding сперва в файле build.gradle добавить в android{...}:
    * buildFeatures{
        viewBinding = true
      }
    * ViewBinding используется для упрощения получения ссылки на элементы view макета.
    * Чтобы не использовать findViewById(). Под капотом для каждого элемента макета
    * автоматически будет создан класс с названием в CamelCase стиле.
    * Создаются две переменные, т.к к нуллабельной можно обратиться в любой момент
    * жизненного цикла фрагмента. Т.о в сообществе принято работать с viewBinding
    * во фрагментах
    *  */
    private var _binding: FragmentStartBinding? = null
    private val binding: FragmentStartBinding
        get() = _binding ?: throw RuntimeException("FragmentStartBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View/*?*/ {
        // Inflate the layout for this fragment
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.proceedButton.setOnClickListener {
            launchLevelChooseFragment()
        }

    }

    // обнуление viewBinding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchLevelChooseFragment() {
        /*
        * хорошим стилем программирования является однотипное создание объектов. Для фрагментов
        * хорошим способом является создание экземпляра фрагмента через фабричный метод (в companion
        * object создается публичный статичный метод, возвращающий экземпляр фрагмента. Также в этом
        * методе  можно загрузить параметры во фрагмент.
        * */
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, LevelChooseFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}