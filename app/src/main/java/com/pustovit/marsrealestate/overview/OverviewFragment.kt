package com.pustovit.marsrealestate.overview

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pustovit.marsrealestate.R
import com.pustovit.marsrealestate.databinding.OverviewFragmentBinding
import com.pustovit.marsrealestate.network.MarsApiFilter
import com.pustovit.marsrealestate.network.MarsProperty

class OverviewFragment : Fragment() {


    private lateinit var mViewModel: OverviewViewModel
    private lateinit var mBinding: OverviewFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        mViewModel =
            ViewModelProvider(this, OverviewViewModelFactory()).get(OverviewViewModel::class.java)

        mBinding = DataBindingUtil.inflate(inflater, R.layout.overview_fragment, container, false)
        mBinding.setLifecycleOwner(this)

        mBinding.viewModel = mViewModel


        mBinding.photosGrid.adapter =
            PhotoGridAdapter(MarsPropertyClickListener { marsProperty: MarsProperty ->
                mViewModel.onNavigateToDetailFragment(marsProperty)
            })

        mViewModel.navigateToDetailFragment.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(
                        it
                    )
                )
                mViewModel.onNavigateToDetailFragmentDone()
            }
        })
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_filter, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mViewModel.updateFilter(
            when (item.itemId) {
                R.id.show_rent -> MarsApiFilter.SHOW_RENT
                R.id.show_buy -> MarsApiFilter.SHOW_BUY
                else -> MarsApiFilter.SHOW_ALL
            }
        )
        return true
    }
}
