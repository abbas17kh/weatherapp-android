package abbas17kh.weatherapp.ui.screen

import abbas17kh.weatherapp.data.json.WeatherResponse
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var cityInput by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .widthIn(min = 320.dp, max = 450.dp)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = cityInput,
                        onValueChange = { cityInput = it },
                        label = { Text("Enter city name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        enabled = !uiState.isLoading
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            viewModel.fetchWeatherForCity(cityInput.trim())
                        },
                        shape = MaterialTheme.shapes.medium,
                        enabled = !uiState.isLoading && cityInput.isNotBlank()
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp))
                        } else {
                            Text("Get Weather")
                        }
                    }
                }

                if (uiState.error != null) {
                    Text(
                        text = "Error: ${uiState.error}",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else if (uiState.weather != null) {
                    WeatherDisplay(weather = uiState.weather!!)
                } else {
                    Text(
                        text = "Enter a city name to get weather information",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun WeatherDisplay(weather: WeatherResponse) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = weather.location.name,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "${weather.location.region}, ${weather.location.country}",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
        Text(
            text = "Local Time: ${weather.location.localtime.substringAfter(" ")}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "${weather.current.temp_c}°C",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = weather.current.condition.text,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Feels like: ${weather.current.feelslike_c}°C",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
        )
    }

    if (weather.forecast.forecastday.isNotEmpty()) {
        val todayForecast = weather.forecast.forecastday[0]
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Today's Outlook (${todayForecast.date.substringAfter("-")})",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Max: ${todayForecast.day.maxtemp_c}°C • ${todayForecast.day.condition.text}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
            Text(
                text = "Sunrise: ${todayForecast.astro.sunrise} • Sunset: ${todayForecast.astro.sunset}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }

    Text(
        text = "Updated: ${weather.current.lastUpdated.substringAfter(" ")}",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
    )
}