package com.example.ejercicio2.data.remote.model

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DragonBallAPI {
        @GET("api/characters")
        suspend fun getCharacters(
            @Query("limit") limit: Int = 58
        ): CharacterDragon

        @GET("api/characters/{id}")
        suspend fun getCharacterDetail(
            @Path("id") id: Int
        ): DetailDragon
    }
