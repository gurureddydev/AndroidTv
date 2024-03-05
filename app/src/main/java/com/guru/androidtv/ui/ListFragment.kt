package com.guru.androidtv.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.BaseOnItemViewClickedListener
import androidx.leanback.widget.FocusHighlight
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.OnItemViewSelectedListener
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.Row
import androidx.leanback.widget.RowPresenter
import com.guru.androidtv.presenter.CastItemPresenter
import com.guru.androidtv.presenter.ItemPresenter
import com.guru.androidtv.model.CastResponse
import com.guru.androidtv.model.DataModel

class ListFragment : RowsSupportFragment() {
    private var itemSelectedListener: ((com.guru.androidtv.model.Result) -> Unit)? = null
    private var itemClickListener: ((com.guru.androidtv.model.Result) -> Unit)? = null
    private var rootAdapter: ArrayObjectAdapter =
        ArrayObjectAdapter(ListRowPresenter(FocusHighlight.ZOOM_FACTOR_MEDIUM))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = rootAdapter
        onItemViewClickedListener = ItemViewSelectedListener()

    }

    fun bindData(dataList: DataModel) {
        rootAdapter.clear()

        val header = HeaderItem(0, "Movies")
        val arrayAdapter = ArrayObjectAdapter(ItemPresenter())
        for ((index, result) in dataList.results.withIndex()) {
            arrayAdapter.add(result)
            Log.d("ListFragment", "Adding item at index $index: $result")
        }
        val movieRow = ListRow(header, arrayAdapter)
        rootAdapter.add(movieRow)
    }

    fun bindCastData(list: List<CastResponse.Cast>) {
        val arrayObjectAdapter = ArrayObjectAdapter(CastItemPresenter())
        list.forEach { context ->
            arrayObjectAdapter.add(context)
        }

        val headerItem = HeaderItem(1,"       Case & Crew")
        val listRow = ListRow(headerItem, arrayObjectAdapter)
        rootAdapter.add(listRow)
    }

    fun setOnContentSelectedListener(listener: (com.guru.androidtv.model.Result) -> Unit) {
        this.itemSelectedListener = listener
    }

    fun setOnItemClickListener(listener: (com.guru.androidtv.model.Result) -> Unit) {
        this.itemClickListener = listener
    }

    inner class ItemViewSelectedListener : OnItemViewSelectedListener,
        BaseOnItemViewClickedListener<Any> {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is com.guru.androidtv.model.Result) {
                itemSelectedListener?.invoke(item)
            }
        }

        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Any?
        ) {
            if (item is com.guru.androidtv.model.Result) {
                itemClickListener?.invoke(item)
            }
        }
    }

    fun requestFocus(): View {
        val view = view
        view?.requestFocus()
        return view!!
    }
}