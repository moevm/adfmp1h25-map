package com.example.map
import androidx.compose.ui.graphics.Color

data class Square(
    var id: Int,
    var xRelative: Int,
    var yRelative: Int
)

data class Polygon(
    var squares: List<Square>,
    var color: Color? = commonWhite,
    var border: List<List<Pair<Int, Int>>> = listOf()
) {
    init {
        val squareIdSet = mutableSetOf<Int>()

        for (square in squares){
            squareIdSet.add(square.id)
        }

        val borders = mutableListOf<List<Pair<Int, Int>>>()

        for (square in squares){
            val current = square.id

            if (!squareIdSet.contains(current - 1)){
                borders.add(
                    listOf(
                        Pair(square.xRelative, square.yRelative),
                        Pair(square.xRelative, square.yRelative + 1),
                        )
                )
            }

            if (!squareIdSet.contains(current + 1)){
                borders.add(
                    listOf(
                        Pair(square.xRelative + 1, square.yRelative),
                        Pair(square.xRelative + 1, square.yRelative + 1),
                    )
                )
            }

            if (!squareIdSet.contains(current - 10)){
                borders.add(
                    listOf(
                        Pair(square.xRelative, square.yRelative),
                        Pair(square.xRelative + 1, square.yRelative),
                    )
                )
            }

            if (!squareIdSet.contains(current + 10)){
                borders.add(
                    listOf(
                        Pair(square.xRelative, square.yRelative + 1),
                        Pair(square.xRelative + 1, square.yRelative + 1),
                    )
                )
            }
        }

        border = borders
    }

    fun updateColor(newColor: Color) {
        color = newColor
    }
}

class MapPolygons(polygonCount: Int) {
    private var polygons: List<Polygon> = mutableListOf()

    init {
        generateGridMap(polygonCount)
    }

    fun getPolygons(): List<Polygon> {
        return polygons
    }

    private fun generateGridMap(polygonCount: Int) {
        var squares = generateSquares()

        // divide into groups for polygons

        polygons = polygonsFromSquareGroups(squares.map { it -> listOf(it) })
    }

    private fun generateSquares(): List<Square> {
        val squares = mutableListOf<Square>()

        for (x in 0..9) {
            for (y in 0..9) {
                squares.add(
                    Square(
                        id = x + 10*y,
                        xRelative = x,
                        yRelative = y
                )
                )

            }
        }

        return squares
    }

    private fun polygonsFromSquareGroups(squares: List<List<Square>>): List<Polygon> {
        val polygons = mutableListOf<Polygon>()

        for (squareSet in squares) {
            polygons.add(
                Polygon(squareSet)
            )
        }

        return polygons
    }
}
