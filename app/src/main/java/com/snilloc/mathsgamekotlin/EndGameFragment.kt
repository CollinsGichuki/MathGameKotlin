package com.snilloc.mathsgamekotlin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.snilloc.mathsgamekotlin.R
import com.snilloc.mathsgamekotlin.databinding.FragmentEndGameBinding

class EndGameFragment : Fragment() {
    private lateinit var binding: FragmentEndGameBinding

    //args from StartGameFragment
    private val args: EndGameFragmentArgs by navArgs()
    private val prefName = "sharedPref"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding Object to inflate the layout for this Fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_end_game, container, false)

        //Get the storedSharedPrefPoints
        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        var storedPoints = sharedPreferences?.getInt(prefName, 0)

        //Get the points from the args
        val points = args.points

        //Store the points using shared prefs
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()

        if (points > storedPoints!!) {
            storedPoints = points
            //Store the new high points
            editor?.putInt(prefName, storedPoints)
            editor?.apply()

            //Show high score
            binding.ivHighScore.visibility = View.VISIBLE
        }
        //Populate the view with the points
        binding.tvPoints.text = points.toString()

        //Populate the best score
        binding.tvBestScore.text = storedPoints.toString()

        //Cancel button to head back to title
        binding.ibExit.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_endGameFragment_to_titleFragment)
        }

        //Restart button to restart the game
        binding.ibRestart.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_endGameFragment_to_startGameFragment)
        }

        return binding.root
    }
}