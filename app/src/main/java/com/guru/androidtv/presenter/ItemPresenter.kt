package com.guru.androidtv.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.guru.androidtv.R
import com.guru.androidtv.databinding.ItemViewBinding
import com.guru.androidtv.model.Result

class ItemPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val context = parent.context
      val  binding = ItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val resources = context.resources
        binding.root.setMainImageDimensions(
            resources.getDimensionPixelSize(R.dimen.image_card_width),
            resources.getDimensionPixelSize(R.dimen.image_card_height))
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any?) {
        val content = item as? Result
        val binding = ItemViewBinding.bind(viewHolder.view)
        val url = "https://www.themoviedb.org/t/p/w500" + content?.poster_path
        binding.root.titleText = content?.original_title
        val requestOptions = RequestOptions()
            .error(R.drawable.ic_movie)
            .placeholder(R.drawable.ic_movie)
            .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache both original & resized image

        // Preload the image to optimize loading time
        Glide.with(viewHolder.view.context)
            .load(url)
            .apply(requestOptions)
            .preload()

        // Load the image into the ImageView
        binding.root.mainImageView?.let { img ->
            Glide.with(viewHolder.view.context)
                .load(url)
                .apply(requestOptions)
                .into(img)
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        val binding = ItemViewBinding.bind(viewHolder.view)
        binding.root.mainImage = null
    }
}
