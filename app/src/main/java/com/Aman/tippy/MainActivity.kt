package com.Aman.tippy

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENTAGE = 15

class MainActivity : AppCompatActivity() {
    private lateinit var baseAmount_et : EditText
    private lateinit var seekbar : SeekBar
    private lateinit var tipAmount_tv : TextView
    private lateinit var totalAmount_tv : TextView
    private lateinit var tipPercentage_tv : TextView
    private lateinit var tipDescription_tv : TextView
    private lateinit var seekBarPerPerson_tv : SeekBar
    private lateinit var splitAmount_tv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        baseAmount_et = findViewById(R.id.baseAmount_et)
        seekbar = findViewById(R.id.seekBar)
        tipAmount_tv = findViewById(R.id.tipAmount_tv)
        totalAmount_tv = findViewById(R.id.totalAmount_tv)
        tipPercentage_tv = findViewById(R.id.tipPercentage_tv)
        tipDescription_tv = findViewById(R.id.tipDescription_tv)
        seekBarPerPerson_tv = findViewById(R.id.seekBarPerPerson_tv)
        splitAmount_tv = findViewById(R.id.splitAmount_tv)

        seekbar.progress = INITIAL_TIP_PERCENTAGE
        tipPercentage_tv.text = "$INITIAL_TIP_PERCENTAGE%"
        updateTipDescrption(INITIAL_TIP_PERCENTAGE)

        // For Seekbar
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // progress : the value of seekbar
                tipPercentage_tv.text = "$progress%"
                computeTipAndTotal()
                updateTipDescrption(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        baseAmount_et.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG,"afterTextChanged $s")
                computeTipAndTotal()
            }
        })

        seekBarPerPerson_tv.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "after sliding the bar $progress")
                splitBill(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

    }

    private fun splitBill(progress : Int) {
        if(baseAmount_et.text.isEmpty()){
            return
        }
        val baseAmount = baseAmount_et.text.toString().toDouble()
        val tipAmount = tipAmount_tv.text.toString().toDouble()
        val splitAmount = (baseAmount + tipAmount )/ progress

        splitAmount_tv.text = "%.2f".format(splitAmount)
    }

    private fun updateTipDescrption(tipPercentage : Int) {
        val tipDescription = when(tipPercentage){
            in 0..6 -> "😡"
            in 7..12 -> "😩"
            in 13..18 -> "🙂"
            in 19..24 -> "😁"
            else -> "🤩"
        }
        tipDescription_tv.text = tipDescription

    }

    private fun computeTipAndTotal() {
        if (baseAmount_et.text.isEmpty()){
            tipAmount_tv.text = ""
            totalAmount_tv.text = ""
            return
        }
        // 1. Get the value of the base-amount and tip
        val baseAmount = baseAmount_et.text.toString().toDouble()
        val tipPercent = seekbar.progress
        // 2. Compute the Tip and Total
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount
        // 3. Update those values in the UI
        tipAmount_tv.text = "%.2f".format(tipAmount)
        totalAmount_tv.text = "%.2f".format(totalAmount)

    }


}