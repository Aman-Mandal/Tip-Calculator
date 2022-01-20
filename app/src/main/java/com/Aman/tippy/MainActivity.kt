package com.Aman.tippy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENTAGE = 15
class MainActivity : AppCompatActivity() {
    private lateinit var baseAmount_et : EditText
    private lateinit var seekbar : SeekBar
    private lateinit var tipAmount_tv : TextView
    private lateinit var totalAmount_tv : TextView
    private lateinit var tipPercentage_tv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        baseAmount_et = findViewById(R.id.baseAmount_et)
        seekbar = findViewById(R.id.seekBar)
        tipAmount_tv = findViewById(R.id.tipAmount_tv)
        totalAmount_tv = findViewById(R.id.totalAmount_tv)
        tipPercentage_tv = findViewById(R.id.tipPercentage_tv)

        seekbar.progress = INITIAL_TIP_PERCENTAGE
        tipPercentage_tv.text = "$INITIAL_TIP_PERCENTAGE%"
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // progress : the value of seekbar
                tipPercentage_tv.text = "$progress%"
                computeTipAndTotal()
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