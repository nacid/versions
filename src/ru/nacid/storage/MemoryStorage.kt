package ru.nacid.storage

class MemoryStorage : Storage() {
    override fun build(): MutableMap<Int, MutableMap<Int, Int>> = HashMap()
    override fun flush() {}
}