package hu.bme.aut.android.fruitcatalog.network

import hu.bme.aut.android.fruitcatalog.model.Fruit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FruitService {
    @GET("fruit/all")
    fun getAllFruits(): Call<List<Fruit>>

    @GET("fruit/{id}}")
    fun getFruitById(@Path("id") id: Int): Call<Fruit>
}