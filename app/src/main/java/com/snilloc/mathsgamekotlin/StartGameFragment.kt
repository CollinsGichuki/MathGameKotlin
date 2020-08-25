package com.snilloc.mathsgamekotlin

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.snilloc.mathsgamekotlin.databinding.FragmentStartGameBinding
import kotlinx.android.synthetic.main.fragment_start_game.*

class StartGameFragment : Fragment() {
    private val TAG = "Binding"
    private lateinit var binding: FragmentStartGameBinding
    private var millsUntilFinished: Long = 30100 //30 seconds
    private lateinit var countDownTimer: CountDownTimer
    private var points = 0
    private var numOfQuestions = 0
    //The questions
    private var operand1 = 0
    private var operand2 = 0
    private val mathOperators = arrayOf("+", "-", "*", "÷")
    private var random = java.util.Random()
    //The answers
    private var correctAnswer = 0
    private var correctAnswerPosition = 0
    private var incorrectAnswersList = mutableListOf<Int>()
    private var nonDuplicateIncorrectAnswersList = mutableListOf<Int>()
    private var incorrectAnswer = 0
    //Array to hold the buttons
    private lateinit var buttonsIds: Array<Button>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding Object to inflate the layout for this Fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_start_game, container, false
        )
        //Initialize the array
        buttonsIds = arrayOf(binding.button0, binding.button1, binding.button2, binding.button3)

        //ClickListeners for the buttons
        binding.button0.setOnClickListener { chooseAnswer(button0) }
        binding.button1.setOnClickListener { chooseAnswer(button1) }
        binding.button2.setOnClickListener { chooseAnswer(button2) }
        binding.button3.setOnClickListener { chooseAnswer(button3) }

        //Start the Game
        startGame()

        return binding.root
    }

    private fun chooseAnswer(button: Button) {
        //Button clicked
        val btnAnswer = button.text
        if (btnAnswer == correctAnswer.toString()){
            points++
            //Show correct
            binding.tvResult.text = "Correct!!"
        } else {
            binding.tvResult.text = "Incorrect"
        }
        //Populate the points
        val pointText = "$points / $numOfQuestions"
        binding.tvPoints.text = pointText

        generateQuestions()
    }

    private fun startGame() {
        //Set the duration
        val timerText = "$millsUntilFinished / 1000"
        binding.tvTimer.text = timerText
        //Populate the points
        val pointText = "$points / $numOfQuestions"
        binding.tvPoints.text = pointText

        //Generate Questions
        generateQuestions()

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
                //Navigate to the endGame Fragment and send the points data
                val action = StartGameFragmentDirections.actionStartGameFragmentToEndGameFragment(points)
                view?.findNavController()?.navigate(action)
            }
        }
    }

    private fun generateQuestions() {
        //Increase the number of questions
        numOfQuestions++

        //The operands
        operand1 = random.nextInt(10)
        //Since there is division, we don't want a 0 hence 1-9
        operand2 = 1 + random.nextInt(9)

        /*
        String to hold the selected operator
        operatorArray receives a random number between 0-3
        */
        val selectedOperator = mathOperators[random.nextInt(4)]

        //Set the questions
        val mathOperationText = "$operand1 $selectedOperator $operand2 =  "
        binding.tvMathOperation.text = mathOperationText
        //Get the answer for the question
        correctAnswer = generateAnswer(selectedOperator)
        //The correct answer position generated can be from 0-3(4 buttons)
        correctAnswerPosition = random.nextInt(4)

        //Populate the correct answer
        buttonsIds[correctAnswerPosition].text = correctAnswer.toString()

        //IncorrectAnswers
        while (nonDuplicateIncorrectAnswersList.size <= 3) {
            //Generate incorrect answers that are similar to the correct answer
            operand1 = random.nextInt(10)
            operand2 = 1 + random.nextInt(9)
            val selectedOperator = mathOperators[random.nextInt(4)]
            incorrectAnswer = generateAnswer(selectedOperator)
            if (incorrectAnswer != correctAnswer){
                //Add the incorrect answers
                incorrectAnswersList.add(incorrectAnswer)
            }
            //Remove duplicates
            nonDuplicateIncorrectAnswersList = incorrectAnswersList.distinct().toMutableList()
        }
        //Display the incorrect answers
        for (i in 0..3 ){
            if (i != correctAnswerPosition){
                //Show non duplicate wrong answers
                buttonsIds[i].text = nonDuplicateIncorrectAnswersList[i].toString()
            }
        }
        //Clear the incorrect answers
        incorrectAnswersList.clear()
        nonDuplicateIncorrectAnswersList.clear()
    }

    private fun generateAnswer(selectedOperator: String): Int {
        var answer = 0
        when (selectedOperator) {
            "+" -> answer = operand1 + operand2
            "-" -> answer = operand1 - operand2
            "*" -> answer = operand1 * operand2
            "÷" -> answer = operand1 / operand2
        }
        return answer
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