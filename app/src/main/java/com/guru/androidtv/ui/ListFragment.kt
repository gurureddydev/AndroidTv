package com.guru.androidtv.ui

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import com.guru.androidtv.model.CastResponse
import com.guru.androidtv.model.DataModel
import com.guru.androidtv.presenter.CastItemPresenter
import com.guru.androidtv.presenter.ItemPresenter

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
        addRow(dataList, "Movies", dataList.results)
    }
    fun bindTopRatedData(topRatedData: DataModel) {
        addRow(topRatedData, "Top Rated", topRatedData.results)
    }


    private fun addRow(dataList: DataModel, category: String, list: List<com.guru.androidtv.model.Result>) {
        val header = HeaderItem(0, category)
        val arrayAdapter = ArrayObjectAdapter(ItemPresenter())
        for ((index, result) in list.withIndex()) {
            arrayAdapter.add(result)
            Log.d("ListFragment", "Adding item at index $index: $result")
        }
        arrayAdapter.addAll(0,dataList.results)
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
        return view ?: throw NullPointerException("Expression 'view' must not be null")
    }
}

