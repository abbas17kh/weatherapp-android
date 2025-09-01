package abbas17kh.weatherapp

import abbas17kh.weatherapp.repository.ApiService
import abbas17kh.weatherapp.ui.screen.MainScreen
import abbas17kh.weatherapp.ui.screen.MainScreenContentPreview
import abbas17kh.weatherapp.ui.screen.MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import abbas17kh.weatherapp.ui.theme.TestWeatherappTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestWeatherappTheme(dynamicColor = false, darkTheme = true) {
                val viewModel = koinViewModel<MainViewModel>()
//                viewModel.getWeather()
//                MainScreen(viewModel)

                MainScreenContentPreview()
            }
        }
    }
}