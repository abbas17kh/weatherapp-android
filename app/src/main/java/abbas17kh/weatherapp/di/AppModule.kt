package abbas17kh.weatherapp.di

import abbas17kh.weatherapp.data.Database
import abbas17kh.weatherapp.data.WeatherDao
import abbas17kh.weatherapp.ui.screen.MainViewModel
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Database> {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java,
            "weather.db"
        )
            .fallbackToDestructiveMigration(true).build()
    }

    single<WeatherDao> { get<Database>().weatherDao() }

    viewModel { MainViewModel(get()) }
}