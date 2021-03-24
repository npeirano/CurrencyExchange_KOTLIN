package com.example.currencyexchangev2.services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.currencyexchangev2.utilities.URL_GET_AVAILABLE_CURRENCIES
import com.example.currencyexchangev2.utilities.URL_GET_CURRENCY_CONVERTER
import com.example.currencyexchangev2.model.Currency
import org.json.JSONException
import org.json.JSONObject

object ApiCalls {

     val currencyList = ArrayList<Currency>()

    var amountToShow: Double = 0.0

    fun getCurrencyList(context: Context, complete: (Boolean) -> Unit) {
        try {
        val getCurrencyListRequest = object : JsonObjectRequest(Method.GET, URL_GET_AVAILABLE_CURRENCIES, null, Response.Listener { response ->

                    val currencies = response.getJSONObject("currencies")
                     val iter: Iterator<String> = currencies.keys()
                     while (iter.hasNext()) {
                            val key = iter.next()
                            val value: String = currencies.getString(key)

                            val currency = Currency(key, value)


                         currencyList.add(currency);

                        ///
                    }
                complete(true)



        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not retrieve channels")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("x-rapidapi-key", "4e1f5615d3mshba47638764e4553p168c73jsn217d428457b2")
                headers.put("x-rapidapi-host", "currency-converter5.p.rapidapi.com")
                headers.put("useQueryString", "true")
                return headers
            }
        }
            Volley.newRequestQueue(context).add(getCurrencyListRequest)

        } catch (e: JSONException) {
            Log.d("JSON", "EXC:" + e.localizedMessage)
            complete(false)
        }
    }

    fun calculateCurrency(context: Context,from: String,to: String , amount:Double ,  complete: (Double,Boolean) -> Unit) {
        /*
        val jsonBody = JSONObject()
        jsonBody.put("from", "AUD")
        jsonBody.put("to", "CAD")
        jsonBody.put("amount", "1")
        val requestBody = jsonBody.toString()*/

        val finalUrl = URL_GET_CURRENCY_CONVERTER + "?to=${to}&from=${from}&amount=${amount}"
       // val finalUrl = URL_GET_CURRENCY_CONVERTER + "?to=CAD&from=AUD&amount=1"

        val calculateCurrencyRequest = object : JsonObjectRequest(Method.GET, finalUrl, null, Response.Listener { response ->
            try {
                val rates = response.getJSONObject("rates")
                val cad = rates.getJSONObject(to)
                val amount = cad.getDouble("rate")

                //amountToShow = response.getJSONObject("rates").getJSONObject("CAD").getDouble("rate")

                complete(amount,true)

            } catch (e: JSONException) {
                Log.d("JSON", "EXC:" + e.localizedMessage)
                complete(0.0,false)
            }

        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not retrieve channels")
            complete(0.0,false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }


            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("x-rapidapi-key", "4e1f5615d3mshba47638764e4553p168c73jsn217d428457b2")
                headers.put("x-rapidapi-host", "currency-converter5.p.rapidapi.com")
                headers.put("useQueryString", "true")
                return headers
            }
        }
        Volley.newRequestQueue(context).add(calculateCurrencyRequest)
    }
}