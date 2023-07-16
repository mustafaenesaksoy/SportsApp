package con.enesaksoy.sportsapp

import java.util.LinkedHashMap

class LRUCache(private val capacity: Int) {
    private val cache: LinkedHashMap<Int, Int> = object : LinkedHashMap<Int, Int>(capacity, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<Int, Int>?): Boolean {
            return size > capacity
        }
    }

    fun get(key: Int): Int {
        return cache.getOrDefault(key, -1)
    }

    fun put(key: Int, value: Int) {
        cache[key] = value
    }
}

fun main() {
    val cache = LRUCache(3)
    cache.put(1, 1)
    cache.put(2, 2)
    cache.put(3, 3)
    cache.put(4, 4)
    cache.put(4, 5)

    println(cache.get(1))
}