@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File
import java.io.IOException
import java.lang.Integer.parseInt
import java.util.*

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
 * каждый на отдельной строке. Пример:
 *
 * 13:15:19
 * 07:26:57
 * 10:00:03
 * 19:56:14
 * 13:15:19
 * 00:40:31
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 00:40:31
 * 07:26:57
 * 10:00:03
 * 13:15:19
 * 13:15:19
 * 19:56:14
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 *
 * оценка
 * ресурсоемкость = O(n)  производительность = O(n log n)
 */
fun sortTimes(inputName: String, outputName: String) {
    if (File(inputName).readLines().isEmpty()) throw IOException()
    val list = ArrayList<Int>()
    File(inputName).forEachLine {
        val part = it.split(":").toTypedArray()
        list.add((parseInt(part[0]) * (60 * 60) + (parseInt(part[1])) * 60) +
                (parseInt(part[2])))
        if (!it.matches(Regex("""(([0-2][0-9]):[0-5][0-9]:[0-5][0-9])""")))
            throw IOException()
    }
    val toInt = list.toIntArray()
    quickSort(toInt)
    val bf = File(outputName).bufferedWriter()
    for (o in toInt) {
        val hour = o / (60 * 60)
        val minute = o / 60 % 60
        val second = o % 3600 % 60
        bf.write(String.format("%02d:%02d:%02d", hour, minute, second))
        bf.newLine()
    }
    bf.close()
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 *
 * оценка
 * ресурсоемкость = O(1) трудоемкость =  O(n log n)
 */
fun sortTemperatures(inputName: String, outputName: String) {
    val list = ArrayList<Int>()
    File(inputName).forEachLine {
        if (it.toDouble() < -273.0 || it.toDouble() > 500.0) throw IOException()
        val temp = (it.toDouble() * 10).toInt()
        list.add(temp)
    }
    val toInt = list.toIntArray()
    quickSort(toInt)
    val bf = File(outputName).bufferedWriter()
    for (o in toInt) {
        val f = o.toDouble() / 10.0
        bf.write(f.toString())
        bf.newLine()
    }

    bf.close()

}

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 *
 *
 * оценка
 * ресурсоемкость = O(n) трудоемкость =  O(n)
 *
 */
fun sortSequence(inputName: String, outputName: String) {
    val arrList = ArrayList<Int>()
    val res = HashMap<Int, Int>()
    var max = 0
    var c = 0
    File(inputName).forEachLine {
        arrList.add(it.toInt())
    }
    for (i in arrList) {
        if (res.containsKey(i)) {
            res[i] = res[i]!! + 1
        } else {
            res[i] = 1
        }
    }
    for (i in res.keys) {
        if (i < c && res.getValue(i) == max || res.getValue(i) > max) {
            max = res.getValue(i)
            c = i
        }
    }
    val bf = File(outputName).bufferedWriter()
    for (l in arrList) {
        if (l != c) {
            bf.write("$l")
            bf.newLine()
        }
    }
    for (i in 1..max) {
        bf.write("$c")
        bf.newLine()
    }
    bf.close()
}



/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 *
 *
 * оценка
 * ресурсоемкость = 0(1) трудоемкость =  O(n log n)
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    for (i in 0 until first.size) {
        second[i] = first[i]
    }
    second.sort()
}

