package ua.com.todd.app

class Matrix(val x: Int, val y: Int) {
    val array = Array(x) { IntArray(y) { 0 } }
}

fun createMatrix(w: Int, h: Int, f: (x: Int, y: Int) -> Int) {
    Matrix(w, h).array.mapIndexed { x, value ->
        value.mapIndexed { y, _ ->
            f(x, y)
        }
    }
}