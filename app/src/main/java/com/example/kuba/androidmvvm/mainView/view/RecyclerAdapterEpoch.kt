package com.example.kuba.androidmvvm.mainView.view

import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kuba.androidmvvm.R
import com.example.kuba.androidmvvm.helpers.inflate
import com.example.kuba.androidmvvm.mainView.model.EpochModel
import kotlinx.android.synthetic.main.recyclerview_item_row_small.view.*

class RecyclerAdapterEpoch(private val epochs: List<EpochModel>) : RecyclerView.Adapter<RecyclerAdapterEpoch.EpochHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterEpoch.EpochHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row_small, false)
        return EpochHolder(inflatedView)
    }

    override fun getItemCount() = epochs.size

    override fun onBindViewHolder(holder: RecyclerAdapterEpoch.EpochHolder, position: Int) {
        val item = epochs[position]
        holder.bindAudioBook(item)
    }

    class EpochHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var epoch: EpochModel? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val context = itemView.context

            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(this.epoch?.url)
            context.startActivity(openURL)
        }

        fun bindAudioBook(epoch: EpochModel) {
            this.epoch = epoch

            view.name.text = epoch.name
        }
    }
}