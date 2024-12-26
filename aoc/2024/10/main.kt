import java.io.File
import java.time.LocalTime

data class Point(
    val x: Int,
    val y: Int,
)

typealias Topo = Int
const val TOPO_MAX = 9

data class Node(
    val p: Point,
    val t: Topo,
)

data class TopoGraph(
    val starts: List<Node>,
    val kidsMap: Map<Node, List<Node>>,
) {
    fun printKids() {
        for ((n, kids) in kidsMap) {
            println(n)
            kids.forEach {
                println("  $it")
            }
        }
    }
}

typealias Matrix = List<List<Topo>>
fun Matrix.getKids(n: Node): List<Node> {
    if (n.t == TOPO_MAX) {
        return emptyList()
    }
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
        Node(it, this[it.y][it.x])
    }.filter {
        it.t - n.t == 1
    }
    // if diagonals would be allowed:
    //val xlim = this[0].size - 1
    //val ylim = this.size - 1
    //val x = n.p.x
    //val y = n.p.y
    //val xlo = if (x-1<0) {0} else {x-1}
    //val ylo = if (y-1<0) {0} else {y-1}
    //val xhi = if (x+1>xlim) {xlim} else {x+1}
    //val yhi = if (y+1>ylim) {ylim} else {y+1}
    //var kids = mutableListOf<Node>()
    //for (xi in xlo..xhi) {
    //    for (yi in ylo..yhi) {
    //        val p = Point(xi, yi)
    //        val t = this[yi][xi]
    //        if (p == n.p || (t - n.t) != 1) (
    //            continue 
    //        )
    //        kids.add(Node(p, t))
    //    }
    //}
    //return kids
}

fun parseTopoMap(lines: List<String>): TopoGraph {
    val M: Matrix = lines.map { l ->
        l.toCharArray().map {
            it.digitToInt()
        }
    }
    var starts = mutableListOf<Node>()
    var km = mutableMapOf<Node, List<Node>>()
    M.forEachIndexed { y, row ->
        row.forEachIndexed { x, t ->
            val n = Node(Point(x, y), t)
        	val kids = M.getKids(n)
            km[n] = kids
            if (t == 0) {
                starts.add(n)
            }
        }
    }
    //lines.forEach{println(it)}
    return TopoGraph(starts, km)
}

fun climb(tg: TopoGraph, start: Node): Set<Node> {
    var nexts = tg.kidsMap[start]
    if (nexts == null || nexts.isEmpty()) {
        return if (start.t == TOPO_MAX) {
            setOf(start)
        } else {
            emptySet()
        }
    }
    var peeks = mutableSetOf<Node>()
    nexts.forEach {
        peeks.addAll(climb(tg, it))
    }
    //println("from $start")
    //peeks.forEach {println("  $it")}
    return peeks
}

fun climb2(tg: TopoGraph, start: Node): Int {
    var nexts = tg.kidsMap[start]
    if (nexts == null || nexts.isEmpty()) {
        return if (start.t == TOPO_MAX) {
            1
        } else {
            0
        }
    }
    return nexts.map {
        climb2(tg, it)
    }.fold(0) {a, x -> a + x}
}

fun part1(f: String): Int {
    println("Part 1 - $f")
    val m = parseTopoMap(File(f).readLines())
    //m.printKids()
    println("Init ${LocalTime.now()}")
    //return climb(m, m.starts[4]).size
    val npeeks = m.starts.map {
        climb(m, it).size
    }
    println("Fini ${LocalTime.now()}")
    return npeeks.fold(0) { a, x ->
    	a + x
    }
}

fun part2(f: String): Int {
    println("Part 2 - $f")
    val m = parseTopoMap(File(f).readLines())
    //m.printKids()
    println("Init ${LocalTime.now()}")
    //return climb(m, m.starts[4]).size
    val ntrails = m.starts.map {
        climb2(m, it)
    }
    println("Fini ${LocalTime.now()}")
    return ntrails.fold(0) { a, x ->
    	a + x
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