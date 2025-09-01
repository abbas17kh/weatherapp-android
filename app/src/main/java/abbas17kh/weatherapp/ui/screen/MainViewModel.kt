package abbas17kh.weatherapp.ui.screen

import abbas17kh.weatherapp.data.json.WeatherResponse
import abbas17kh.weatherapp.repository.ApiService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val api: ApiService
): ViewModel() {
    private val scope = viewModelScope
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun getWeather(){
        scope.launch(Dispatchers.IO) {
            api.getWeather()
        }
    }

    data class UiState(
        val darkMode: Boolean = false,
        val weather: WeatherResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    data class MockUiState(
        val darkMode: Boolean = false,
        val weather: WeatherResponse? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}