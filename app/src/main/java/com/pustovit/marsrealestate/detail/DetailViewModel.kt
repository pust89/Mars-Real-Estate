package com.pustovit.marsrealestate.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pustovit.marsrealestate.R
import com.pustovit.marsrealestate.network.MarsProperty

class DetailViewModel(marsProperty: MarsProperty, application: Application) : ViewModel() {

    private val _selectedProperty = MutableLiveData<MarsProperty>()
    val selectedProperty: LiveData<MarsProperty>
        get() = _selectedProperty

    init {
        _selectedProperty.value = marsProperty
    }

    // The displayPropertyPrice formatted Transformation Map LiveData, which displays the sale
    // or rental price.
    val displayPropertyPrice = Transformations.map(selectedProperty) {
        application.applicationContext.getString(
            when (it.isRental) {
                true -> R.string.display_price_monthly_rental
                false -> R.string.display_price
            }, it.price)
    }

    val displayPropertyType = Transformations.map(selectedProperty){
        application.applicationContext.getString(R.string.display_type,
        application.applicationContext.getString(when(it.isRental){
            true -> R.string.type_rent
            false -> R.string.type_sale
        }))
    }

}
