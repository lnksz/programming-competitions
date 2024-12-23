import java.io.File

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
    val xhi: Int,
    val yhi: Int,
    val obsts: List<Point>,
    val guy: Vector,
)


fun parseFloorMap(lines: List<String>): FloorMap {
    val xdim = lines[0].length
    val ydim = lines.size
    val guardChars = arrayOf<Char>(
        '<', 'v', '>', '^'
    )
    var obsts = mutableListOf<Point>()
    var dir = Dir.S
    var start = Point(0, 0) 
    lines.forEachIndexed { ycoord, l ->
        l.forEachIndexed { xcoord, c ->
        	if (guardChars.contains(c)) {
                start = Point(xcoord, ycoord)
            }
        	when(c) {
                '<' -> dir = Dir.W
                'v' -> dir = Dir.S
                '>' -> dir = Dir.E
                '^' -> dir = Dir.N
                '#' -> obsts.add(Point(xcoord, ycoord))
            }
        }
    }
    return FloorMap(
        xdim, ydim,
        obsts, Vector(start, dir)
    )
}

fun FloorMap.isInside(p: Point): Boolean {
    return (p.x in 0 until xhi)
    	&& (p.y in 0 until yhi)
}

fun FloorMap.print(visited: Set<Vector> = emptySet()) {
    for (y in 0 until yhi) {
        for (x in 0 until xhi) {
            val p = Point(x, y)
            if (obsts.contains(p)) {
                print('#')
            } else if(guy.p == p) {
                print('S')
            } else if(visited.any{it.p == p}) {
                print('*')
            } else {
                print('.')
            }
        }
        println()
    }
}

fun Vector.step(): Vector {
   var x = p.x
   var y = p.y
   when (d) {
       Dir.N -> y--
       Dir.S -> y++
       Dir.W -> x--
       Dir.E -> x++
   }
   return Vector(Point(x, y), d)
}

// First AoC type extension!
fun FloorMap.calcNext(v: Vector): Vector {
    val n = v.step()
    if (obsts.contains(n.p)) {
       return Vector(v.p, v.d.turn()).step()
    }
    return n
}

//fun runBoyRun()


fun d06_1(f: String): Int {
    val lines = File(f).readLines()
    val fm = parseFloorMap(lines)
    var next = fm.guy
    var visited = mutableSetOf<Point>(next.p)
    while (fm.isInside(next.p)) {
        visited.add(next.p)
        next = fm.calcNext(next)
    }
    return visited.size
}

fun FloorMap.isLoopy(visit: Set<Vector>): Boolean {
    var next = guy
    var visited = visit.toMutableSet()
    while (isInside(next.p)) {
        if (visited.contains(next)) {
            return true
        }
        visited.add(next)
        next = calcNext(next)
    }
    return false
}

fun d06_2(f: String): Int {
    val lines = File(f).readLines()
    val fm = parseFloorMap(lines)
    //fm.print()
    var pos = fm.guy
    var visited = mutableSetOf<Vector>()
    var loopers = mutableSetOf<Point>()
    while (fm.isInside(pos.p)) {
        val next = fm.calcNext(pos)
        // Not on the guy...
        if (next.p == fm.guy.p) {
            visited.add(pos)
            pos = next
            continue
        }
        // add obstacle @next
        // is modified loopy?
        // continue on unaltered map
        val obstsMod: List<Point> = fm.obsts.plus(next.p)
        val fmMod = FloorMap(fm.xhi, fm.yhi, obstsMod, fm.guy)
        if (fmMod.isLoopy(emptySet())) {
            loopers.add(next.p)
            println("Loops with $next")
            fmMod.print(visited)
        }
        visited.add(pos)
        pos = next
    }
    return loopers.size
}