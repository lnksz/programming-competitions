package d12

import java.io.File
import java.time.LocalTime

typealias Veg = Char

data class Point(
    val x: Int,
    val y: Int,
)

data class GPlot(
    val p: Point,
    val v: Veg,
)

typealias MutGRange = MutableSet<GPlot>
typealias GRange = Set<GPlot>

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
    var perim = this.filter {
        nhs.get(it)!!.size != 4
    }
    
    return 42
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
        Point(x-1, y),
        Point(x+1, y),
        Point(x, y-1),
        Point(x, y+1),
    ).filter {
        it.x in 0..xlim && it.y in 0..ylim
    }.map {
        GPlot(it, this[it.y][it.x])
    }.filter {
        it.v == n.v
    }
}

data class Garden(
    val xlim: Int,
    val ylim: Int,
    val gps: List<GPlot>,
    val veggies: List<Veg>,
    var todos: MutableList<GPlot>,
    val nhbors: Map<GPlot, List<GPlot>>,
    var ranges: Map<Veg, MutableList<MutGRange>>,
) {
    fun print() {
        gps.chunked(ylim) { row ->
        	row.forEach {print(it.v)}
            println()
        }
        println(veggies)
        //printNBors()
        printRanges()
    }
    
    fun printNBors() {
        for ((n, ns) in nhbors) {
            println(n)
            ns.forEach {
                println("  $it")
            }
        }
    }
    
    fun printRanges() {
        for ((k, rs) in ranges) {
            println(k)
            rs.forEach {
                println("${it.size} $it")
            }
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
        for (r in vegRanges) {
            if (!nhs.toSet().intersect(r).isEmpty()) {
                r.add(gp)
                addNeighs(nhs)
                return
            }
        }
        createRange(gp)
        addNeighs(nhs)
    }
    
    fun calcRanges() {
        for (gp in gps) {
            if (gp in todos) {
                addToRange(gp)
            }
        }
    }
    
    fun calcCost1(): Int {
        var cost = 0
        for ((_, rs) in ranges) {
            for (r in rs) {
                cost += r.getCost1(nhbors)
            }
        }
        return cost
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
    var todos = GPS.toMutableList()
    
    val veggies = sorts.sorted().toList()
    var ranges = mutableMapOf<Veg, MutableList<MutableSet<GPlot>>>()
    veggies.forEach {
        ranges[it] = mutableListOf<MutableSet<GPlot>>()
    }
    
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


fun part1(f: String): Int {
    println("Part 1 - $f")
    val G = parseInput(File(f).readLines())
    println("Init ${LocalTime.now()}")
    G.calcRanges()
    val res = G.calcCost1()
    println("Fini ${LocalTime.now()}")
    //G.print()
    return res
}

fun part2(f: String): Int {
    println("Part 2 - $f")
    val G = parseInput(File(f).readLines())
    println("Init ${LocalTime.now()}")
    G.calcRanges()
    val res = G.calcCost2()
    println("Fini ${LocalTime.now()}")
    //G.print()
    return res
}

fun main() {
    try {
        println(part1("sample")) // 1930
        println(part1("input"))  // 1533644
        //println(part2("sample"))
        //println(part2("input"))
    } catch (ex: Exception) {
        println("error: ${ex.message}")
    }
}
