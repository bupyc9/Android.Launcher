package bupyc9.launcher.view.applist

import android.content.Context
import android.content.pm.ResolveInfo
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import bupyc9.launcher.R
import kotlinx.android.synthetic.main.item_app.view.*

class AppListAdapter(private var mItems: MutableList<ResolveInfo>, private var mContext: Context)
    : RecyclerView.Adapter<AppListAdapter.ViewHolder>(), Filterable {

    private var mItemsFiltered = mItems
    var onClickListener: ((View?, ResolveInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_app, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mItemsFiltered.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val vh = holder as ViewHolder
        vh.bind(getItem(position))
    }

    fun getItem(position: Int) = mItemsFiltered[position]

    fun addAll(items: MutableList<ResolveInfo>) {
        mItems = items
        mItemsFiltered = items
        notifyDataSetChanged()
    }

    fun add(item: ResolveInfo) {
        mItems.add(item)
        mItemsFiltered.add(item)
        notifyItemInserted(mItemsFiltered.indexOf(item))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var mItem: ResolveInfo

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: ResolveInfo) {
            mItem = item
            itemView.app_name.text = item.loadLabel(mContext.packageManager).toString()
            itemView.app_icon.setImageDrawable(item.loadIcon(mContext.packageManager))
        }

        override fun onClick(view: View?) {
            onClickListener?.invoke(view, mItem)
        }
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val result = FilterResults()
            val query = constraint!!.toString().toLowerCase()

            if (query.isNotEmpty()) {
                val filterItems = mutableListOf<ResolveInfo>()
                mItems.forEach {
                    if (it.loadLabel(mContext.packageManager).toString().toLowerCase().contains(query)) {
                        filterItems.add(it)
                    }
                }

                result.count = filterItems.size
                result.values = filterItems
            } else {
                result.count = mItems.size
                result.values = mItems
            }


            return result
        }

        override fun publishResults(p0: CharSequence?, result: FilterResults?) {
            mItemsFiltered = result?.values as MutableList<ResolveInfo>
            notifyDataSetChanged()
        }
    }
}