package abbas17kh.weatherapp.di

import abbas17kh.weatherapp.data.Database
import abbas17kh.weatherapp.data.WeatherDao
import abbas17kh.weatherapp.repository.ApiService
import abbas17kh.weatherapp.ui.screen.MainViewModel
import org.koin.core.module.dsl.viewModel
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import kotlin.jvm.java

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
    single<ApiService> { ApiService() }

    viewModel { MainViewModel(get()) }
}