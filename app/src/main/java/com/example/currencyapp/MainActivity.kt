package com.example.currencyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.example.currencyapp.model.CurrencyData
import com.example.currencyapp.model.CurrencySymbolModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var spinnerCurrencySymbols: Spinner
    private lateinit var convertFromCurrency: EditText
    private lateinit var imgExchange: ImageView
    private lateinit var spinnerCurrencySymbols2: Spinner
    private lateinit var convertToCurrency: EditText
    private lateinit var btnConvert: Button
    private var fromCurrencyCode: String? = null
    private var toCurrencyCode: String? = null
    private val currSymbolList = mutableListOf("Currency")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerCurrencySymbols = findViewById(R.id.spinnerCurrencySymbols)
        convertFromCurrency = findViewById(R.id.convertFromCurrency)
        imgExchange = findViewById(R.id.imgExchange)
        spinnerCurrencySymbols2 = findViewById(R.id.spinnerCurrencySymbols2)
        convertToCurrency = findViewById(R.id.convertToCurrency)
        btnConvert = findViewById(R.id.btnConvert)

        getCurrencySymbols()

        spinnerCurrencySymbols.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    fromCurrencyCode = if (position > 0) {
                        currSymbolList[position]
                    } else {
                        null
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        spinnerCurrencySymbols2.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    toCurrencyCode = if (position > 0) {
                        currSymbolList[position]
                    } else {
                        null
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        btnConvert.setOnClickListener {
            if (fromCurrencyCode != null && toCurrencyCode != null) {
                val amount = convertFromCurrency.text.toString()
                if (amount.isNotEmpty()) {
                    getCurrentCurrency(amount.toInt())
                }
            } else {
                Toast.makeText(this, "Choose Currency", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getCurrencySymbols() {
        val symbols = RetrofitInstance.getCurrency.getCurrencySymbols()

        symbols.enqueue(object : Callback<CurrencySymbolModel?> {
            override fun onResponse(
                call: Call<CurrencySymbolModel?>,
                response: Response<CurrencySymbolModel?>
            ) {
                val myRes = response.body()
                if (myRes != null) {
                    myRes.result.forEach { currSymbolList.add(it.code) }
                    setSymbols()
                } else {
                    Log.d("R/T", "semboller null geldi")
                }
            }

            override fun onFailure(call: Call<CurrencySymbolModel?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setSymbols() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currSymbolList)
        spinnerCurrencySymbols.adapter = adapter
        spinnerCurrencySymbols2.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun getCurrentCurrency(amount: Int) {
        val res = toCurrencyCode?.let {
            fromCurrencyCode?.let { it1 ->
                RetrofitInstance.getCurrency.getCurrency(
                    amount,
                    it, it1
                )
            }
        }

        res!!.enqueue(object : Callback<CurrencyData?> {
            override fun onResponse(call: Call<CurrencyData?>, response: Response<CurrencyData?>) {
                val toCurr = response.body()
                if (toCurr != null) {
                    val curr = toCurr.result.data[0].calculatedstr
                    convertToCurrency.setText(curr)
                } else {
                    Log.d("R/T", "null 131")
                }
            }

            override fun onFailure(call: Call<CurrencyData?>, t: Throwable) {
                Log.d("R/T", t.message.toString() + " ******")
            }
        })
    }
}