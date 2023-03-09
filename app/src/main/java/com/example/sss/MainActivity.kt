package com.example.sss

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sss.ui.theme.SssTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
                val photos = unsplashService.getPhotos("your_client_id", page = 1, perPage = 10)
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


