package ua.com.todd.app

import java.io.InputStream
import java.util.*
import javax.comm.CommPortIdentifier
import javax.comm.SerialPort

fun <T> Enumeration<Any>.toList() = this.asSequence().toList() as List<T>

fun <T> CommPortIdentifier.openPort(str: String, count: Int) = open(str, count) as T

fun CommPortIdentifier.openSerialPort(): InputStream =
        openPort<SerialPort>("SimpleReadApp", 1000)
                .run {
                    setSerialPortParams(1000000,
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE)
                    inputStream
                }

inline fun infinityLoop(f: (Int) -> Unit) {
    var counter = 0
    while (true) {
        f(counter)
        counter++
    }
}