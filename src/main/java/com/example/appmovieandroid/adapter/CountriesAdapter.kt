package com.example.appmovieandroid.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.appmovieandroid.databinding.ItemCountryBinding
import com.example.appmovieandroid.model.CountryModel

class CountriesAdapter(private val countries: ArrayList<CountryModel>) :
    RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {

    private var listener: OnItemClickListener? = null

    fun updateCountries(newCountries: List<CountryModel>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryViewHolder(
        ItemCountryBinding.inflate(LayoutInflater.from(parent.context))
    )
    override fun getItemCount() = countries.size
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bin(countries[position], listener)
    }

    class CountryViewHolder(private val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bin(country: CountryModel, listener: OnItemClickListener?) {
            binding.apply {
                tvCountry.text = country.name.common
                tvCapital.text = country.capital?.joinToString(", ")
                root.setOnClickListener { listener?.onItemClick(country) }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(country: CountryModel)
    }
}
