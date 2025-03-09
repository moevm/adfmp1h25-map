package com.example.map

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

object SettingsDataStore {
    private val SELECTED_POLYGON = stringPreferencesKey("selectedPolygon")

    suspend fun saveSelectedPolygon(context: Context, value: Int) {
        context.dataStore.edit { settings ->
            settings[SELECTED_POLYGON] = value.toString()
        }
    }

    suspend fun getSelectedPolygon(context: Context): Int {
        return context.dataStore.data
            .map { it[SELECTED_POLYGON] ?: "10" }
            .first()
            .toInt()
    }
}
