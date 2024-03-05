package com.guru.androidtv.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.guru.androidtv.databinding.ItemViewBinding
import com.guru.androidtv.model.Result
import com.guru.androidtv.utils.Common.Companion.getHeightInPercent
import com.guru.androidtv.utils.Common.Companion.getWidthInPercent

class ItemPresenter : Presenter() {
    private lateinit var binding: ItemViewBinding
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        binding = ItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val params = binding.root.layoutParams
        params.width = parent.context?.let { getWidthInPercent(it, 12) } ?: 0
        params.height = parent.context?.let { getHeightInPercent(it, 32) } ?: 0

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any?) {
        val content = item as? Result
        val url = "https://www.themoviedb.org/t/p/w500" + content?.poster_path
        Glide.with(viewHolder.view.context)
            .load(url)
            .into(binding.posterImg)
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        // Implement your unbinding logic here
    }
}
