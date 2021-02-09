package com.fiqsky.sampleweatherapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.fiqsky.sampleweatherapp.api.ApiRequest
import com.fiqsky.sampleweatherapp.models.Data
import com.fiqsky.sampleweatherapp.utils.kelvinToCelsius
import com.fiqsky.sampleweatherapp.utils.unixTimestampToDateTimeString
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout.*
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
        swipeRefresh()
    }

    private fun swipeRefresh() {
        swipe.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(
                        this,
                        R.color.purple_500
                )
        )
        swipe.setColorSchemeColors(Color.WHITE)

        swipe.setOnRefreshListener {
            apiRequest()
            swipe.isRefreshing = false
        }
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
                    responses(response)
                }
            } catch (e: Exception) {
                Log.e("Main", "Error: ${e.message}")
            }
        }
    }

    private fun responses(response: Data) {
        tv_city.text = ("${response.name}, ${response.sys.country}")
        tv_date_time.text = response.dt.unixTimestampToDateTimeString()
        tv_temperature.text = response.main.temp.kelvinToCelsius().toString()
        tv_weather_condition.text = response.weather[0].description
        lottieAnim(response)
    }

    private fun lottieAnim(response: Data) {
        when (response.weather[0].main) {
            "Haze" -> lottie_weather_condition.setAnimation(R.raw.haze)
            "Fog" -> lottie_weather_condition.setAnimation(R.raw.haze)
            "Clouds" -> lottie_weather_condition.setAnimation(R.raw.clouds)
            "Rain" -> lottie_weather_condition.setAnimation(R.raw.rain)
            else -> lottie_weather_condition.setAnimation(R.raw.unknw)
        }
    }
}