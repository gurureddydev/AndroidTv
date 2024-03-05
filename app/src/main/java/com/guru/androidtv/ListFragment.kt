package com.guru.androidtv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.guru.androidtv.model.CastResponse

class ListFragment : RowsSupportFragment() {
    private var itemSelectedListener: ((DataModel.Result.Detail) -> Unit)? = null
    private var itemClickListener: ((DataModel.Result.Detail) -> Unit)? = null
    private var rootAdapter: ArrayObjectAdapter =
        ArrayObjectAdapter(ListRowPresenter(FocusHighlight.ZOOM_FACTOR_MEDIUM))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = rootAdapter
        onItemViewClickedListener = ItemViewSelectedListener()

    }

    fun bindData(dataList: DataModel) {
        dataList.result.forEachIndexed { _, result ->
            val arrayObjectAdapter = ArrayObjectAdapter(ItemPresenter())
            result.details.forEach { dataModelResult ->
                arrayObjectAdapter.add(dataModelResult)
            }

            val headerItem = HeaderItem(result.title)
            val listRow = ListRow(headerItem, arrayObjectAdapter)
            rootAdapter.add(listRow)
        }
    }

    fun bindCastData(list: List<CastResponse.Cast>) {
        val arrayObjectAdapter = ArrayObjectAdapter(CastItemPresenter())
        list.forEach { context ->
            arrayObjectAdapter.add(context)
        }

        val headerItem = HeaderItem("Case & Crew")
        val listRow = ListRow(headerItem, arrayObjectAdapter)
        rootAdapter.add(listRow)
    }

    fun setOnContentSelectedListener(listener: (DataModel.Result.Detail) -> Unit) {
        this.itemSelectedListener = listener
    }

    fun setOnItemClickListener(listener: (DataModel.Result.Detail) -> Unit) {
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
            if (item is DataModel.Result.Detail) {
                itemSelectedListener?.invoke(item)
            }
        }

        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Any?
        ) {
            if (item is DataModel.Result.Detail) {
                itemClickListener?.invoke(item)
            }
        }
    }
}