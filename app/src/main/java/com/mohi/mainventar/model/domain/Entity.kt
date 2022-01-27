package com.mohi.mainventar.model.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entity")
data class Entity(
    @PrimaryKey
    var id: Int = 0,
    @ColumnInfo
    val nume: String = "",
    @ColumnInfo
    val tip: String = "",
    @ColumnInfo
    val cantitate: Int = 0,
    @ColumnInfo
    val pret: Int = 0,
    @ColumnInfo
    val discount: Int = 0,
    @ColumnInfo
    val status: Boolean = false,
    @ColumnInfo
    var isLocal: Boolean = false
)
