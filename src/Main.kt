import java.io.File
import java.lang.Integer.reverse
import java.util.BitSet
import java.util.zip.CRC32
import java.util.zip.Checksum
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    try {
        println(args[0])
        val file = File(args[0])
        println("${file.length()} bytes")
        val input: ByteArray = file.inputStream().readBytes()
        System.out.printf("Calculated a CRC my of 0x%x\n", crc32(input))

        val checksum: Checksum = CRC32()
        checksum.update(input, 0, input.size)
        System.out.printf("Calculated a CRC lib of 0x%x\n", checksum.value)
    } catch (e: Exception) {
        exitProcess(-1)
    }
}

fun crc32(data: ByteArray): Int {
    val bitSet = BitSet.valueOf(data)
    var crc32 = -0x1 // initial value https://medium.com/@hanru.yeh/color-int-of-argb-in-kotlin-fb609b07439f
    for (i in 0 until data.size * 8) {
        crc32 = if (crc32 ushr 31 and 1 != (if (bitSet[i]) 1 else 0)) crc32 shl 1 xor 0x04C11DB7 // xor with polynomial
        else crc32 shl 1
    }
    crc32 = reverse(crc32) // result reflect
    return crc32 xor -0x1 // final xor value
}