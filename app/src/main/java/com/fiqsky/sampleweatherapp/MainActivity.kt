package com.fiqsky.sampleweatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fiqsky.sampleweatherapp.api.ApiRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiRequest()
    }

    private fun apiRequest() {
        val api = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getWeather("jakarta", BuildConfig.API_KEY)
                Log.d("Main", "Size: ${response.weather}")

                withContext(Dispatchers.Main) {
                    tv_text.text = response.weather[0].main
                }
            } catch (e: Exception) {
                Log.e("Main", "Error: ${e.message}")
            }
        }

    }
}