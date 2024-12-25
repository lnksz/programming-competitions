package d9
import java.io.File

const val FREE_IDX = -1

data class BlockRange(
    val id: Int,
    val size: Int) {
    fun isFree(): Boolean {
        return id == FREE_IDX
    }
}

fun expand(l: String): List<Int> {
    val a = mutableListOf<Int>()
    var id = 0
    l.chunked(2) {
        val nb = it[0].digitToInt()
        for (i in 0 until nb) {
            a.add(id)
        }
        id++
        if (it.length < 2) {
            return@chunked
        }
        val nf = it[1].digitToInt()
        for (i in 0 until nf) {
            a.add(FREE_IDX)
        }
    }
    return a
}

fun expand2(brs: List<BlockRange>): List<Int> {
    val exp = mutableListOf<Int>()
    brs.forEach { br ->
        exp.addAll(List(br.size) {br.id})
    }
    return exp
}

fun parseToRanges(l: String): List<BlockRange> {
    val a = mutableListOf<BlockRange>()
    var id = 0
    l.chunked(2) {
        val nb = it[0].digitToInt()
        a.add(BlockRange(id++, nb))
        if (it.length < 2) {
            return@chunked
        }
        val nf = it[1].digitToInt()
        a.add(BlockRange(FREE_IDX, nf))
    }
    return a
}

fun <T> List<T>.lastIndexOfAny(predicate: (T) -> Boolean): Int {
    for (idx in lastIndex downTo 0) {
        if (predicate(this[idx])) {
            return idx
        }
    }
    return -1
}

fun compact(inp: MutableList<Int>): List<Int> {
    val nFree = inp.count {it == FREE_IDX}
    val targetPartIdx = inp.size - nFree
    var idf = inp.indexOf(FREE_IDX)
    var idb = inp.lastIndexOfAny { it > FREE_IDX }
    while (idf < targetPartIdx) {
        inp[idf] = inp[idb].also {
            inp[idb] = inp[idf]
        }
        idb = inp.lastIndexOfAny { it > FREE_IDX }
        idf = inp.indexOf(FREE_IDX)
    }
    return inp
}

fun optimizeOne(inp: List<BlockRange>): Pair<Int, Int> {
    inp.reversed().forEachIndexed { bidxRev, b ->
        if (b.isFree()) {
            return@forEachIndexed
        }
        val bidx = inp.size - 1 - bidxRev
        inp.forEachIndexed { fidx, f ->
            if (f.isFree() &&
            	f.size >= b.size &&
                fidx < bidx) {
                return fidx to bidx
            }
        }
    }
    return 0 to 0
}

fun compact2(inp: List<BlockRange>): List<BlockRange> {
    var out = inp.toMutableList()
    var opt = optimizeOne(out)
    while (opt != Pair(0, 0)) {
        val (fidx, bidx) = opt
        val freeBlk = out[fidx]
        val fileBlk = out[bidx]
        out[fidx] = fileBlk
        out[bidx] = BlockRange(FREE_IDX, fileBlk.size)
        val sizeDiff = freeBlk.size - fileBlk.size
        if (sizeDiff > 0) {
            out.add(fidx + 1, BlockRange(FREE_IDX, sizeDiff))
        }
        opt = optimizeOne(out)
    }
    return out
}

fun parseInput(lines: List<String>): String {
    return lines[0]
}

fun part1(f: String): Long {
    val l = parseInput(File(f).readLines())
    //println(l)
    val a = expand(l)
    //println(a)
    val c = compact(a.toMutableList())
    //println(c)
    return c
    .filter {
        it > FREE_IDX
    }
    .foldIndexed(0L) { i, a, e ->
        a + i * e
    }
}

fun part2(f: String): Long {
    val l = parseInput(File(f).readLines())
    //println(l)
    val blkRngs = parseToRanges(l)
    val blkRngsComped = compact2(blkRngs)
    val blkRngsExped = expand2(blkRngsComped)
    return blkRngsExped.foldIndexed(0L) { i, a, e ->
        return@foldIndexed if (e > FREE_IDX) {
            a + i * e
        } else {
            a
        }
    }
}

fun main() {
    try {
        println(part1("sample"))
        println(part1("input"))
        println(part2("sample"))
        println(part2("input"))
    } catch (ex: Exception) {
        println("error: ${ex.message}")
    }
}