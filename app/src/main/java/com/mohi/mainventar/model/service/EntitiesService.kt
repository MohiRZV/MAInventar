package com.mohi.mainventar.model.service

import com.mohi.mainventar.model.domain.Entity
import retrofit2.http.*

interface EntitiesService {

    @GET("/products")
    suspend fun getAll(): List<Entity>

    @POST("/product")
    suspend fun add(@Body entity: Entity): Entity
}