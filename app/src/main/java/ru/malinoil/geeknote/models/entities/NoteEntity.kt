package ru.malinoil.geeknote.models.entities

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class NoteEntity(
    val id: String,
    var name: String? = null,
    var description: String? = null,
    var createDate: Long? = null,
    var deadline: Long? = null
): Parcelable {

    constructor(): this("") {}

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeValue(createDate)
        parcel.writeValue(deadline)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteEntity> {
        override fun createFromParcel(parcel: Parcel): NoteEntity {
            return NoteEntity(parcel)
        }

        override fun newArray(size: Int): Array<NoteEntity?> {
            return arrayOfNulls(size)
        }

        fun generateId(): String {
            return UUID.randomUUID().toString()
        }
    }
}