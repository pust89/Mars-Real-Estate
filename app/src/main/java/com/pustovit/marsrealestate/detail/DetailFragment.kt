package com.pustovit.marsrealestate.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pustovit.marsrealestate.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mBinding = DetailFragmentBinding.inflate(inflater)
        mBinding.lifecycleOwner = this

        val selectedEstate = DetailFragmentArgs.fromBundle(requireArguments())!!.selectedEstate
        val application = requireNotNull(activity).application
        val mViewModel =
            ViewModelProvider(this, DetailViewModelFactory(selectedEstate, application)).get(
                DetailViewModel::class.java
            )
        mBinding.viewModel = mViewModel


        return mBinding.root
    }


}
