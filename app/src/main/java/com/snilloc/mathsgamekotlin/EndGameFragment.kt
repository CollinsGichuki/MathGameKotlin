package com.snilloc.mathsgamekotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.snilloc.mathsgamekotlin.R
import com.snilloc.mathsgamekotlin.databinding.FragmentEndGameBinding

class EndGameFragment : Fragment() {
    private lateinit var binding: FragmentEndGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding Object to inflate the layout for this Fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_end_game, container, false)

        //Cancel button to head back to title
        binding.ibExit.setOnClickListener {
            view: View ->
            view.findNavController().navigate(R.id.action_endGameFragment_to_titleFragment)
        }

        //Restart button to restart the game
        binding.ibRestart.setOnClickListener {
            view: View ->
            view.findNavController().navigate(R.id.action_endGameFragment_to_startGameFragment)
        }

        return binding.root
    }
}