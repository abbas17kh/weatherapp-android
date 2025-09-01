package abbas17kh.weatherapp.data.json

import androidx.annotation.Keep
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class Forecast(
    val forecastday: Array<ForecastDay>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Forecast

        if (!forecastday.contentEquals(other.forecastday)) return false
        return true
    }

    override fun hashCode(): Int {
        return forecastday.contentHashCode()
    }
}

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@Keep
data class ForecastDay(
    val date: String,
    val day: Day,
    val astro: Astro
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@Keep
data class Day(
    val maxtemp_c: Double,
    val maxwind_kph: Double,
    val condition: DayCondition
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@Keep
data class DayCondition(
    val text: String,
    val icon: String
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@Keep
data class Astro(
    val sunrise: String,
    val sunset: String
)