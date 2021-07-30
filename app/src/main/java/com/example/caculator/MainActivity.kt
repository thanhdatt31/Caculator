package com.example.caculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.caculator.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var number: String? = null
    private var firstNumber: Double = 0.0
    private var lastNumber: Double = 0.0
    private var status: String? = null
    private var operator: Boolean = false
    private var myFormatter = DecimalFormat("######.######")
    private var history: String? = null
    private var currentResult: String? = null
    private var dot = true
    private var btnAcControl = true
    private var btnEqualsControl = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButton()
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.num0 -> {
                    numberClick("0")
                }
                R.id.num1 -> {
                    numberClick("1")
                }
                R.id.num2 -> {
                    numberClick("2")
                }
                R.id.num3 -> {
                    numberClick("3")
                }
                R.id.num4 -> {
                    numberClick("4")
                }
                R.id.num5 -> {
                    numberClick("5")
                }
                R.id.num6 -> {
                    numberClick("6")
                }
                R.id.num7 -> {
                    numberClick("7")
                }
                R.id.num8 -> {
                    numberClick("8")
                }
                R.id.num9 -> {
                    numberClick("9")
                }
                R.id.clear -> {
                    number = null
                    status = null
                    binding.answer.text = "0"
                    binding.textViewHistory.text = ""
                    firstNumber = 0.0
                    lastNumber = 0.0
                    dot = true
                    btnAcControl = true

                }
                R.id.actionBack -> {
                    if (btnAcControl) {
                        binding.answer.text = "0"
                    } else {
                        number = number?.substring(0, number!!.length - 1)
                        if (number?.isEmpty() == true) {
                            binding.actionBack.isClickable = false
                        } else dot = !number?.contains(".")!!
                        binding.answer.text = number
                    }
                }
                R.id.actionAdd -> {
                    history = binding.textViewHistory.text.toString()
                    currentResult = binding.answer.text.toString()
                    binding.textViewHistory.text = "$history$currentResult+"
                    if (operator) {
                        when (status) {
                            "multiplication" -> {
                                multiply()
                            }
                            "division" -> {
                                divide()
                            }
                            "subtraction" -> {
                                minus()
                            }
                            else -> {
                                plus()
                            }
                        }

                    }
                    status = "sum"
                    operator = false
                    number = null
                }
                R.id.actionMinus -> {
                    history = binding.textViewHistory.text.toString()
                    currentResult = binding.answer.text.toString()
                    binding.textViewHistory.text = "$history$currentResult-"
                    if (operator) {
                        when (status) {
                            "multiplication" -> {
                                multiply()
                            }
                            "division" -> {
                                divide()
                            }
                            "sum" -> {
                                plus()
                            }
                            else -> {
                                minus()
                            }
                        }
                    }
                    status = "subtraction"
                    operator = false
                    number = null
                }
                R.id.actionMultiply -> {
                    history = binding.textViewHistory.text.toString()
                    currentResult = binding.answer.text.toString()
                    binding.textViewHistory.text = "$history$currentResult*"
                    if (operator) {
                        when (status) {
                            "sum" -> {
                                plus()
                            }
                            "division" -> {
                                divide()
                            }
                            "subtraction" -> {
                                minus()
                            }
                            else -> {
                                multiply()
                            }
                        }
                    }
                    status = "multiplication"
                    operator = false
                    number = null
                }
                R.id.actionDivide -> {
                    history = binding.textViewHistory.text.toString()
                    currentResult = binding.answer.text.toString()
                    binding.textViewHistory.text = "$history$currentResult/"
                    if (operator) {
                        when (status) {
                            "multiplication" -> {
                                multiply()
                            }
                            "sum" -> {
                                plus()
                            }
                            "subtraction" -> {
                                minus()
                            }
                            else -> {
                                divide()
                            }
                        }
                    }
                    status = "division"
                    operator = false
                    number = null
                }
                R.id.actionEquals -> {
                    if (operator) {
                        when (status) {
                            "sum" -> {
                                plus()
                            }
                            "subtraction" -> {
                                minus()
                            }
                            "multiplication" -> {
                                multiply()
                            }
                            "division" -> {
                                divide()
                            }
                            else -> {
                                firstNumber =
                                    java.lang.Double.parseDouble(binding.answer.text.toString())
                            }
                        }
                    }

                    operator = false
                    btnEqualsControl = true
                }
                R.id.numDot -> {
                    if (dot) {
                        if (number == null) {
                            number = "0."
                        } else {
                            number += "."
                        }
                    }

                    binding.answer.text = number
                    dot = false
                }
            }
        }
    }

    private fun numberClick(view: String) {
        when {
            number == null -> {
                number = view
            }
            btnEqualsControl -> {
                firstNumber = 0.0
                lastNumber = 0.0
                number = view
            }
            else -> {
                number += view
            }
        }
        binding.answer.text = number
        operator = true
        btnAcControl = false
        binding.actionBack.isClickable = true
        btnEqualsControl = false
    }

    @SuppressLint("SetTextI18n")
    private fun plus() {
        lastNumber = java.lang.Double.parseDouble(binding.answer.text.toString())
        firstNumber += lastNumber
        binding.answer.text = myFormatter.format(firstNumber)
        dot = true
    }

    @SuppressLint("SetTextI18n")
    private fun minus() {
        if (firstNumber == 0.0) {
            firstNumber = java.lang.Double.parseDouble(binding.answer.text.toString())
        } else {
            lastNumber = java.lang.Double.parseDouble(binding.answer.text.toString())
            firstNumber -= lastNumber
        }
        binding.answer.text = myFormatter.format(firstNumber)
        dot = true
    }

    @SuppressLint("SetTextI18n")
    private fun multiply() {
        if (firstNumber == 0.0) {
            firstNumber = 1.0
            lastNumber = java.lang.Double.parseDouble(binding.answer.text.toString())
            firstNumber *= lastNumber
        } else {
            lastNumber = java.lang.Double.parseDouble(binding.answer.text.toString())
            firstNumber *= lastNumber
        }
        binding.answer.text = myFormatter.format(firstNumber)
        dot = true
    }

    @SuppressLint("SetTextI18n")
    private fun divide() {
        if (firstNumber == 0.0) {

            lastNumber = java.lang.Double.parseDouble(binding.answer.text.toString())
            firstNumber = lastNumber / 1
        } else {
            lastNumber = java.lang.Double.parseDouble(binding.answer.text.toString())
            firstNumber /= lastNumber
        }
        binding.answer.text = myFormatter.format(firstNumber)
        dot = true
    }

    private fun initButton() {
        binding.num0.setOnClickListener(this)
        binding.num1.setOnClickListener(this)
        binding.num2.setOnClickListener(this)
        binding.num3.setOnClickListener(this)
        binding.num4.setOnClickListener(this)
        binding.num5.setOnClickListener(this)
        binding.num6.setOnClickListener(this)
        binding.num7.setOnClickListener(this)
        binding.num8.setOnClickListener(this)
        binding.num9.setOnClickListener(this)
        binding.actionBack.setOnClickListener(this)
        binding.actionAdd.setOnClickListener(this)
        binding.actionDivide.setOnClickListener(this)
        binding.actionMinus.setOnClickListener(this)
        binding.actionEquals.setOnClickListener(this)
        binding.actionMultiply.setOnClickListener(this)
        binding.clear.setOnClickListener(this)
    }
}