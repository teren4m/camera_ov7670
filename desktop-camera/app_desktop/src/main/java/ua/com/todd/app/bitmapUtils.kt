package ua.com.todd.app

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun saveBMP(filename: String, rgbValues: Array<IntArray>) {
    try {
        val fos = FileOutputStream(File(filename))

        val bytes = ByteArray(54 + 3 * rgbValues.size * rgbValues[0].size)

        saveFileHeader(bytes)
        saveInfoHeader(bytes, rgbValues.size, rgbValues[0].size)
        saveBitmapData(bytes, rgbValues)

        fos.write(bytes)

        fos.close()
    } catch (e: IOException) {
        throw IllegalStateException(e)
    }

}

private fun saveFileHeader(bytes: ByteArray) {
    bytes[0] = 'B'.toByte()
    bytes[1] = 'M'.toByte()

    bytes[5] = bytes.size.toByte()
    bytes[4] = (bytes.size shr 8).toByte()
    bytes[3] = (bytes.size shr 16).toByte()
    bytes[2] = (bytes.size shr 24).toByte()

    //data offset
    bytes[10] = 54
}

private fun saveInfoHeader(bytes: ByteArray, height: Int, width: Int) {
    bytes[14] = 40

    bytes[18] = width.toByte()
    bytes[19] = (width shr 8).toByte()
    bytes[20] = (width shr 16).toByte()
    bytes[21] = (width shr 24).toByte()

    bytes[22] = height.toByte()
    bytes[23] = (height shr 8).toByte()
    bytes[24] = (height shr 16).toByte()
    bytes[25] = (height shr 24).toByte()

    bytes[26] = 1

    bytes[28] = 24
}

private fun saveBitmapData(bytes: ByteArray, rgbValues: Array<IntArray>) {
    for (i in rgbValues.indices) {
        writeLine(bytes, i, rgbValues)
    }
}

private fun writeLine(bytes: ByteArray, row: Int, rgbValues: Array<IntArray>) {
    val offset = 54
    val rowLength = rgbValues[row].size
    for (i in 0 until rowLength) {
        val rgb = rgbValues[row][i]
        val temp = offset + 3 * (i + rowLength * row)

        bytes[temp + 2] = (rgb shr 16).toByte()
        bytes[temp + 1] = (rgb shr 8).toByte()
        bytes[temp] = rgb.toByte()
    }
}


