package game.example.map
import kotlin.random.Random

data class Square(
    var id: Int,
    var xRelative: Int,
    var yRelative: Int
)

data class Painter(
    var isGettingColor: Boolean = false,
    var currentColor: Int = 0
)

data class Polygon(
    var id: Int,
    var squares: List<Square>,
    var color: Int = 0,
    var neighbors: MutableMap<Int, Polygon> = mutableMapOf(),
    var border: List<List<Pair<Int, Int>>> = listOf(),
    var changeableColor: Boolean = true
) {

    fun updateBorder() {
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

    fun isUpdatingColorPossible(newColor: Int) : Boolean {
        if (!changeableColor) {
            return false
        }

        if (newColor == 0) {
            return true
        }

        for (polygon in neighbors) {
            if (polygon.value.color == newColor) {
                return false
            }
        }

        return true
    }

    fun updateColor(newColor: Int) {
        color = newColor
    }

    fun hasSquareOnCoordinates(x: Int, y: Int): Boolean {
        for (square in squares) {
            if (square.xRelative == x && square.yRelative == y) return true
        }
        return false
    }
}

class MapPolygons(polygonCount: Int = 0 , colorCount: Int = 0) {
    private var polygons: List<Polygon> = mutableListOf()
    private var possibleColorCount: Int = colorCount

    fun getPolygons(): List<Polygon> {
        return polygons
    }

    fun createMap(polygonCount: Int, colorCount: Int) {
        generateGridMap(polygonCount, colorCount)
    }

    private fun generateGridMap(polygonCount: Int, colorCount: Int) {
        val squares = generateSquares()
        val polygonsList = initPolygons(squares)

        groupPolygons(polygonCount, polygonsList)
        generateBorders(polygonsList)

        polygons = polygonsList
        possibleColorCount = colorCount

        for (i in 1..possibleColorCount) {
            colorRandomPolygon(i)
        }
    }

    fun hintColoring() {
        for (polygon in polygons) {
            for (i in 1..possibleColorCount) {
                if (polygon.color == 0 && polygon.isUpdatingColorPossible(i)){
                    polygon.updateColor(i)

                    isGamePassed()
                    return
                }
            }
        }
    }

    private fun isGamePassed() {
        for (polygon in polygons) {
            if (polygon.color == 0)
                return

//            for (neighbor in polygon.neighbors) {
//                if (neighbor.value.color == polygon.color)
//                    return
//            }
        }

        finishGame()
    }

    fun getPolygonByCords(coords: Pair<Int, Int>): Polygon {
        for (polygon in polygons) {
            if (polygon.hasSquareOnCoordinates(coords.first, coords.second))
                return polygon
        }

        return Polygon(
            -1,
            squares = listOf()
        )
    }

    fun updatePolygonColor(cords: Pair<Int, Int>, newColor: Int) {
        val polygon = getPolygonByCords(cords)

        if (!polygon.isUpdatingColorPossible(newColor)) {
            return
        }

        polygons[id].updateColor(newColor)
        isGamePassed()
        return
    }

    private fun finishGame() {
        // TBD
        println("YOU FINISHED GAME!")
        return
    }

    private fun colorRandomPolygon(colorNumber: Int) {
        while (true) {
            val randomPolygonId: Int = Random.nextInt(polygons.size)

            if (!polygons[randomPolygonId].isUpdatingColorPossible(colorNumber)) {
                continue
            }

            polygons[randomPolygonId].updateColor(colorNumber)
            polygons[randomPolygonId].changeableColor = false
            break
        }
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

    private fun initPolygons(squares: List<Square>): MutableList<Polygon>{
        val polygonsMap = mutableMapOf<Int, Polygon>()
        for (square in squares) {
            polygonsMap[square.id] = Polygon(
                    id = square.id,
                    squares = mutableListOf(square)
            )
        }

        polygonsMap.forEach{entry ->
            val neighbors = mutableMapOf<Int, Polygon>()

            val iterations = listOf(-10, 10)
            for (iteration in iterations) {
                if (entry.value.id + iteration in 0..99) {
                    neighbors[entry.value.id + iteration] = polygonsMap.getValue(entry.value.id + iteration)
                }
            }

            if (entry.value.id % 10 != 0) {
                neighbors[entry.value.id - 1] = polygonsMap.getValue(entry.value.id - 1)
            }

            if (entry.value.id % 10 != 9) {
                neighbors[entry.value.id + 1] = polygonsMap.getValue(entry.value.id + 1)
            }

            entry.value.neighbors = neighbors
        }

        return  polygonsMap.values.toMutableList()
    }

    private fun groupPolygons(resultPolygonCount: Int, polygons: MutableList<Polygon>) {
        while (polygons.size > resultPolygonCount) {
            var polygon = polygons.removeAt(Random.nextInt(polygons.size))

            for (neighbor in polygon.neighbors) {
                neighbor.value.neighbors.remove(polygon.id)
            }

            val neighborsList = polygon.neighbors.values.toMutableList()
            var selectedNeighbor = neighborsList.removeAt(Random.nextInt(neighborsList.size))

            polygon.neighbors.remove(selectedNeighbor.id)

            selectedNeighbor.neighbors += polygon.neighbors
            selectedNeighbor.squares += polygon.squares

            for (neighbor in selectedNeighbor.neighbors) {
                neighbor.value.neighbors[selectedNeighbor.id] = selectedNeighbor
            }
        }
    }

    private fun generateBorders(polygons: List<Polygon>) {
        polygons.forEach { polygon -> polygon.updateBorder() }
    }
}
