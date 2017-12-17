package ua.com.todd.app

import javax.comm.CommPortIdentifier

const val PORT = "COM7"

const val WIDTH = 320 //640;
const val HEIGHT = 240 //480;

fun main(args: Array<String>) {
    val port = CommPortIdentifier.getPortIdentifiers()
            .toList<CommPortIdentifier>()
            .map {
                it.apply {
                    println("Port name: " + name)
                }
            }
            .first {
                it.name == PORT
            }
    SimpleRead(port, WIDTH, HEIGHT)
}