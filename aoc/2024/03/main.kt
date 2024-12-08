import java.io.File

fun getMuls(txt: String): List<Pair<Int, Int>> {
    val reg = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
    return reg.findAll(txt).toList().map {
        Pair(it.groupValues[1].toInt(), it.groupValues[2].toInt())
    }
}

fun d03_1(f: String): Int {
    val txt = File(f).readText()
    return getMuls(txt).fold(0) { acc, it -> acc + it.first * it.second }
}

fun getMulsEn(txt: String): List<Pair<Int, Int>> {
    // whole,word,num,num
    val reg = Regex("""(mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\))""")
    var isEn = true

    return reg.findAll(txt).toList().map {
        val gv = it.groupValues
        val intr = gv[0]
        val zp = Pair(0, 0)
        //println(gv)
        if (isEn && intr.startsWith("mul")) {
            return@map Pair(it.groupValues[2].toInt(), it.groupValues[3].toInt())
        } else if (intr.startsWith("don")) {
            isEn = false
        } else if (intr.startsWith("do")) {
            isEn = true
        }
        return@map zp
    }
}

fun d03_2(f: String): Int {
    val txt = File(f).readText()
    val muls = getMulsEn(txt)
    //println(muls)
    return muls.fold(0) { acc, it -> acc + it.first * it.second }
}

fun main() {
    try {
        println(d03_1("input"))
        println(d03_2("input"))
    } catch (ex: Exception) {
        println("error: ${ex.message}")
    }
}