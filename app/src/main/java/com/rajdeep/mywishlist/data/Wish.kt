package com.rajdeep.mywishlist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "wish-title")
    val title: String = "",
    @ColumnInfo(name = "wish-desc")
    val description: String =""
)

object DummyWish{
    val wishList = listOf(
        Wish(title = "Google Pixel 8", description = "An android Smartphone"),
        Wish(title = "Oculus Quest 3", description = "A VR headset"),
        Wish(title = "Bean Bag", description = "A comfy bean bag to substitute for a chair"),
        Wish(title = "Dune", description = "A sci-fi novel")
    )
}