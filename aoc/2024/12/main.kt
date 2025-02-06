package d12

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import java.util.concurrent.Executors
import java.util.concurrent.ConcurrentSkipListSet

typealias Veg = Char

data class Point(
    val x: Int,
    val y: Int,
) {
    fun around(): List<Point> {
        return listOf(x, x, x-1, x+1).zip(
               listOf(y-1, y+1, y, y)
        ).map {
            Point(it.first, it.second)
        }
    }
}

data class GPlot(
    val p: Point,
    val v: Veg,
) : Comparable<GPlot> {
    override fun compareTo(other: GPlot): Int {
        // Define your comparison logic here
        if (this.v != other.v) {
            return this.v.compareTo(other.v)
        } 
        if (this.p.y != other.p.y) {
            return this.p.y - other.p.y
        }
        return this.p.x - other.p.x
    }
}

data class Fence(
    val inside: Point,
    val outside: Point,
) {
    fun isHorizontal(): Boolean {
        return inside.x == outside.x
    }
    fun isVertical(): Boolean {
        return !isHorizontal()
    }
    fun attaches(o: Fence): Boolean {
        val dxi = Math.abs(inside.x - o.inside.x)
        val dyi = Math.abs(inside.y - o.inside.y)
        val dxo = Math.abs(outside.x - o.outside.x)
        val dyo = Math.abs(outside.y - o.outside.y)
        if (isHorizontal() &&
            o.isHorizontal() &&
            (dxi == 1) &&
            (dxo == 1) &&
            (dyi == 0) &&
            (dyo == 0)) {
            return true
        } else if (isVertical() &&
            o.isVertical() &&
            (dxi == 0) &&
            (dxo == 0) &&
            (dyi == 1) &&
            (dyo == 1)) {
            return true
        } else {
            return false
        }
    }
}

typealias MutGRange = MutableSet<GPlot>

typealias GRange = Set<GPlot>

fun GRange.print(fcs: List<Fence> = emptyList()) {
    val lhis = this.toList()
    val v = lhis[0].v
    val ps = this.map {
      it.p
    }.toSet()
    val fs = fcs.flatMap {
        listOf(it.inside, it.outside)
    }.toSet()
    val allPoints = ps.toList() + fs.toList()
    val xlo = allPoints.minOf {it.x}
    val xhi = allPoints.maxOf {it.x}
    val ylo = allPoints.minOf {it.y}
    val yhi = allPoints.maxOf {it.y}
    
    println("Range of $v")
    println("x=$xlo..$xhi y=$ylo..$yhi")
    for (y in ylo..yhi) {
        for (x in xlo..xhi) {
            val p = Point(x, y)
            if (p in ps) {
                print(v)
            } else if (p in fs) {
                print('f')
            } else {
                print('.')
            }
        }
        println()
    }
}


fun GRange.getPerim(nhs: Map<GPlot, List<GPlot>>): Int {
    var perim = this.size * 4
    for (p in this) {
        val ns = nhs.get(p)!!.size
        perim -= ns
    }
    return perim
}

fun GRange.getCost1(nhs: Map<GPlot, List<GPlot>>): Int {
    return this.size * this.getPerim(nhs)
}

fun GRange.getPerim2(nhs: Map<GPlot, List<GPlot>>): Int {
    val border = this.filter { nhs.get(it)!!.size != 4 }
    var fences = mutableListOf<Fence>()
    for (gp in border) {
        val (x, y) = gp.p
        val ns = nhs.get(gp)!!.map {
            it.p
        }.toSet()
        for (p in gp.p.around()) {
            if (p !in ns) {
                fences.add(Fence(gp.p, p))
            }
        }
    }
    var perim = fences.size
    val dupli = mutableSetOf<Fence>()
    var edges = 0
    for (f in fences.sortedWith(compareBy({it.inside.y}, {it.inside.x}))) {
        val ns = fences.filter {f.attaches(it)}
        //println("@ $f")
        //ns.forEach{println(it)}
        dupli.addAll(ns)
        if (f !in dupli) {
            edges++
            //println("> $f")
        }
    }
    //this.print(fences)
    //println("s: ${this.size} p: $perim e: $edges")
    return edges
}

fun GRange.getCost2(nhs: Map<GPlot, List<GPlot>>): Int {
    return this.size * this.getPerim2(nhs)
}

typealias Matrix = List<List<Veg>>

fun Matrix.getNeighs(n: GPlot): List<GPlot> {
    val xlim = this[0].size - 1
    val ylim = this.size - 1
    val x = n.p.x
    val y = n.p.y
    return listOf(
            Point(x - 1, y),
            Point(x + 1, y),
            Point(x, y - 1),
            Point(x, y + 1),
        )
        .filter { it.x in 0..xlim && it.y in 0..ylim }
        .map { GPlot(it, this[it.y][it.x]) }
        .filter { it.v == n.v }
}

data class Garden(
    val xlim: Int,
    val ylim: Int,
    val gps: List<GPlot>,
    val veggies: List<Veg>,
    //var todos: MutableList<GPlot>,
    var todos: ConcurrentSkipListSet<GPlot>,
    // neighbors of same type
    val nhbors: Map<GPlot, List<GPlot>>,
    var ranges: Map<Veg, MutableList<MutGRange>>,
) {
    fun print() {
        gps.chunked(ylim) { row ->
            row.forEach { print(it.v) }
            println()
        }
        println(veggies)
        // printNBors()
        printRanges()
    }

    fun printNBors() {
        for ((n, ns) in nhbors) {
            println(n)
            ns.forEach { println("  $it") }
        }
    }

    fun printRanges() {
        for ((k, rs) in ranges) {
            println(k)
            rs.forEach { println("${it.size} $it") }
        }
    }

    fun createRange(gp: GPlot): MutGRange {
        var r = ranges.get(gp.v)!!
        var s = mutableSetOf<GPlot>(gp)
        r.add(s)
        return s
    }

    fun addNeighs(nhs: List<GPlot>) {
        for (n in nhs) {
            addToRange(n)
        }
    }

    fun addToRange(gp: GPlot) {
        if (gp !in todos) {
            return
        }
        todos.remove(gp)
        val vegRanges = ranges.get(gp.v)!!
        val nhs = nhbors.get(gp)!!
        val nhss = nhs.toSet()
        for (r in vegRanges) {
            if (!nhss.intersect(r).isEmpty()) {
                r.add(gp)
                addNeighs(nhs)
                return
            }
        }
        createRange(gp)
        addNeighs(nhs)
    }
    
    fun calcRangesTask(gpl: List<GPlot>) {
        for (gp in gpl) {
            if (gp in todos) {
                addToRange(gp)
            }
        }
    }

    fun calcRanges() {
        val numCores = Runtime.getRuntime().availableProcessors()
        val executor = Executors.newFixedThreadPool(numCores)
        val vegPlots = gps.groupBy {it.v}
        for ((veg, plts) in vegPlots) {
            executor.submit {
                calcRangesTask(plts)
            }
        }
        executor.shutdown()
        executor.awaitTermination(3, TimeUnit.SECONDS)
    }
    
    fun calcCost1Task(ranges: List<GRange>): Int {
        return ranges.map {
            it.getCost1(nhbors)
        }.sum()
    }

    fun calcCost1(): Int {
        val numCores = Runtime.getRuntime().availableProcessors()
        val executor = Executors.newFixedThreadPool(numCores)
        val rngs = ranges.values.flatMap {it}.chunked(numCores)
        val futures = rngs.map {
            executor.submit<Int> {
                calcCost1Task(it)
            }
        }
        executor.shutdown()
        executor.awaitTermination(2, TimeUnit.SECONDS)
        return futures.map {
            it.get()
        }.sum()
    }

    fun calcCost2(): Int {
        var cost = 0
        for ((_, rs) in ranges) {
            for (r in rs) {
                cost += r.getCost2(nhbors)
            }
        }
        return cost
    }
}

fun parseInput(lines: List<String>): Garden {
    var sorts = mutableSetOf<Veg>()
    val GPS = mutableListOf<GPlot>()
    var M = mutableListOf<List<Veg>>()
    lines.forEachIndexed { y, l ->
        var row = mutableListOf<Veg>()
        l.forEachIndexed { x, c ->
            sorts.add(c)
            row.add(c)
            GPS.add(GPlot(Point(x, y), c))
        }
        M.add(row)
    }

    var nhbors = mutableMapOf<GPlot, List<GPlot>>()
    GPS.forEach { nhbors[it] = M.getNeighs(it) }
    //var todos = GPS.toMutableList()
    var todos = ConcurrentSkipListSet<GPlot>()
    todos.addAll(GPS)

    val veggies = sorts.sorted().toList()
    var ranges = mutableMapOf<Veg, MutableList<MutableSet<GPlot>>>()
    veggies.forEach { ranges[it] = mutableListOf<MutableSet<GPlot>>() }

    return Garden(
        lines[0].length,
        lines.size,
        GPS,
        veggies,
        todos,
        nhbors,
        ranges,
    )
}

fun log(message: String) {
  val currentDateTime = LocalDateTime.now()
  val formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS") 
  val formattedTime = currentDateTime.format(formatter)
  println("[$formattedTime] $message")
}


fun part1(f: String): Int {
    log("Part 1 - $f")
    val G = parseInput(File(f).readLines())
    log("Rangify")
    G.calcRanges()
    log("calcCost1")
    val res = G.calcCost1()
    log("Finished")
    // G.print()
    return res
}


fun part2(f: String): Int {
    log("Part 2 - $f")
    val G = parseInput(File(f).readLines())
    log("Rangify")
    G.calcRanges()
    log("calcCost2")
    val res = G.calcCost2()
    log("Finished")
    //G.print()
    return res
}

fun main() {
    try {
        println(part1("sample")) // 1930
        println(part1("input"))  // 1533644
        println(part2("sample")) // 1206
        println(part2("input"))  // 936718
    } catch (ex: Exception) {
        println("error: ${ex.message}")
    }
}
