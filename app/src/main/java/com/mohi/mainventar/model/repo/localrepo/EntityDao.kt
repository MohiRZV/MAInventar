package com.mohi.mainventar.model.repo.localrepo

import androidx.room.*
import com.mohi.mainventar.model.domain.Entity

@Dao
interface EntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: Entity) : Long

    @Query("select * from entity")
    fun getAll(): List<Entity>

    @Query("select COUNT(*) from entity where id= :id")
    fun findById(id: Int): Int

    @Query("delete from entity")
    fun nuke()

}