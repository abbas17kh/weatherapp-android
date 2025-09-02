package abbas17kh.weatherapp.ui.screen

import abbas17kh.weatherapp.BuildConfig
import abbas17kh.weatherapp.data.WeatherDao
import abbas17kh.weatherapp.data.json.WeatherResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class MainViewModel(
    private val weatherDao: WeatherDao
): ViewModel() {
    private val scope = viewModelScope
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val baseUrl = "https://api.weatherapi.com/v1"
    val client = OkHttpClient()
    val key = BuildConfig.WEATHER_API_KEY
    val city = "Gera"
    val days = "5"
    val alerts = "yes"

    var response: String? = null

    val jsonParser = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

//    val weatherResponse: WeatherResponse? = try {
//        jsonParser.decodeFromString<WeatherResponse>(viewModel.response?:"")
//    } catch (e: Exception) {
//        null
//    }

    fun getWeatherFromApi(): String{
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

        return responseString
    }

    fun getWeather(){
        scope.launch(Dispatchers.IO) {
            val apiResponse = getWeatherFromApi()
            response = apiResponse
        }
    }

    data class UiState(
        val darkMode: Boolean = false,
        val city: String = "Berlin",
        val days: String = "1",
        val alerts: String = "yes",
        val weather: WeatherResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}