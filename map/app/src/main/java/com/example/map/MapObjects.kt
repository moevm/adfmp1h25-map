package com.example.map
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

data class Square(
    var id: Int,
    var xRelative: Int,
    var yRelative: Int
)

data class Polygon(
    var squares: List<Square>,
    var color: Int = 0,
//    val neighbors: Set<Polygon>,
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

    fun updateColor(newColor: Int) {
        color = newColor
    }
}

class MapPolygons(polygonCount: Int, colorCount: Int) {
    private var polygons: List<Polygon> = mutableListOf()

    init {
        generateGridMap(polygonCount, colorCount)
    }

    fun getPolygons(): List<Polygon> {
        return polygons
    }

    private fun generateGridMap(polygonCount: Int, colorCount: Int) {
        var squares = generateSquares()
        var squaresGroups: MutableList<List<Square>> = mutableListOf()

        var randomIndices: List<Int> = (0..99)
            .filter { it !in 0..10 && it !in 90..100 && it % 10 != 0 && it % 10 != 9 }
            .shuffled()

        val directions: List<Int> = listOf(-1, 1, 10, -10)

        for (i in 0 .. (100 - polygonCount)){
            val randomIndex = randomIndices.last()
            randomIndices = randomIndices.dropLast(1).toMutableList()
            val direction = directions.random()

            println(randomIndex)
            println(direction)

            val square1 = squares.find { it.id == randomIndex }
            val square2 = squares.find { it.id == randomIndex + direction }

            if (square1 != null && square2 != null) {
                println(square1.id)
                println(square2.id)
                squares.removeIf { it.id == randomIndex }
                squares.removeIf { it.id == randomIndex + direction }
                squaresGroups.add(mutableListOf(square1, square2))
            }
        }

        val combinedSquares = squaresGroups + squares.map { listOf(it) }
        polygons = polygonsFromSquareGroups(combinedSquares)

    }

    private fun generateSquares(): MutableList<Square> {
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
            squareSet.forEach { square ->
                println("Square ID: ${square.id}, x: ${square.xRelative}, y: ${square.yRelative}")
            }
            println("_____________")
            polygons.add(
                Polygon(squareSet)
            )
        }

        return polygons
    }
}
