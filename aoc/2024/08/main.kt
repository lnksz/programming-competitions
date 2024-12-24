import java.io.File

data class Point(
    val x: Int,
    val y: Int,
) {
    fun mirrorTo(o: Point): Point {
        val dx = o.x - x
        val dy = o.y - y
        return Point(o.x + dx, o.y + dy)
    }
}

data class Vector(
    val p: Point,
    val t: Char,
)

data class AntMap(
    val xlim: Int,
    val ylim: Int,
    val ants: Map<Point, Char>) {
        
    fun print(sups: Set<Point> = emptySet()) {
        for (y in 0 until ylim) {
            for (x in 0 until xlim) {
                val p = Point(x, y)
                val char = when {
                    p in ants -> ants.get(p)
                    sups.contains(p) -> '#'
                    else -> '.'
                }
                print(char)
            }
            println()
        }
    }
    
    fun isInside(p: Point): Boolean {
        return p.x in 0 until xlim && p.y in 0 until ylim
    }
    
    fun runHalfLine(p: Point, o: Point): Set<Point> {
        var hl = mutableSetOf<Point>(o, p)
        var pnt = o
        var nxt = p.mirrorTo(o)
        while (isInside(nxt)) {
            hl.add(nxt)
            val tmp = pnt.mirrorTo(nxt)
            pnt = nxt
            nxt = tmp
        }
        return hl
    }
    
    fun runLine(p1: Point, p2: Point): Set<Point> {
        return runHalfLine(p1, p2).union(
            runHalfLine(p2, p1)
        )
    }
    
    fun calcSups(): List<Point> {
        var sups = mutableSetOf<Point>()
        val antsByType = mutableMapOf<Char,MutableList<Point>>()
        for ((p, t) in ants) {
            if (t in antsByType) {
                antsByType[t]!!.add(p)
            } else {
                antsByType[t] = mutableListOf<Point>(p)
            }
        }
        for ((t, ps) in antsByType) {
            ps.forEachIndexed { i, p ->
                val others = ps.filter {
                    it != p
                }
                others.forEach {
                    sups.add(p.mirrorTo(it))
                }
            }
        }
        return sups.filter {
            isInside(it)
        }
    }
    
    fun calcSups2(): List<Point> {
        var sups = setOf<Point>()
        val antsByType = mutableMapOf<Char,MutableList<Point>>()
        for ((p, t) in ants) {
            if (t in antsByType) {
                antsByType[t]!!.add(p)
            } else {
                antsByType[t] = mutableListOf<Point>(p)
            }
        }
        for ((t, ps) in antsByType) {
            ps.forEachIndexed { i, p ->
                val others = ps.filter {
                    it != p
                }
                others.forEach {
                    sups = sups.union(runLine(p,it))
                }
            }
        }
        return sups.filter {
            isInside(it)
        }
    }
}

fun parseAntMap(lines: List<String>): AntMap {
    val xlim = lines[0].length
    val ylim = lines.size
    val ants = mutableMapOf<Point,Char>()

    lines.forEachIndexed { ycoord, l ->
        l.forEachIndexed { xcoord, c ->
        	val p = Point(xcoord, ycoord)
            if (c == '.') {
                return@forEachIndexed
            }
            ants[p] = c
        }
    }
    return AntMap(xlim, ylim, ants)
}


fun d08_1(f: String): Int {
    val m = parseAntMap(File(f).readLines())
    //m.print()
    return m.calcSups().size
}

fun d08_2(f: String): Int {
    val m = parseAntMap(File(f).readLines())
    //m.print()
    return m.calcSups2().size
}

fun main() {
    try {
        println(d08_1("input"))
        println(d08_2("input"))
    } catch (ex: Exception) {
        println("error: ${ex.message}")
    }
}