package com.example.questionpicker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.ArrayList
import java.util.Collections

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val totalQuestionsEditText: EditText = findViewById(R.id.totalQuestionsEditText)
        val questionsPerStudentEditText: EditText = findViewById(R.id.questionsPerStudentEditText)
        val totalStudentsEditText: EditText = findViewById(R.id.totalStudentsEditText)
        val generateButton: Button = findViewById(R.id.generateButton)
        val resultTextView: TextView = findViewById(R.id.resultTextView)
        val shareButton: Button = findViewById(R.id.shareButton)

        generateButton.setOnClickListener {
            val numQuestions = totalQuestionsEditText.text.toString().toInt()
            val questionsPerStudent = questionsPerStudentEditText.text.toString().toInt()
            val numStudents = totalStudentsEditText.text.toString().toInt()

            if (numQuestions < questionsPerStudent) {
                resultTextView.text = "Error: Not enough questions to assign $questionsPerStudent questions to each student."
                return@setOnClickListener
            }

            val questions = ArrayList<Int>()
            for (i in 1..numQuestions) {
                questions.add(i)
            }

            val result = StringBuilder()

            for (i in 1..numStudents) {
                result.append("Roll No $i, Questions:\n")

                Collections.shuffle(questions)

                for (j in 0 until questionsPerStudent) {
                    result.append("Question ${questions[j]}\n")
                }

                result.append("\n")
            }

            resultTextView.text = result.toString()
        }

        shareButton.setOnClickListener {
            val resultText = resultTextView.text.toString()
            if (resultText.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, resultText)
                }
                startActivity(Intent.createChooser(intent, "Share Output Text"))
            }
        }
    }
}