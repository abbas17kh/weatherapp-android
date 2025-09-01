package abbas17kh.weatherapp.ui.screen

import abbas17kh.weatherapp.data.json.Astro
import abbas17kh.weatherapp.data.json.*
import android.content.res.Configuration
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel


@Composable
fun MainScreenContentPreview() {
    val mockWeatherResponse = WeatherResponse(
        location = Location(
            name = "Mountain View",
            region = "California",
            country = "USA",
            localtime = "2023-10-27 10:00",
            lat = 747.0,
            lon = 7647.0,
            tz_id = "",
            localtime_epoch = 2
        ),
        current = Current(
            temp_c = 25.0,
            condition = CurrentCondition(text = "Sunny", icon = ""),
            feelslike_c = 26.0,
            lastUpdated = "2023-10-27 09:45"
        ),
        forecast = Forecast(
            forecastday = arrayOf(
                ForecastDay(
                    date = "2023-10-27",
                    day = Day(
                        maxtemp_c = 28.0,
                        condition = DayCondition(text = "Clear", icon = ""),
                        maxwind_kph = 478.0
                    ),
                    astro = Astro(
                        sunrise = "07:00 AM",
                        sunset = "06:00 PM"
                    )
                )
            )
        )
    )

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
                    Text(
                        text = mockWeatherResponse.location.name,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${mockWeatherResponse.location.region}, ${mockWeatherResponse.location.country}",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "Local Time: ${mockWeatherResponse.location.localtime.substringAfter(" ")}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${mockWeatherResponse.current.temp_c}°C",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = mockWeatherResponse.current.condition.text,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Feels like: ${mockWeatherResponse.current.feelslike_c}°C",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                }

                if (mockWeatherResponse.forecast.forecastday.isNotEmpty()) {
                    val todayForecast = mockWeatherResponse.forecast.forecastday[0]
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
                    text = "Updated: ${mockWeatherResponse.current.lastUpdated.substringAfter(" ")}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
        }
    }
}
