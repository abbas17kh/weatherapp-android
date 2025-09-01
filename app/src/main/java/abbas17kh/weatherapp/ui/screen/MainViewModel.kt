package abbas17kh.weatherapp.ui.screen

import abbas17kh.weatherapp.data.json.WeatherResponse
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

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