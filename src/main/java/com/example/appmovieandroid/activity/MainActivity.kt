package com.example.appmovieandroid.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.appmovieandroid.R
import com.example.appmovieandroid.adapter.CountriesAdapter
import com.example.appmovieandroid.databinding.ActivityMainBinding
import com.example.appmovieandroid.model.CountryModel
import com.example.appmovieandroid.networking.CountriesApi
import com.example.appmovieandroid.networking.CountriesService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var apiService: CountriesApi? = null
    private var countriesAdapter = CountriesAdapter(arrayListOf())
    private var countries: List<CountryModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = CountriesService.create()

        binding.listView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }
        countriesAdapter.setOnItemClickListener(object: CountriesAdapter.OnItemClickListener {
            override fun onItemClick(countryModel: CountryModel) {
                Toast.makeText(this@MainActivity, "Country ${countryModel.name}, capital is ${countryModel.capital} checked", Toast.LENGTH_SHORT).show()
            }
        })

        binding.searchField.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p: Editable) {
                if (p.isNotEmpty()) {
                    val filterCounties = countries?.filter { countryModel ->
                        countryModel.name.common.contains(p.toString(), true)
                    }
                    filterCounties?.let { countriesAdapter.updateCountries(it) }
                } else {
                    countries?.let { countriesAdapter.updateCountries(it) }
                }
            }
        })

        onFetchCountries()
    }

    /*
    * On fetch countries
    * */
    private fun onFetchCountries() {
        binding.listView.visibility = View.GONE
        binding.progress.visibility = View.VISIBLE
        binding.searchField.isEnabled = false

        apiService?.let {
            it.getList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    countries = result
                    countriesAdapter.updateCountries(result)
                    onSuccess()
                }, { error ->
                    onError()
                })
        }
    }

    fun onError() {
        binding.listView.visibility = View.GONE
        binding.progress.visibility = View.GONE
        binding.searchField.isEnabled = false
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
    }

    fun onSuccess() {
        binding.listView.visibility = View.VISIBLE
        binding.progress.visibility = View.GONE
        binding.searchField.isEnabled = true

        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
    }
}