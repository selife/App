package com.example.sss

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class MainActivity : ComponentActivity() {
    private lateinit var photoRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photoRecyclerView = findViewById(R.id.photoRecyclerView)
        progressBar = findViewById(R.id.progressBar)

        photoRecyclerView.layoutManager = LinearLayoutManager(this)
        photoRecyclerView.adapter = PhotoAdapter(emptyList())

        fetchPhotos()
    }

    private fun fetchPhotos() {
        progressBar.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val photos = UnsplashService.getPhotos("rUvu4QNu8rP_wvTNjQB-ikSPLURJi1ReZ6de-VBdhck", page = 1, perPage = 10)
                withContext(Dispatchers.Main) {
                    photoRecyclerView.adapter = PhotoAdapter(photos)
                    progressBar.visibility = View.GONE
                    photoRecyclerView.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error fetching photos", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }
            }
        }
    }
}


