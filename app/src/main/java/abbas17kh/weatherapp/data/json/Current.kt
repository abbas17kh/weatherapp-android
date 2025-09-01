package abbas17kh.weatherapp.data.json

import androidx.annotation.Keep
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@Keep
data class Current(
    @SerialName("last_updated")
    val lastUpdated: String,
    val temp_c: Double,
    val feelslike_c: Double,
    val condition: CurrentCondition
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@Keep
data class CurrentCondition(
    val text: String,
    val icon: String
)
