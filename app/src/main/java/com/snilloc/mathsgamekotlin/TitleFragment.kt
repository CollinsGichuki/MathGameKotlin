package com.snilloc.mathsgamekotlin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.snilloc.mathsgamekotlin.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding Object to inflate the layout for this Fragment
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            inflater, R.layout.fragment_title, container, false
        )
        //Click listener for the button
        binding.imageButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_startGameFragment)
        }

        return binding!!.root
    }
}