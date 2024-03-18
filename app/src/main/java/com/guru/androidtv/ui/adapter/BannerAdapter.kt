package com.guru.androidtv.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.guru.androidtv.R
import com.guru.androidtv.model.Result
import android.os.Handler
import android.os.Looper
import androidx.viewpager.widget.ViewPager

class BannerAdapter : PagerAdapter() {
    private var dataList: List<Result> = listOf()
    fun setData(data: List<Result>) {
        this.dataList = data
        notifyDataSetChanged()
    }

    override fun getCount(): Int = dataList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(container.context)
            .inflate(R.layout.banner_item, container, false)
        val imageView: ImageView = itemView.findViewById(R.id.banner_image)
        val textView: TextView = itemView.findViewById(R.id.title_banner)
        val result = dataList[position]
        textView.text = result.original_title
        val imageUrl = "https://www.themoviedb.org/t/p/w500${result.backdrop_path}"
        Glide.with(container.context).load(imageUrl)
            .placeholder(R.drawable.ic_live)
            .error(R.drawable.ic_live)
            .into(imageView)

        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun startAutoScroll(viewPager: ViewPager) {
        val handler = Handler(Looper.getMainLooper())
        var currentPosition = 0

        val autoScrollRunnable = object : Runnable {
            override fun run() {
                if (currentPosition == dataList.size) {
                    currentPosition = 0
                }
                viewPager.currentItem = currentPosition
                currentPosition++
                handler.postDelayed(this, 3000)
            }
        }

        handler.postDelayed(autoScrollRunnable, 3000)
    }
}
