package com.example.appmovieandroid.controller

import com.example.appmovieandroid.networking.CountriesApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CountryController(private val apiService: CountriesApi) {
    fun onFetchCountries() {
        apiService.let {
            it.getList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res ->
                    // true
                }, { error ->
                    // false
                })
        }
    }
}