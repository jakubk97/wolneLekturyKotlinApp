package com.example.kuba.androidmvvm.mainView.view

import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kuba.androidmvvm.R
import com.example.kuba.androidmvvm.helpers.inflate
import com.example.kuba.androidmvvm.mainView.model.AuthorModel
import kotlinx.android.synthetic.main.recyclerview_item_row_small.view.*

class RecyclerAdapterAuthor(private val authors: List<AuthorModel>) : RecyclerView.Adapter<RecyclerAdapterAuthor.AuthorHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterAuthor.AuthorHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row_small, false)
        return AuthorHolder(inflatedView)
    }

    override fun getItemCount() = authors.size

    override fun onBindViewHolder(holder: RecyclerAdapterAuthor.AuthorHolder, position: Int) {
        val item = authors[position]
        holder.bindAudioBook(item)
    }

    class AuthorHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var author: AuthorModel? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val context = itemView.context

            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(this.author?.url)
            context.startActivity(openURL)
        }

        fun bindAudioBook(author: AuthorModel) {
            this.author = author

            view.name.text = author.name
        }
    }
}