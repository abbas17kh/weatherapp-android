package abbas17kh.weatherapp.ui.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AppSettingsEntity::class, WeatherCacheEntity::class],
    version = 1
)

abstract class Database : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}