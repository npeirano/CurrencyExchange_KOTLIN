package com.example.currencyexchangev2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.currencyexchangev2.R
import com.example.currencyexchangev2.model.Currency
import com.example.currencyexchangev2.services.ApiCalls
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    var isEnabled = false
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val button: ImageButton = root.findViewById<ImageButton>(R.id.swapCurrenciesBtn);
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val to =autoComTextTo.text.toString()
                val from =autoComTextFrom.text.toString()
                val amountFrom = amountFromText.text.toString()
                val amountTo = amountToText.text.toString()
                if(!autoComTextTo.text.toString().equals("") && !autoComTextFrom.text.toString().equals("") && !amountFromText.text.toString().equals("") && !amountToText.text.toString().equals("")) {
                    autoComTextTo.setText(from)
                    autoComTextFrom.setText(to)
                    amountFromText.setText(amountTo)
                    amountToText.setText(amountFrom)
                }
            }
        })


        //val textView: TextView = root.findViewById(R.id.text_home)
        //homeViewModel.text.observe(viewLifecycleOwner, Observer {
           // textView.text = it
        //})
        return root
    }
    // Post view initialization logic
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Connect adapters
        autoComTextFrom.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            val prueba = autoComTextTo.text.toString()
            val pruebaActual = autoComTextFrom.text.toString()
            if(!autoComTextTo.text.toString().equals("") && !autoComTextFrom.text.toString().equals("") && !amountFromText.text.toString().equals("")) {
                val to =autoComTextTo.text.toString().split("-")[0].trim()
                val from =autoComTextFrom.text.toString().split("-")[0].trim()
                val amount = amountFromText.text.toString().toDouble()

                ApiCalls.calculateCurrency(requireActivity().getApplicationContext(), from, to , amount) { amount, findSuccess ->
                    if (findSuccess) {
                        amountToText.setText(amount.toString())
                        amountToText.setEnabled(false);
                    } else {
                        amountToText.setEnabled(true);
                    }
                }
            }
            // Display the clicked item using toast
            //Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }


        autoComTextTo.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            if(!autoComTextFrom.text.toString().equals("") && !autoComTextFrom.text.toString().equals("") && !amountFromText.text.toString().equals("")) {
                val to =autoComTextTo.text.toString().split("-")[0].trim()
                val from =autoComTextFrom.text.toString().split("-")[0].trim()
                val amount = amountFromText.text.toString().toDouble()

                ApiCalls.calculateCurrency(requireActivity().getApplicationContext(), from, to , amount) { amount, findSuccess ->
                    if (findSuccess) {
                        amountToText.setText(amount.toString())
                        amountToText.setEnabled(false);
                    } else {
                        amountToText.setEnabled(true);
                    }
                }
            }
            // Display the clicked item using toast
            //Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }
        ApiCalls.getCurrencyList(requireActivity().getApplicationContext()) { findSuccess ->
            if (findSuccess) {
                val adapter1 = ArrayAdapter<Currency>(requireActivity().getApplicationContext(),android.R.layout.simple_dropdown_item_1line, ApiCalls.currencyList)
                val adapter = adapter1
                autoComTextFrom.setAdapter(adapter1)
                autoComTextTo.setAdapter(adapter1)
            } else {
                println("falla")
            }
        }


        val fab: FloatingActionButton = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            if (isEnabled) {
                fab.setImageDrawable(ContextCompat.getDrawable(view.context, android.R.drawable.btn_star_big_off))
            } else {
                fab.setImageDrawable(ContextCompat.getDrawable(view.context, android.R.drawable.btn_star_big_on))
                Snackbar.make(view, "Exchange added to favorites", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }
            isEnabled = !isEnabled
            fab.hide();
            fab.show();

           /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()*/

        }
    }







}


