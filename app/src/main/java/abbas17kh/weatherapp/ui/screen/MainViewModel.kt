package abbas17kh.weatherapp.ui.screen

import abbas17kh.weatherapp.BuildConfig
import abbas17kh.weatherapp.data.CurrentWeatherCacheEntity
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

    val jsonParser = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun getWeatherFromApi(
        city: String,
        days: String,
        alerts: String
    ): String{
        val request = Request.Builder()
            .url("$baseUrl/forecast.json?key=$key&q=$city&aqi=no&days=$days&alerts=$alerts")
            .build()

        val responseString: String = try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) "" else response.body.string()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
        return responseString
    }

    fun getWeather(){
        val currentUiState = _uiState.value

        scope.launch(Dispatchers.IO) {
            val apiResponse = getWeatherFromApi(
                city = currentUiState.city,
                days = currentUiState.days,
                alerts = currentUiState.alerts
            )

            val weatherResponse: WeatherResponse? = try {
                jsonParser.decodeFromString<WeatherResponse>(apiResponse)
            } catch (e: Exception) {
                null
            }

            cacheWeather(weatherResponse)
        }
    }

    fun cacheWeather(weather: WeatherResponse?){
        scope.launch(Dispatchers.IO) {
            weatherDao.cacheWeather(
                weather = CurrentWeatherCacheEntity(
                    locationName = weather?.location?.name?:"",
                    weatherData = weather?.current?.condition?.text?:"",
                    lastUpdated = weather?.current?.lastUpdated?: ""
                )
            )
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