package com.example.kuba.androidmvvm.mainView.view

import android.R.menu
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kuba.androidmvvm.R
import com.example.kuba.androidmvvm.helpers.ErrorMessage
import com.example.kuba.androidmvvm.helpers.observeOnMainThread
import com.example.kuba.androidmvvm.helpers.show
import com.example.kuba.androidmvvm.helpers.subscribe
import com.example.kuba.androidmvvm.mainView.model.AudioBookModel
import com.example.kuba.androidmvvm.mainView.viewModel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity: AppCompatActivity() {

    // Private Properties
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    private var disposable: Disposable? = null
    private var linearLayoutManager = LinearLayoutManager(this)

    // View Life Cycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindUIData()
        bindUIGestures()
        setupRecyclerView()

        disposable = searchButton.clicks()
            .observeOnMainThread()
            .subscribe {
                viewModel.getAudioBooksData(search.text.toString().trim())
            }


    }

    override fun onPause() {
        disposable?.dispose()
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        menu.removeItem(R.id.action_audiobooks)
        return true
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_books -> {
            val intent = Intent(this, BookActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.action_authors -> {
            val intent = Intent(this, AuthorActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.action_epochs -> {
            val intent = Intent(this, EpochActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }


    // Private Methods

    private fun bindUIData() {
        viewModel.audioBooks.subscribe(this, ::showAllAudioBooks)
        viewModel.progress.subscribe(this, ::updateProgress)
        viewModel.errors.subscribe(this, ::showErrorMessage)
    }

//    private fun bindUIGestures() {
//        disposable = downloadButton.clicks()
//            .observeOnMainThread()
//            .subscribe {
//                viewModel.getAudioBooksData()
//            }
//    }

    private fun bindUIGestures(search: String = "") {
            disposable = downloadButton.clicks()
                .observeOnMainThread()
                .subscribe {
                    viewModel.getAudioBooksData(search.trim())
                }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = linearLayoutManager
    }

    // View Model Binding

    private fun showAllAudioBooks(audioBooks: List<AudioBookModel>) {
        recyclerView.adapter = RecyclerAdapter(audioBooks)
    }

    private fun updateProgress(isDownloading: Boolean) {
        progressBar.show(isDownloading)
    }

    private fun showErrorMessage(error: ErrorMessage) {
        Snackbar.make(
            rootLayout,
            error.getMessage(),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
