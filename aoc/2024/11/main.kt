package d11

import java.io.File
import java.time.LocalTime

typealias Stone = Long
typealias StoneBlinkCache = MutableMap<Int, MutableMap<Stone, Long>>

fun parseInput(lines: List<String>): List<Stone> {
    return lines[0].split(' ').map { it.toLong() }
}

fun Stone.isEvenDigitsLong(): Boolean {
    val ndigits = this.toString().length
    return (ndigits and 0x1) == 0
}

fun String.trimLeadingZeros(): String {
    val trimmed = this.trimStart { it == '0' }
    return if (trimmed.isEmpty()) {
        "0"
    } else {
        trimmed
    }
}

fun Stone.split(): List<Stone> {
    val txt = this.toString()
    val len = txt.length
    return listOf(
        txt.substring(0, len / 2).toLong(),
        txt.substring(len / 2, len).trimLeadingZeros().toLong(),
    )
}

fun Stone.blink(): List<Stone> {
    return when {
        this == 0L -> listOf(1L)
    	this.isEvenDigitsLong() -> this.split()
    	else -> listOf(this * 2024L)
    }
}

fun blink(stones: List<Stone>): List<Stone> {
    return stones.flatMap {
        it.blink()
    }
}

fun blinker(stones: List<Stone>, n: Int): List<Stone> {
    var sts = stones
    for (b in 1..n) {
        sts = blink(sts)
    }
    return sts
}

fun part1(f: String): Int {
    println("Part 1 - $f")
    val stones = parseInput(File(f).readLines())
    println("Init ${LocalTime.now()}")
    val res = blinker(stones, 25)
    println("Fini ${LocalTime.now()}")
    return res.size
}

fun countDFS(stone: Stone, toGo: Int, cache: StoneBlinkCache): Long {
    if (toGo == 0) {
        return 1L
    }
    if (!cache.containsKey(toGo)) {
        cache[toGo] = mutableMapOf<Stone, Long>()
    }
    val depthCache = cache[toGo]!!
    if (depthCache.containsKey(stone)) {
        return depthCache.get(stone)!!
        //.also {
        //    println("reused $toGo $stone $it")
        //}
    }
    val cnt = stone.blink().sumOf {countDFS(it, toGo - 1, cache)}
    depthCache[stone] = cnt
    return cnt
}

fun blinkerDFS(stones: List<Stone>, n: Int): Long {
    var cache: StoneBlinkCache = mutableMapOf()
    return stones.map {countDFS(it, n, cache)}.sum()
}

fun part2(f: String): Long {
    println("Part 2 - $f")
    val stones = parseInput(File(f).readLines())
    println("Init ${LocalTime.now()}")
    val res = blinkerDFS(stones, 75)
    println("Fini ${LocalTime.now()}")
    return res
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