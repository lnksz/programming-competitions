import java.io.File
import java.time.LocalTime

enum class Dir {
    N, E, S, W;
    fun turn(): Dir {
        return values()[(ordinal + 1) % values().size]
    }
}

data class Point(
    val x: Int,
    val y: Int,
)

data class Vector(
    val p: Point,
    val d: Dir,
)

data class FloorMap(
    val xlim: Int,
    val ylim: Int,
    val obsts: List<Point>,
    val start: Vector,
) {
    fun isInside(p: Point): Boolean {
        return p.x in 0 until xlim && p.y in 0 until ylim
    }

    fun print(visited: Set<Vector> = emptySet()) {
        for (y in 0 until ylim) {
            for (x in 0 until xlim) {
                val p = Point(x, y)
                val char = when {
                    obsts.contains(p) -> '#'
                    start.p == p -> 'S'
                    visited.any { it.p == p } -> '*'
                    else -> '.'
                }
                print(char)
            }
            println()
        }
    }

    fun calcNext(v: Vector): Vector {
        val n = v.step()
        return if (obsts.contains(n.p)) {
            Vector(v.p, v.d.turn())
        } else {
            n
        }
    }

    fun isLoopy(): Boolean {
        var pos = start
        val visited = mutableSetOf<Vector>()
        while (isInside(pos.p)) {
            if (visited.contains(pos)) {
                return true
            }
            visited.add(pos)
            pos = calcNext(pos)
        }
        return false
    }
}

fun parseFloorMap(lines: List<String>): FloorMap {
    val xlim = lines[0].length
    val ylim = lines.size
    val guardChars = setOf('<', 'v', '>', '^')
    val obsts = mutableListOf<Point>()
    var dir = Dir.S
    var start = Point(0, 0)

    lines.forEachIndexed { ycoord, l ->
        l.forEachIndexed { xcoord, c ->
            when {
                guardChars.contains(c) -> {
                    start = Point(xcoord, ycoord)
                    dir = when(c) {
                        '<' -> Dir.W
                        'v' -> Dir.S
                        '>' -> Dir.E
                        '^' -> Dir.N
                        else -> throw IllegalArgumentException(
                            "Should never happen $c"
                        )
                    }
                }
                c == '#' -> obsts.add(Point(xcoord, ycoord))
            }
        }
    }
    return FloorMap(xlim, ylim, obsts, Vector(start, dir))
}

fun Vector.step(): Vector {
    val (x, y) = when (d) {
        Dir.N -> p.x to p.y - 1
        Dir.S -> p.x to p.y + 1
        Dir.W -> p.x - 1 to p.y
        Dir.E -> p.x + 1 to p.y
    }
    return Vector(Point(x, y), d)
}

fun d06_1(f: String): Int {
    val fm = parseFloorMap(File(f).readLines())
    var next = fm.start
    val visited = mutableSetOf(next.p)
    
    while (fm.isInside(next.p)) {
        visited.add(next.p)
        next = fm.calcNext(next)
    }
    return visited.size
}

fun d06_2(f: String): Int {
    println("Init ${LocalTime.now()}")
    val fm = parseFloorMap(File(f).readLines())
    var pos = fm.start
    val visited = mutableSetOf<Vector>()
    val loopers = mutableSetOf<Point>()
    
    while (fm.isInside(pos.p)) {
        val next = fm.calcNext(pos)
        if (next.p == fm.start.p || visited.contains(next)) {
            visited.add(pos)
            pos = next
            continue
        }
        
        val modifiedMap = FloorMap(
            fm.xlim,
            fm.ylim,
            fm.obsts + next.p,
            fm.start
        )
        
        if (modifiedMap.isLoopy()) {
            loopers.add(next.p)
        }
        visited.add(pos)
        pos = next
    }
    println("Fini ${LocalTime.now()}")
    return loopers.size
}


fun main() {
    try {
        println(d06_1("input"))
        println(d06_2("input"))
        // 1696 1563 nope
    } catch (ex: Exception) {
        println("error: ${ex.message}")
    }
}
