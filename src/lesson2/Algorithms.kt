@file:Suppress("UNUSED_PARAMETER")

package lesson2

import java.io.File
import java.io.IOException

/**
 * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
 * Простая
 *
 * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
 * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
 *
 * 201
 * 196
 * 190
 * 198
 * 187
 * 194
 * 193
 * 185
 *
 * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
 * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
 * Вернуть пару из двух моментов.
 * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
 * Например, для приведённого выше файла результат должен быть Pair(3, 4)
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 *
 * оценка
 * ресурсоемкость = O(n)   трудоемкость = O(n)
 */
fun optimizeBuyAndSell(inputName: String): Pair<Int, Int> {
    val list = ArrayList<Int>()
    var res = Pair(1, 1)
    var j = 0
    var t = 0
    var max = 0
    File(inputName).forEachLine {
        list.add(it.toInt())
        if (it.toInt() < 0) throw IOException()
    }
    val margin = IntArray(list.size - 1)
    for (i in 0 until list.size - 1) {
        margin[i] = list[i + 1] - list[i]
    }
    for (i in 0 until margin.size) {
        t += margin[i]
        if (t < 0) {
            t = 0
            j = i
        }
        if (t >= max) {
            max = t
            res = Pair(j + 2, i + 2)
        }

    }
    return res
}

/**
 * Задача Иосифа Флафия.
 * Простая
 *
 * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
 *
 * 1 2 3
 * 8   4
 * 7 6 5
 *
 * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
 * Человек, на котором остановился счёт, выбывает.
 *
 * 1 2 3
 * 8   4
 * 7 6 х
 *
 * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
 * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
 *
 * 1 х 3
 * 8   4
 * 7 6 Х
 *
 * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
 *
 * 1 Х 3
 * х   4
 * 7 6 Х
 *
 * 1 Х 3
 * Х   4
 * х 6 Х
 *
 * х Х 3
 * Х   4
 * Х 6 Х
 *
 * Х Х 3
 * Х   х
 * Х 6 Х
 *
 * Х Х 3
 * Х   Х
 * Х х Х
 *
 *
 * оценка
 *
 * ресурсоемкость = O(1)  трудоемкость = O(n
 */
fun josephTask(menNumber: Int, choiceInterval: Int): Int {
    var res = 0
    if (menNumber == 1)
        return res + 1
    for (i in 0 until menNumber)
        res = (res + choiceInterval) % (i + 1)
    return res + 1
}

/**
 * Наибольшая общая подстрока.
 * Средняя
 *
 * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
 * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
 * Если общих подстрок нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 * Если имеется несколько самых длинных общих подстрок одной длины,
 * вернуть ту из них, которая встречается раньше в строке first.
 *
 *
 * ресурсоемкость = O(n*m)     трудоемкость = O(n*m)
 */
fun longestCommonSubstring(first: String, second: String): String {
    val arr = Array(first.length) { IntArray(second.length) }
    var t = 0
    var length = 0
    for (i in 0 until first.length) {
        for (j in 0 until second.length) {
            if (first[i] == second[j]) {
                if (i > 0 && j > 0) {
                    arr[i][j] = arr[i - 1][j - 1] + 1
                } else {
                    arr[i][j] = 1
                }
                if (arr[i][j] > length) {
                    length = arr[i][j]
                    t = i
                }
            }
        }
    }
    if (length == 0) return ""
    return first.substring(t - length + 1, t + 1)
}

/**
 * Число простых чисел в интервале
 * Простая
 *
 * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
 * Если limit <= 1, вернуть результат 0.
 *
 * Справка: простым считается число, которое делится нацело только на 1 и на себя.
 * Единица простым числом не считается.
 *
 *
 * ресурсоемкость = O(n)   трудоемкость = O(n)
 */
fun calcPrimesNumber(limit: Int): Int {
    var counter = 0
    if (limit <= 1) return 0
    for (i in 1..limit) {
        if (primeNumber(i)) counter++
    }
    return counter
}

fun primeNumber(n: Int): Boolean {
    var i = 2
    if (n <= 1) return false
    while (i * i <= n) {
        if (n % i == 0)
            return false
        i++
    }
    return true
}

/**
 * Балда
 * Сложная
 *
 * В файле с именем inputName задана матрица из букв в следующем формате
 * (отдельные буквы в ряду разделены пробелами):
 *
 * И Т Ы Н
 * К Р А Н
 * А К В А
 *
 * В аргументе words содержится множество слов для поиска, например,
 * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
 *
 * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
 * и вернуть множество найденных слов. В данном случае:
 * ТРАВА, КРАН, АКВА, НАРТЫ
 *
 * И т Ы Н     И т ы Н
 * К р а Н     К р а н
 * А К в а     А К В А
 *
 * Все слова и буквы -- русские или английские, прописные.
 * В файле буквы разделены пробелами, строки -- переносами строк.
 * Остальные символы ни в файле, ни в словах не допускаются.
 */
fun baldaSearcher(inputName: String, words: Set<String>): Set<String> {
    TODO()
}