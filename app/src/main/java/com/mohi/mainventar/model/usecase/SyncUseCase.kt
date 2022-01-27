package com.mohi.mainventar.model.usecase

import com.mohi.mainventar.model.domain.Entity
import com.mohi.mainventar.model.repo.EntitiesRepository
import javax.inject.Inject

interface SyncUseCase {
    suspend operator fun invoke(): List<Entity>
}

class BaseSyncUseCase @Inject constructor(
    private val repo: EntitiesRepository
) : SyncUseCase {
    override suspend fun invoke(): List<Entity> {
        return repo.backOnline()
    }
}