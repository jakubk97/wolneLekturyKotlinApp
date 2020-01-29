package com.example.kuba.androidmvvm.mainView.view

import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kuba.androidmvvm.R
import com.example.kuba.androidmvvm.helpers.inflate
import com.example.kuba.androidmvvm.mainView.model.BookModel
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

class RecyclerAdapterBook(private val books: List<BookModel>) : RecyclerView.Adapter<RecyclerAdapterBook.BookHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterBook.BookHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
        return BookHolder(inflatedView)
    }

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: RecyclerAdapterBook.BookHolder, position: Int) {
        val item = books[position]
        holder.bindAudioBook(item)
    }

    class BookHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var book: BookModel? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val context = itemView.context

            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(this.book?.url)
            context.startActivity(openURL)
        }

        fun bindAudioBook(book: BookModel) {
            this.book = book

            view.title.text = book.title
            view.author.text = book.author
            view.genre.text = book.kind
        }
    }
}