package com.pustovit.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pustovit.marsrealestate.network.MarsApi
import com.pustovit.marsrealestate.network.MarsApiFilter
import com.pustovit.marsrealestate.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class MarsApiStatus { LOADING, ERROR, DONE }
class OverviewViewModel : ViewModel() {
    private val TAG = "mytag"
    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus>
        get() = _status


    private val _properties = MutableLiveData<List<MarsProperty>>()

    val properties: LiveData<List<MarsProperty>>
        get() = _properties


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    private fun getMarsRealEstateProperties(filter : MarsApiFilter) {

        coroutineScope.launch {
            var getPropertyDeferred = MarsApi.retrofitService.getProperties(filter.value)
            try {
                _status.value = MarsApiStatus.LOADING

                var listResult = getPropertyDeferred.await()
                _properties.value = listResult

                _status.value = MarsApiStatus.DONE

            } catch (t: Throwable) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    private val _navigateToDetailFragment = MutableLiveData<MarsProperty?>()
    val navigateToDetailFragment: LiveData<MarsProperty?>
        get() = _navigateToDetailFragment

    fun onNavigateToDetailFragment(marsProperty: MarsProperty) {
        _navigateToDetailFragment.value=marsProperty
    }

    fun onNavigateToDetailFragmentDone(){
        _navigateToDetailFragment.value = null
    }


    fun updateFilter(filter: MarsApiFilter){
        getMarsRealEstateProperties(filter)
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}


