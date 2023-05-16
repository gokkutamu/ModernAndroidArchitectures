package com.example.appmovieandroid.networking

import com.example.appmovieandroid.model.CountryModel
import io.reactivex.Single
import retrofit2.http.GET

interface CountriesApi {

    @GET("all")
    fun getList(): Single<List<CountryModel>>
}