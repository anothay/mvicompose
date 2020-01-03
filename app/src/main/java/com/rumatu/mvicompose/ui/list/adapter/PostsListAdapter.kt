package com.rumatu.mvicompose.ui.list.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rumatu.mvicompose.R
import com.rumatu.mvicompose.ui.list.displaymodels.ListDisplayModel
import com.rumatu.mvicompose.utils.autoNotify
import com.rumatu.mvicompose.utils.inflate
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.item_post.view.*

class PostsListAdapter(
    private val postSelector: (ListDisplayModel) -> Unit
) : RecyclerView.Adapter<PostsListAdapter.PostViewHolder>() {

    var items: List<ListDisplayModel> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o == n }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(parent.inflate(R.layout.item_post), postSelector)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class PostViewHolder(
        itemView: View,
        private val postSelector: (ListDisplayModel) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.title

        internal fun bind(data: ListDisplayModel) {
            itemView.setOnClickListener { postSelector(data) }
            title.text = data.title
        }
    }
}
