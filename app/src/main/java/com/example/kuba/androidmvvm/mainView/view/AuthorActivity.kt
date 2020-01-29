package com.example.kuba.androidmvvm.mainView.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kuba.androidmvvm.R
import com.example.kuba.androidmvvm.helpers.ErrorMessage
import com.example.kuba.androidmvvm.helpers.observeOnMainThread
import com.example.kuba.androidmvvm.helpers.show
import com.example.kuba.androidmvvm.helpers.subscribe
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_author.*
import android.view.Menu
import android.view.MenuItem
import com.example.kuba.androidmvvm.mainView.model.AuthorModel
import com.example.kuba.androidmvvm.mainView.viewModel.MainViewModelAuthor
import kotlinx.android.synthetic.main.activity_author.downloadButton
import kotlinx.android.synthetic.main.activity_author.progressBar
import kotlinx.android.synthetic.main.activity_author.recyclerView
import kotlinx.android.synthetic.main.activity_author.search
import kotlinx.android.synthetic.main.activity_author.searchButton
import kotlinx.android.synthetic.main.activity_main.*

class AuthorActivity: AppCompatActivity() {

    // Private Properties
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModelAuthor::class.java) }

    private var disposable: Disposable? = null
    private var linearLayoutManager = LinearLayoutManager(this)

    // View Life Cycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author)

        bindUIData()
        bindUIGestures()
        setupRecyclerView()

        disposable = searchButton.clicks()
            .observeOnMainThread()
            .subscribe {
                viewModel.getAuthorsData(search.text.toString().trim())
            }
    }

    override fun onPause() {
        disposable?.dispose()
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        menu.removeItem(R.id.action_authors)
        return true
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_audiobooks -> {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.action_books -> {
            val intent = Intent(this, BookActivity::class.java)
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
        viewModel.authors.subscribe(this, ::showAllAuthors)
        viewModel.progress.subscribe(this, ::updateProgress)
        viewModel.errors.subscribe(this, ::showErrorMessage)
    }

    private fun bindUIGestures(search: String = "") {
        disposable = downloadButton.clicks()
            .observeOnMainThread()
            .subscribe {
                viewModel.getAuthorsData(search.trim())
            }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = linearLayoutManager
    }

    // View Model Binding

    private fun showAllAuthors(authors: List<AuthorModel>) {
        recyclerView.adapter = RecyclerAdapterAuthor(authors)
    }

    private fun updateProgress(isDownloading: Boolean) {
        progressBar.show(isDownloading)
    }

    private fun showErrorMessage(error: ErrorMessage) {
        Snackbar.make(
            rootLayout2,
            error.getMessage(),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
