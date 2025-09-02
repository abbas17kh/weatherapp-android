package abbas17kh.weatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AppSettingsEntity::class, CurrentWeatherCacheEntity::class],
    version = 1
)

abstract class Database : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}