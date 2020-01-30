package com.example.kuba.androidmvvm.mainView.view

import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kuba.androidmvvm.R
import com.example.kuba.androidmvvm.helpers.inflate
import com.example.kuba.androidmvvm.mainView.model.AudioBookModel
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import kotlinx.android.synthetic.main.recyclerview_item_row.view.author
import kotlinx.android.synthetic.main.recyclerview_item_row.view.genre
import kotlinx.android.synthetic.main.recyclerview_item_row.view.title
import kotlinx.android.synthetic.main.recyclerview_item_row_book.view.*

class RecyclerAdapter(private val audioBooks: List<AudioBookModel>) : RecyclerView.Adapter<RecyclerAdapter.AudioBookHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.AudioBookHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
        return AudioBookHolder(inflatedView)
    }

    override fun getItemCount() = audioBooks.size

    override fun onBindViewHolder(holder: RecyclerAdapter.AudioBookHolder, position: Int) {
        val item = audioBooks[position]
        holder.bindAudioBook(item)
    }

    class AudioBookHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var audioBook: AudioBookModel? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val context = itemView.context

            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(this.audioBook?.url)
            context.startActivity(openURL)
        }

        fun bindAudioBook(audioBook: AudioBookModel) {
            this.audioBook = audioBook

            view.title.text = audioBook.title
            view.author.text = audioBook.author
            view.genre.text = audioBook.genre
        }
    }
}