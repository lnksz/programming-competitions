import java.io.File

fun fileLines(f: String): List<String> {
    return File(f).readLines()
}

fun isSafeLevel(dir: Int, a: Int, b: Int): Boolean {
    val d = b - a
    if (d * dir < 0) {
        return false
    }
    when (d * d) {
        in 1..9 -> return true
        else -> return false
    }
}

fun isSafeLine(line: String): Boolean {
    val nums = line.split(" ").map{it.toInt()}
    val dir = nums.last() - nums.first()
    if (dir == 0) {
        return false
    }
    for (w in nums.windowed(2)) {
        if (!isSafeLevel(dir, w[0], w[1])) {
            return false
        }
    }
    return true
}

fun d02_1(f: String): Int {
    val lines = fileLines(f)
    return lines.count { isSafeLine(it) }
}

fun <T> generateVariants(list: List<T>): List<List<T>> =
    list.indices.map { index -> list.slice(0 until index) + list.slice(index + 1 until list.size) }

fun isSafeishLine(line: String): Boolean {
    if (isSafeLine(line)) {
        return true
    }
    val nums = line.split(" ").map{it.toInt()}
    val variants = generateVariants(nums)
    for (v in variants) {
        if (isSafeLine(v.joinToString(separator = " "))) {
            return true
        }
    }
    return false
}

fun d02_2(f: String): Int {
    val lines = fileLines(f)
    return lines.count { isSafeishLine(it) }
}


println(d02_1("input"))
println(d02_2("input"))

