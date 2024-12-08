import java.io.File

fun stringsToCharMatrix(lines: List<String>): Array<CharArray> {
    val rows = lines.size
    val cols = lines[0].length

    val array = Array(rows) { CharArray(cols) }

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            array[i][j] = lines[i][j]
        }
    }

    return array
}

fun diagStrs(m: List<String>, inv: Boolean = false): List<String> {
    val shifted = mutableListOf<StringBuilder>()

    // Initialize StringBuilder for each row
    for (i in m.indices) {
        shifted.add(StringBuilder())
    }

    // max diagonal nrow + ncol - 1
    val ncol = m[0].length
    val maxd = m.size + ncol - 1

    for ((i, row) in m.withIndex()) {
        val pad = maxd - ncol - i
        val pref = if (inv) pad else i
        val sufx = if (inv) i else pad

        shifted[i].append("_".repeat(pref))
        shifted[i].append(row)
        shifted[i].append("_".repeat(sufx))
    }

    // Convert the StringBuilder objects to Strings
    return shifted.map { it.toString() }
}

fun transpStrs(strings: List<String>): List<String> {
    val transposed = mutableListOf<StringBuilder>()

    // Initialize StringBuilder for each column
    for (i in strings[0].indices) {
        transposed.add(StringBuilder())
    }

    // Populate the transposed list by iterating over each string and character
    for (str in strings) {
        for (i in str.indices) {
            transposed[i].append(str[i])
        }
    }

    // Convert the StringBuilder objects to Strings
    return transposed.map { it.toString() }
}

fun cntStrStr(str: String, s: String): Int {
    var cnt = 0
    var idx = 0
    for(w in str.windowed(s.length)) {
        if (w == s) {
            cnt++
        }
    }
    return cnt
}

fun findL2W(m: List<String>): Int {
    val cnts =
        m.map {
        	//println(it)
        	cntStrStr(it, "XMAS") + cntStrStr(it, "SAMX")
        }
    //println(cnts)
    return cnts.fold(0) { a, i -> a + i }
}

fun countXmas(m: List<String>): Int {
    var nxmas = 0
    val tm = transpStrs(m)
    val dm = transpStrs(diagStrs(m))
    val tdm = transpStrs(diagStrs(m, true))
    val ms = listOf(m, tm, dm, tdm)

    return ms.map { findL2W(it) }.fold(0) { a, i -> a + i }
}

fun d04_1(f: String): Int {
    val m = File(f).readLines()
    return countXmas(m)
}

fun hasMas(s: String): Boolean {
    return s == "MAS" || s == "SAM"
}

fun hasMasX(cm: Array<CharArray>, r: Int, c: Int): Boolean {
    var d1 = ""
    var d2 = ""
    for (i in 0..2) {
        d1 += cm[r + i][c + i]
        d2 += cm[r + (2 - i)][c + i]
    }
    return hasMas(d1) && hasMas(d2)
}

fun countMasX(m: List<String>): Int {
    //m.forEach { println(it) }
    val cm = stringsToCharMatrix(m)
    val nrow = cm.size
    val ncol = cm[0].size
    var nmas = 0

    for (rid in 1 until nrow - 1) {
        for (cid in 1 until ncol - 1) {
            if (cm[rid][cid] == 'A' && hasMasX(cm, rid - 1, cid - 1)) {
                nmas++
            }
        }
    }
    return nmas
}

fun d04_2(f: String): Int {
    val m = File(f).readLines()
    return countMasX(m)
}

fun main() {
    try {
        println(d04_1("input"))
        println(d04_2("input"))
    } catch (ex: Exception) {
        println("error: ${ex.message}")
    }
}
