package game.example.map

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("statistics")

object StatisticsDataStore {
    private val EASY_SOLVED = intPreferencesKey("easy_solved")
    private val EASY_BEST_TIME = stringPreferencesKey("easy_best_time")

    private val MEDIUM_SOLVED = intPreferencesKey("medium_solved")
    private val MEDIUM_BEST_TIME = stringPreferencesKey("medium_best_time")

    private val HARD_SOLVED = intPreferencesKey("hard_solved")
    private val HARD_BEST_TIME = stringPreferencesKey("hard_best_time")

    suspend fun getStatistics(context: Context): Map<String, Pair<Int, String>> {
        return context.dataStore.data.map { settings ->
            mapOf(
                "Easy" to Pair(settings[EASY_SOLVED] ?: 0, settings[EASY_BEST_TIME] ?: "--:--:--"),
                "Medium" to Pair(settings[MEDIUM_SOLVED] ?: 0, settings[MEDIUM_BEST_TIME] ?: "--:--:--"),
                "Hard" to Pair(settings[HARD_SOLVED] ?: 0, settings[HARD_BEST_TIME] ?: "--:--:--")
            )
        }.first()
    }

    suspend fun updateStatistics(context: Context, difficulty: String, time: Int) {
        val hours: Int = time / 3600
        val minutes: Int = (time - hours * 3600) / 60
        val seconds: Int = time % 60
        val timeStr: String = "%02d:%02d:%02d".format(hours, minutes, seconds)
        println(difficulty)
        println(difficulty == "Easy")

        context.dataStore.edit { settings ->
            if (difficulty == "Easy") {
                settings[EASY_SOLVED] = (settings[EASY_SOLVED] ?: 0) + 1
                val bestTime = settings[EASY_BEST_TIME] ?: timeStr
                if (bestTime == "--:--:--" || bestTime >= timeStr)
                    settings[EASY_BEST_TIME] = timeStr
            }
            if (difficulty == "Medium") {
                settings[MEDIUM_SOLVED] = (settings[MEDIUM_SOLVED] ?: 0) + 1
                val bestTime = settings[MEDIUM_BEST_TIME] ?: timeStr
                if (bestTime == "--:--:--" || bestTime >= timeStr)
                    settings[MEDIUM_BEST_TIME] = timeStr
            }


            if (difficulty == "Hard") {
                settings[HARD_SOLVED] = (settings[HARD_SOLVED] ?: 0) + 1
                val bestTime = settings[HARD_BEST_TIME] ?: timeStr
                if (bestTime == "--:--:--" || bestTime >= timeStr)
                    settings[HARD_BEST_TIME] = timeStr
            }

        }
    }

    suspend fun defaultStatistics(context: Context) {
        context.dataStore.edit { settings ->
            settings[EASY_SOLVED] = 0
            settings[MEDIUM_SOLVED] = 0
            settings[HARD_SOLVED] = 0
            settings[EASY_BEST_TIME] = "00:00:00"
            settings[MEDIUM_BEST_TIME] = "00:00:00"
            settings[HARD_BEST_TIME] = "00:00:00"
        }
    }
}
