package com.ecwid.warehouse.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Long,
    val name: String,
    //val avatar_url: ByteArray

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray? = null,
    val coast: Double
)