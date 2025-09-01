package abbas17kh.weatherapp.repository

import abbas17kh.weatherapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request

class ApiService {
    val baseUrl = "https://api.weatherapi.com/v1"
    val client = OkHttpClient()
    val key = BuildConfig.WEATHER_API_KEY
    val city = "Gera"
    val days = "5"
    val alerts = "yes"



    fun getWeather(){
        val request = Request.Builder()
            .url("$baseUrl/forecast.json?key=$key&q=$city&aqi=no&days=$days&alerts=$alerts")
            .build()

        val responseString: String = try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) "" else response.body.string()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error: $e")
            ""
        }
        println("API Response: $responseString")
    }
}