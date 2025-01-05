import java.io.File

typealias Rules = MutableMap<Int, MutableSet<Int>>
// values must go before key
typealias RORules = Map<Int, Set<Int>>

data class Order(val rules: RORules, val updates: List<List<Int>>)

fun parseRule(str: String): Pair<Int, Int> {
    val (f, s) = str.split('|').map { it.toInt() }
    return Pair(f, s)
}

fun addRule(rules: Rules, rule: Pair<Int, Int>) {
    val r = rules.get(rule.second)
    if (r == null) {
        rules.set(rule.second, mutableSetOf<Int>(rule.first))
    } else {
        r.add(rule.first)
    }
}

fun evalUpdate(rules: RORules, update: List<Int>): Int {
    val mid = update[update.size / 2]
    update.forEachIndexed { i, u ->
        val behind = update.subList(i + 1, update.size).toSet()
        val before = rules.get(u)
        if (before == null) {
            return@forEachIndexed
        }
        if (!before.intersect(behind).isEmpty()) {
            return 0
        }
    }
    return mid
}

fun corrUpdate(
    rules: RORules,
	update: List<Int>,
    idx1: Int,
    idx2: Int): List<Int> {
    var corr = update.toMutableList()
    val tmp = corr[idx1]
    corr[idx1] = corr[idx2]
    corr[idx2] = tmp
    return corr
}

fun evalUpdate2(
    rules: RORules,
    update: List<Int>): Int {
    update.forEachIndexed { i, u ->
        val behind = update.subList(i + 1, update.size).toSet()
        val before = rules.get(u)
        if (before == null) {
            return@forEachIndexed
        }
        behind.forEachIndexed { j, bhnd ->
        	if (before.contains(bhnd)) {
                val corr = corrUpdate(rules, update, i, i + 1 + j)
                return evalUpdate2(rules, corr)
            }
        }
    }
    return update[update.size / 2]
}

fun parseInput(lines: List<String>): Order {
    var rules: Rules = mutableMapOf()
    var updates = mutableListOf<List<Int>>()
    for (l in lines) {
        if (l.contains('|')) {
            val p = parseRule(l)
            addRule(rules, p)
        } else if (l.contains(',')) {
            updates.add(l.split(',').map { it.toInt() })
        }
    }
    return Order(rules, updates)
} 

fun d05_1(f: String): Int {
    val lines = File(f).readLines()
    val order = parseInput(lines)
    return order.updates.map {
       	evalUpdate(order.rules, it)
       }.fold(0) { a, i -> a + i }
}

fun d05_2(f: String): Int {
    val lines = File(f).readLines()
    val order = parseInput(lines)
    val fails = order.updates.filter {
        evalUpdate(order.rules, it) == 0
    }
    val mids = fails.map {
        evalUpdate2(order.rules, it)
    }
   return mids.fold(0) { a, i -> a + i }
}


fun main() {
    try {
        println(d05_1("input"))
        println(d05_2("input"))
    } catch (ex: Exception) {
        println("error: ${ex.message}")
    }
}
