package abbas17kh.weatherapp.ui.screen

import abbas17kh.weatherapp.BuildConfig
import abbas17kh.weatherapp.data.WeatherCacheEntity
import abbas17kh.weatherapp.data.WeatherDao
import abbas17kh.weatherapp.data.json.WeatherResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class MainViewModel(
    private val weatherDao: WeatherDao
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val baseUrl = "https://api.weatherapi.com/v1"
    private val client = OkHttpClient()
    private val key = BuildConfig.WEATHER_API_KEY

    private val jsonParser = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    init {
        viewModelScope.launch {
            loadCachedWeather()
        }
    }

    fun fetchWeatherForCity(city: String) {
        if (city.isBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val weatherResponse = getWeatherFromApi(city)
                if (weatherResponse != null) {
                    cacheWeather(city, weatherResponse)
                    _uiState.value = _uiState.value.copy(
                        weather = weatherResponse,
                        city = city,
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Failed to fetch weather data"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    private suspend fun getWeatherFromApi(city: String): WeatherResponse? = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url("$baseUrl/forecast.json?key=$key&q=$city&aqi=no&days=1&alerts=yes")
                .build()

            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val responseString = response.body?.string() ?: return@withContext null
                    jsonParser.decodeFromString<WeatherResponse>(responseString)
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    private suspend fun cacheWeather(city: String, weather: WeatherResponse) = withContext(Dispatchers.IO) {
        try {
            val weatherJson = jsonParser.encodeToString(weather)
            weatherDao.cacheWeather(
                WeatherCacheEntity(
                    locationName = city,
                    weatherResponseJson = weatherJson,
                    lastUpdated = System.currentTimeMillis()
                )
            )
        } catch (e: Exception) {
            println("Error: ${e.message}")
            e.printStackTrace()
        }
    }

    private suspend fun loadCachedWeather() = withContext(Dispatchers.IO) {
        try {
            val cachedData = weatherDao.getLastCachedWeather()
            if (cachedData != null) {
                val weatherResponse = jsonParser.decodeFromString<WeatherResponse>(cachedData.weatherResponseJson)
                _uiState.value = _uiState.value.copy(
                    weather = weatherResponse,
                    city = cachedData.locationName
                )
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            e.printStackTrace()
        }
    }

    data class UiState(
        val darkMode: Boolean = false,
        val city: String = "",
        val weather: WeatherResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}