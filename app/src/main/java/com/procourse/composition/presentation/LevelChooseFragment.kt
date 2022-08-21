package com.procourse.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.procourse.composition.R
import com.procourse.composition.databinding.FragmentLevelChooseBinding

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
        binding.testButton.setOnClickListener {  }
        binding.easyButton.setOnClickListener {  }
        binding.middleButton.setOnClickListener {  }
        binding.hardButton.setOnClickListener {  }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

