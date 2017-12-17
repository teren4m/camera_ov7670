package ua.com.todd.app

import java.io.IOException
import java.io.InputStream
import javax.comm.CommPortIdentifier

class SimpleRead(portId: CommPortIdentifier, w: Int, h: Int) {

    private val COMMAND = charArrayOf('*', 'R', 'D', 'Y', '*')

    init {

        try {
            val inputStream = portId.openSerialPort()

            val rgb = Matrix(h, w).array

            infinityLoop {
                println("Looking for image")
                while (!isImageStart(inputStream, 0)) {
                }

                for (y in 0 until h) {
                    for (x in 0 until w) {
                        val temp = read(inputStream)
                        rgb[y][x] = temp and 0xFF shl 16 or (temp and 0xFF shl 8) or (temp and 0xFF)
                    }
                }

                saveBMP("C:/Users/Timmy/Desktop/OUT/$it.bmp", rgb)
                println("Saved image: $it")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Throws(IOException::class)
    private fun read(inputStream: InputStream): Int = inputStream.read().apply {
        if (this == -1) {
            error("Exit")
        }
    }

    @Throws(IOException::class)
    private fun isImageStart(inputStream: InputStream, index: Int): Boolean {
        return if (index < COMMAND.size) {
            if (COMMAND[index].toInt() == read(inputStream)) {
                isImageStart(inputStream, index + 1)
            } else {
                isImageStart(inputStream, 0)
            }
        } else {
            true
        }
    }
}