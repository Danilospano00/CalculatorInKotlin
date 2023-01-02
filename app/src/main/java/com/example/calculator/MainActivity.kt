package com.example.calculator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var isLastNumeric: Boolean = false
    private var isLastDot: Boolean = false
    private val listOfNumbers = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        isLastNumeric = true
        isLastDot = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
        listOfNumbers.clear()
    }

    fun onDecimalPoint(view: View) {
        if (isLastNumeric && !isLastDot && !tvInput?.text.toString().contains(".")) {
            tvInput?.append(".")
            isLastNumeric = false
            isLastDot = true
        }
    }

    fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*")
                    || value.contains("+") || value.contains("-")
        }
    }

    fun onOperator(view: View) {
        tvInput?.text.let {
            if (isLastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                isLastNumeric = false
                isLastDot
            }

        }
    }

    fun onEqual(view: View) {
        if (isLastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            if (tvValue.startsWith("-")) {
                prefix = "-"
                tvValue = tvValue.substring(1)
            }

            val regex = Regex("[+\\-*/]")
            val operator = regex.find(tvValue)?.value

            if (operator != null) {
                val splitValue = tvValue.split(operator)
                var one = splitValue[0]
                var two = splitValue[1]

                if (prefix.isNotEmpty()) {
                    one = prefix + one
                }

                tvInput?.text = when (operator) {
                    "+" -> (one.toDouble() + two.toDouble()).toString()
                        .removeSuffix(".0")
                    "-" -> (one.toDouble() - two.toDouble()).toString()
                        .removeSuffix(".0")
                    "*" -> (one.toDouble() * two.toDouble()).toString()
                        .removeSuffix(".0")
                    "/" -> (one.toDouble() / two.toDouble()).toString()
                        .removeSuffix(".0")
                    else -> ""
                }
            }
        }
    }
}
