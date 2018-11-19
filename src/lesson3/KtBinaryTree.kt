package lesson3

import java.util.*
import kotlin.NoSuchElementException

// Attention: comparable supported but comparator is not
class KtBinaryTree<T : Comparable<T>> : AbstractMutableSet<T>(), CheckableSortedSet<T> {

    private var root: Node<T>? = null

    override var size = 0
        private set

    private class Node<T>(val value: T) {

        var left: Node<T>? = null

        var right: Node<T>? = null
    }

    override fun add(element: T): Boolean {
        val closest = find(element)
        val comparison = if (closest == null) -1 else element.compareTo(closest.value)
        if (comparison == 0) {
            return false
        }
        val newNode = Node(element)
        when {
            closest == null -> root = newNode
            comparison < 0 -> {
                assert(closest.left == null)
                closest.left = newNode
            }
            else -> {
                assert(closest.right == null)
                closest.right = newNode
            }
        }
        size++
        return true
    }

    override fun checkInvariant(): Boolean =
            root?.let { checkInvariant(it) } ?: true

    private fun checkInvariant(node: Node<T>): Boolean {
        val left = node.left
        if (left != null && (left.value >= node.value || !checkInvariant(left))) return false
        val right = node.right
        return right == null || right.value > node.value && checkInvariant(right)
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     *
     *
     *
     *
     * оценка
     * h - размер дерева
     * Трудоемкость =   в среднем O(log h),  в худшем случае О(h)
     */
    override fun remove(element: T): Boolean {
        remove(element, root!!, null)
        size--
        return contains(element)
    }

    private fun remove(element: T, node: Node<T>?, parent: Node<T>?): Boolean {
        if (node == null) return false
        val nodeR = node.right
        val nodeL = node.left

        if (element > node.value)
            remove(element, nodeR, node)
        if (element < node.value)
            remove(element, nodeL, node)
        if (element.compareTo(node.value) == 0) {
            when {
                nodeL == null && nodeR == null -> replaces(parent, node, null)
                nodeL == null -> replaces(parent, node, nodeR)
                nodeR == null -> replaces(parent, node, nodeL)
                else -> {
                    val change = min(nodeR, node)
                    val f = Node(change.first.value)
                    if (f.value != nodeL.value) f.left = nodeL
                    else f.left = null
                    if (f.value != nodeR.value) f.right = nodeR
                    else {
                        if (nodeR.right != null) {
                            f.right = nodeR.right
                        } else {
                            f.right = null
                        }
                    }
                    replaces(parent, node, f)
                    if (change.first.right != null) {
                        replaces(change.second, change.first, change.first.right)
                    } else replaces(change.second, change.first, null)
                }
            }
        }
        return true
    }


    private fun min(node: Node<T>, parent: Node<T>): Pair<Node<T>, Node<T>> {
        return if (node.left == null) {
            Pair(node, parent)
        } else {
            min(node.left!!, node)
        }
    }

    private fun replaces(parent: Node<T>?, node: Node<T>, new: Node<T>?) {
        when {
            parent == null -> root = new
            parent.left != null && parent.left!!.value.compareTo(node.value) == 0 -> parent.left = new
            else -> parent.right = new
        }
    }

    override operator fun contains(element: T): Boolean {
        val closest = find(element)
        return closest != null && element.compareTo(closest.value) == 0
    }

    private fun find(value: T): Node<T>? =
            root?.let { find(it, value) }

    private fun find(start: Node<T>, value: T): Node<T> {
        val comparison = value.compareTo(start.value)
        return when {
            comparison == 0 -> start
            comparison < 0 -> start.left?.let { find(it, value) } ?: start
            else -> start.right?.let { find(it, value) } ?: start
        }
    }

    inner class BinaryTreeIterator : MutableIterator<T> {

        private var current: Node<T>? = null
        private val deque = ArrayDeque<Node<T>>()

        init {
            var node = root
            while (node != null) {
                deque.push(node)
                node = node.left
            }
        }

        /**
         * Поиск следующего элемента
         * Средняя
         *
         *
         *
         * оценка
         * h - размер дерева
         * ресурсоемкость = O(h) Трудоемкость = в среднем O(1), в худшем случае O(h)
         */
        private fun findNext(): Node<T>? {
            var node: Node<T>? = deque.pop()
            val res: Node<T> = node!!

            node = node.right
            while (node != null) {
                deque.push(node)
                node = node.left
            }
            return res
        }


        override fun hasNext(): Boolean = deque.peekFirst() != null

        override fun next(): T {
            if (!hasNext()) throw NoSuchElementException()
            current = findNext()
            return current!!.value

        }

        /**
         * Удаление следующего элемента
         * Сложная
         *
         * оценка
         * ресурсоемкость = O(1) Трудоемкость =  O(1)
         */
        override fun remove() {
            if (current == null)
                throw NoSuchElementException()
            remove(current!!.value)
        }
    }

    override fun iterator(): MutableIterator<T> = BinaryTreeIterator()

    override fun comparator(): Comparator<in T>? = null

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    override fun subSet(fromElement: T, toElement: T): SortedSet<T> {
        TODO()
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    override fun headSet(toElement: T): SortedSet<T> {
        TODO()
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    override fun tailSet(fromElement: T): SortedSet<T> {
        TODO()
    }

    override fun first(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.left != null) {
            current = current.left!!
        }
        return current.value
    }

    override fun last(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.right != null) {
            current = current.right!!
        }
        return current.value
    }
}
