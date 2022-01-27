package com.mohi.mainventar.model.repo

import android.util.Log
import com.mohi.mainventar.MainActivity
import com.mohi.mainventar.model.domain.Entity
import com.mohi.mainventar.model.repo.localrepo.LocalDatabase
import com.mohi.mainventar.model.repo.localrepo.LocalEntitiesRepository
import com.mohi.mainventar.model.service.EntitiesService
import com.mohi.mainventar.utils.InternetStatus
import com.mohi.mainventar.utils.InternetStatusLive
import retrofit2.HttpException
import java.net.ConnectException
import javax.inject.Inject

interface EntitiesRepository {
    suspend fun getAll(): List<Entity>
    suspend fun add(entity: Entity): Entity
    suspend fun backOnline(): List<Entity>
}

class BaseEntitiesRepository @Inject constructor(
    private val service: EntitiesService
) : EntitiesRepository {
    private var retrieved = false
    private val localRepo = LocalEntitiesRepository(LocalDatabase.getDatabase(MainActivity.bcontext).entityDao())
    override suspend fun getAll(): List<Entity> {
        return localRepo.getAll()
    }

    override suspend fun add(entity: Entity): Entity {
        return if(InternetStatusLive.status.value?.equals(InternetStatus.OFFLINE) == true) {
            entity.isLocal = true
            Log.d("Mohi","Saving $entity locally")
            localRepo.save(entity)
        } else {
            Log.d("Mohi","Saving $entity")
            val saved = service.add(entity)
            entity.id = saved.id
            localRepo.save(entity)
            saved
        }
    }

    override suspend fun backOnline(): List<Entity> {
        localRepo.getAll().forEach { entity ->
            if(entity.isLocal) {
                try {
                    service.add(entity)
                } catch (ex: HttpException) {
                    //not relevant here
                }
            }
        }
        var entities = listOf<Entity>()
        try {
            entities = service.getAll()
            entities.forEach {
                if(!localRepo.exists(it.id))
                    localRepo.save(it)
            }
            retrieved = true
        } catch (ex: ConnectException) {
            Log.d("ConnectException", ex.message.toString())
        }
        return entities
    }
}