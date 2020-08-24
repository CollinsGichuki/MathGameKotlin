package com.snilloc.mathsgamekotlin

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.snilloc.mathsgamekotlin.databinding.FragmentStartGameBinding

class StartGameFragment : Fragment() {
    private lateinit var binding: FragmentStartGameBinding
    private var millsUntilFinished: Long = 5100
    private lateinit var countDownTimer: CountDownTimer
    private val TAG = "Binding"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding Object to inflate the layout for this Fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_start_game, container, false
        )
        //Set the duration
        val timerText = millsUntilFinished / 1000
        binding.tvTimer.text = timerText.toString()

        //Start the Game(countdown)
        startGame()

        return binding.root
    }

    private fun startGame() {
        //CountDownTimer is an abstract class, we can only inherit it
        //We however use Kotlin's Object declaration to initialize it
        countDownTimer = object : CountDownTimer(millsUntilFinished, 1000) {
            override fun onTick(millisNotFinished: Long) {
                Log.d(TAG, "onTick")
                //Countdown the duration
                val seconds = millisNotFinished / 1000
                binding.tvTimer.text = "$seconds s"
            }

            override fun onFinish() {
                Log.d(TAG, "onFinish")
                view?.findNavController()?.navigate(R.id.action_startGameFragment_to_endGameFragment)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        //Start the CountDownTimer
        countDownTimer.start()
        Log.d(TAG, "countDownStarted")
    }

    override fun onPause() {
        super.onPause()
        //Cancel the CountDownTimer
        countDownTimer.cancel()
        Log.d(TAG, "countDownCancelled")
    }
}