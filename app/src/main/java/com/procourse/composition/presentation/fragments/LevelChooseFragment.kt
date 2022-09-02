package com.procourse.composition.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.procourse.composition.R
import com.procourse.composition.databinding.FragmentLevelChooseBinding
import com.procourse.composition.domain.entity.Level

class LevelChooseFragment : Fragment() {

    private var _binding: FragmentLevelChooseBinding? = null
    private val binding: FragmentLevelChooseBinding
        get() = _binding ?: throw RuntimeException("FragmentLevelChooseBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLevelChooseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            testButton.setOnClickListener { launchGameFragment(Level.TEST) }
            easyButton.setOnClickListener { launchGameFragment(Level.EASY) }
            middleButton.setOnClickListener { launchGameFragment(Level.NORMAL) }
            hardButton.setOnClickListener { launchGameFragment(Level.HARD) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFragment(level: Level){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFragment.newInstance(level))
            .addToBackStack(GameFragment.NAME)
            .commit()
    }

    companion object{

        fun newInstance(): LevelChooseFragment {
            return LevelChooseFragment()
        }
    }
}

