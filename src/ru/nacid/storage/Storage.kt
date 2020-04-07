package ru.nacid.storage

import ru.nacid.Version

typealias StorageMap = MutableMap<Int, MutableMap<Int, Int>>

abstract class Storage {
    lateinit var map: StorageMap
        private set

    init {
        rebuild()
    }

    fun next(version: Version): Version {
        val major = map.getOrPut(version.major) { HashMap() }
        val minor = version.minor.coerceAtLeast(major.getOrPut(version.middle) { 0 }) + 1

        major[version.middle] = minor
        flush()

        return version.copy(minor = minor)
    }

    fun rebuild(): Unit {
        map = build()
        flush()
    }

    fun rebuild(from: Storage) {
        map = HashMap(from.map)
        flush()
    }

    protected abstract fun build(): StorageMap
    protected abstract fun flush(): Unit
}