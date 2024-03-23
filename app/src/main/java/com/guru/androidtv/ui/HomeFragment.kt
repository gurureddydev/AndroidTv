package com.guru.androidtv.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.guru.androidtv.R
import com.guru.androidtv.databinding.FragmentHomeBinding
import com.guru.androidtv.ui.adapter.BannerAdapter
import com.guru.androidtv.viewmodel.HomeFragmentViewModel
import com.guru.core_app.AnalyticsModule
import com.guru.core_app.AnalyticsTracker
import com.guru.core_app.FirebaseAnalyticsTracker

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var analyticsTracker: AnalyticsTracker
    private lateinit var bannerAdapter: BannerAdapter
    private val listFragment = ListFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addFragment(listFragment)
        bannerAdapter = BannerAdapter()

        binding.bannerViewpager.adapter = bannerAdapter
        bannerAdapter.startAutoScroll(binding.bannerViewpager) // Start auto-scrolling
        AnalyticsModule.initialize(requireContext())
        analyticsTracker = AnalyticsModule.getTrackerBuilder().build()
        viewModelObserver()

        listFragment.setOnItemClickListener { item ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("id", item.id)
            startActivity(intent)

            // Log event with parameters
            val params = mapOf(
                FirebaseAnalytics.Param.ITEM_ID to item.id.toString(),
                FirebaseAnalytics.Param.ITEM_NAME to item.original_title,
                FirebaseAnalytics.Param.CONTENT_TYPE to item.poster_path
            )

            analyticsTracker.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params)
        }
    }

    private fun viewModelObserver() {
        viewModel.dataList.observe(viewLifecycleOwner) { dataList ->
            val results = dataList.results
            bannerAdapter.setData(results)
            listFragment.bindData(dataList)
        }

        viewModel.topRatedList.observe(viewLifecycleOwner) { topRatedDataList ->
            listFragment.bindTopRatedData(topRatedDataList)
        }
    }

    private fun addFragment(castFragment: ListFragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.list_fragment, castFragment)
        transaction.commit()
    }
}
