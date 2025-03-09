package com.example.map
import androidx.compose.ui.graphics.Color

data class Polygon(val id: String, val points: List<Pair<Int, Int>>, var color: Color = commonWhite) {

    fun updateColor(newColor: Color) {
        color = newColor
    }
}

class MapPolygons(polygonCount: Int) {

    private val polygons: MutableMap<String, Polygon> = mutableMapOf()

    init {
        generateGridMap(350, 350, polygonCount)
    }

    fun addPolygon(polygon: Polygon) {
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

    private fun generateGridMap(width: Int, height: Int, polygonCount: Int) {
        val columns = kotlin.math.sqrt(polygonCount.toDouble()).toInt()
        val rows = (polygonCount + columns - 1) / columns
        val cellWidth = width / columns
        val cellHeight = height / rows

        for (row in 0 until rows) {
            for (col in 0 until columns) {
                if (polygons.size >= polygonCount) return
                val x1 = col * cellWidth
                val y1 = row * cellHeight
                val x2 = x1 + cellWidth
                val y2 = y1 + cellHeight
                val points = listOf(
                    x1 to y1,
                    x2 to y1,
                    x2 to y2,
                    x1 to y2
                )
                val polygon = Polygon(id = "${row * columns + col}", points = points)
                addPolygon(polygon)
            }
        }
    }

}
