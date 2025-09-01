package abbas17kh.weatherapp.data.json

import androidx.annotation.Keep
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@Keep
data class WeatherResponse(
    val location: Location,
    val current: Current,
    val forecast: Forecast
)
