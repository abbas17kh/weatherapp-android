package abbas17kh.weatherapp

import abbas17kh.weatherapp.ui.screen.MainScreenContentPreview
import abbas17kh.weatherapp.ui.screen.MainViewModel
import abbas17kh.weatherapp.ui.theme.TestWeatherappTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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