package com.guru.androidtv.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.guru.androidtv.R
import com.guru.androidtv.databinding.CastItemViewBinding
import com.guru.androidtv.model.CastResponse
import com.guru.androidtv.utils.Common.Companion.getHeightInPercent
import com.guru.androidtv.utils.Common.Companion.getWidthInPercent

class CastItemPresenter : Presenter() {
    private lateinit var binding: CastItemViewBinding
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        binding = CastItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

//        val params = binding.root.layoutParams
//        params.width = getWidthInPercent(parent.context, 15)
//        params.height = getHeightInPercent(parent.context, 20)

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any?) {
        val content = item as CastResponse.Cast

        val path = "https://www.themoviedb.org/t/p/w780" + content.profile_path
        Glide.with(viewHolder.view.context)
            .load(path)
            .error(R.drawable.baseline_person_24)
            .placeholder(R.drawable.baseline_person_24)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.castImg)
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
    }
}