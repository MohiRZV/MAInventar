package com.mohi.mainventar.model.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohi.mainventar.model.domain.Entity
import com.mohi.mainventar.model.usecase.AddEntityUseCase
import com.mohi.mainventar.model.usecase.GetEntitiesUseCase
import com.mohi.mainventar.model.usecase.SyncUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class EntitiesViewModel @Inject constructor(
    val getAllUseCase: GetEntitiesUseCase,
    val addUseCase: AddEntityUseCase,
    val syncUseCase: SyncUseCase
) : ViewModel() {

    val loading = mutableStateOf(false)

    private val _listOfEntities: MutableState<List<Entity>> = mutableStateOf(emptyList())
    val listOfEntities: State<List<Entity>> = _listOfEntities

    private val _listOfTopEntities: MutableState<List<Entity>> = mutableStateOf(emptyList())
    val listOfTopEntities: State<List<Entity>> get() {
        getTop()
        return _listOfTopEntities
    }

    init {
        viewModelScope.launch {
            loading.value = true
            val entityList = getAllUseCase()
            _listOfEntities.value = entityList
            delay(1000)
            loading.value = false
        }
    }

    fun add(nume: String, tip: String, cantitate: Int, pret: Int, discount: Int, status: Boolean = false) {
        viewModelScope.launch {
            loading.value = true
            try {
                val entity = Entity(
                    nume = nume,
                    tip = tip,
                    cantitate = cantitate,
                    pret = pret,
                    discount = discount,
                    status = status
                )
                Log.d("Mohi","Adding $entity")
                addUseCase(
                    entity = entity
                )
                loading.value = false
            }catch (ex: Exception) {
                Log.d("Mohi", ex.message?:"")
            }
        }
    }

    private fun getTop() {
        viewModelScope.launch {
            loading.value = true
            Log.d("Mohi","Generating top cheapest")
            val entityList = getAllUseCase()
            delay(1000)
            _listOfTopEntities.value = entityList.sortedByDescending { it.pret }.take(15)
            loading.value = false
        }
    }

    fun backOnline() {

        viewModelScope.launch {
            loading.value = true
            Log.d("Mohi","Synchronizing server with local data")
            try {
                syncUseCase()
                delay(1000)
                loading.value = false
            } catch (ex: Exception) {
                Log.d("Mohi", "Something went wrong while sync with server")
            }

        }
    }
}