package abbas17kh.weatherapp.ui.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_cache")
data class WeatherCacheEntity(
    @PrimaryKey val locationName: String,
    val weatherData: String,
    val lastUpdated: Long
)