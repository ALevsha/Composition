package com.procourse.composition.domain.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/* Реализацию Parcelize  также можно не создавать, если использовать специальную аннотацию.
* В build.gradle добавляем плагин kotlin-parcelize и помечаем класс, реализующий интерфейс
* Parcelable аннотацией */

@Parcelize // при выборе аннотации необходимо использовать аннотацию без пакета 'Android'
data class GameSettings(
    val maxSumValue: Int,// максимальное количество суммы
    val minCountOfRightAnswers: Int,// минимальное количество правильных ответов
    val minPercentsOfRightAnswers: Int,// минимальный процент правильных ответов
    val gameTimeInSeconds: Int// время игры в секунду
): Parcelable /*{
    *//*
    * В отличии от интерфейса Serializable, Parcelable не генерится автоматически.
    * При его использовании необходимо подстроить реализацию интерфейса под каждый класс
    * индивидуально, что как раз и дает ощутимый прирост производительности в сравнении
    * с тем же Serializable. Serializable является интерфейсом - маркером, т.е его методы не нужно
    * переопределять. Android Studio может генерить реализацию Parcelable самостоятельно.*//*
    constructor(parcel: Parcel) : this(
        *//*
        * Конструктор объекта Parcel, в нем считываются записанные поля также определенными
        * методами (отдельно для Int, String и т.п. Здесь также важно соблюдать порядок записи
        * *//*
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        *//* Здесь происходит запись полей класса в объект Parcel. Важен порядок запись.
        * Он должен соответствовать порядку полей в конструкторе. Для записи используются методы
        * соответвтвующие типу данных поля*//*
        parcel.writeInt(maxSumValue)
        parcel.writeInt(minCountOfRightAnswers)
        parcel.writeInt(minPercentsOfRightAnswers)
        parcel.writeInt(gameTimeInSeconds)
    }

    override fun describeContents(): Int {
        *//*
        * Можно не обращать внимание. Практически всегда возвращается 0*//*
        return 0
    }


    companion object CREATOR : Parcelable.Creator<GameSettings> {
        *//* И, наконец, для использования объекта Parcel необходимо переопределить объект CREATOR
        * в котором:*//*
        override fun createFromParcel(parcel: Parcel): GameSettings {
            *//*
            * вызывает конструктор с объектом Parcel
            * *//*
            return GameSettings(parcel)
        }

        override fun newArray(size: Int): Array<GameSettings?> {
            *//*
            * Описывает создание массива объектов GameSettings
            * *//*
            return arrayOfNulls(size)
        }
    }
}*/
