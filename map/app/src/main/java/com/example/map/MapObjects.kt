package com.example.map
import androidx.compose.ui.graphics.Color


data class Polygon(val id: String, val points: List<Pair<Double, Double>>, var color: Color = commonWhite) {

    fun updateColor(newColor: Color): Unit {
        color = newColor
    }
}

class ColoringMap {
    private val polygons: MutableMap<String, Polygon> = mutableMapOf()

    fun addPolygon(polygon: Polygon): Unit {
        polygons[polygon.id] = polygon
    }

    fun removePolygon(id: String) {
        polygons.remove(id)
    }

    fun getPolygon(id: String): Polygon? {
        return polygons[id]
    }

    fun updatePolygonColor(id: String, color: Color) {
        polygons[id]?.updateColor(color)
    }
}
