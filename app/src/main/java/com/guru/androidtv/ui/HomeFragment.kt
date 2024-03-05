package com.guru.androidtv.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.guru.androidtv.R
import com.guru.androidtv.databinding.FragmentHomeBinding
import com.guru.androidtv.viewmodel.HomeFragmentViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel
    val listFragment = ListFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addFragment(listFragment)
        viewModelObserver()

        listFragment.setOnContentSelectedListener {
            //
        }

        listFragment.setOnItemClickListener {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent)
        }
    }

    private fun viewModelObserver() {
        viewModel.dataList.observe(viewLifecycleOwner) { dataList ->
            val firstResult = dataList.results.firstOrNull()
            listFragment.bindData(dataList)
            if (firstResult != null) {
                // Access and update UI components with the data from the first result
                binding.title.text = firstResult.title
                binding.subtitle.text = "Language: ${firstResult.original_language}"
                binding.description.text = firstResult.overview
                val imageUrl = "https://www.themoviedb.org/t/p/w500${firstResult.poster_path}"
                Glide.with(requireContext())
                    .load(imageUrl)
                    .into(binding.imgBanner)
            }
        }
    }


    private fun addFragment(castFragment: ListFragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.list_fragment, castFragment)
        transaction.commit()
    }
}
