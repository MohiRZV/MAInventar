package com.mohi.mainventar.model.repo.localrepo

import android.util.Log
import com.mohi.mainventar.model.domain.Entity
import com.mohi.mainventar.model.repo.localrepo.EntityDao
import javax.inject.Inject

class LocalEntitiesRepository @Inject constructor(
    private val entityDao: EntityDao
){

    fun getAll(): List<Entity> {
        return entityDao.getAll()
    }

    fun save(entity: Entity): Entity {
        entityDao.save(entity)
        Log.d("Mohi","Saved locally $entity")
        return entity
    }

    fun exists(id: Int): Boolean {
        return (entityDao.findById(id)>0)
    }

    fun nuke() {
        entityDao.nuke()
    }
}