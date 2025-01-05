import java.io.File

data class Eq(val res: Long, val ops: List<Int>) {
    fun print() {
        println("$res : $ops")
    }
    
    fun solve(): Long {
        var variants = mutableListOf<Long>(ops[0].toLong())
        for (op in ops.subList(1, ops.size)) {
            var nextVars = mutableListOf<Long>()
            variants.forEach {
                nextVars.add(it * op)
                nextVars.add(it + op)
            }
            variants = nextVars
        }
        return if (variants.contains(res)) {
            res
        } else {
            0L
        }
    }
    
    fun solve2(): Long {
        var variants = mutableListOf<Long>(ops[0].toLong())
        for (op in ops.subList(1, ops.size)) {
            var nextVars = mutableListOf<Long>()
            variants.forEach {
                nextVars.add(it * op)
                nextVars.add(it + op)
                nextVars.add((
                    	it.toString() +
                    	op.toString()
                	).toLong())
            }
            variants = nextVars
        }
        return if (variants.contains(res)) {
            res
        } else {
            0L
        }
    }
}

fun parseLine(line: String): Eq {
    val nums = line.split(' ')
    return Eq(
        nums[0].split(':')[0].toLong(),
        nums.subList(1, nums.size).map { it.toInt() }
    )
}

fun parseInput7(lines: List<String>): List<Eq> {
    return lines.map { parseLine(it) }
}

fun d07_1(f: String): Long {
    val eqs = parseInput7(File(f).readLines())
    //eqs.forEach { it.print() }
    return eqs.fold(0) { a, it -> a + it.solve() }
}

fun d07_2(f: String): Long {
    val eqs = parseInput7(File(f).readLines())
    //eqs.forEach { it.print() }
    return eqs.fold(0) { a, it -> a + it.solve2() }
}