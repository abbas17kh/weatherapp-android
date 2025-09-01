package abbas17kh.weatherapp.ui.screen

import abbas17kh.weatherapp.data.json.WeatherResponse
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    data class UiState(
        val darkMode: Boolean = false,
        val weather: WeatherResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}