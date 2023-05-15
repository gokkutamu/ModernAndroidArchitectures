package com.example.appmovieandroid.networking

import com.example.appmovieandroid.model.Movie
import io.reactivex.Single

interface MovieApi {

    fun getList(): Single<List<Movie>>
}