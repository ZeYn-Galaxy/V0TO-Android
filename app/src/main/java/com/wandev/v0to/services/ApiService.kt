package com.wandev.v0to.services

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.navigation.NavHostController
import com.wandev.v0to.models.Camera
import com.wandev.v0to.models.CameraDetail
import com.wandev.v0to.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object ApiService {
    const val http = "http://10.0.2.2:5000/api"
    var key = ""

    fun getCategory() : List<Category> {
        val connection = URL("${http}/category").openConnection() as HttpURLConnection

        return try {
            val response = connection.inputStream.bufferedReader().use { it.readText() }

            JSONArray(response).let {array ->
                List(array.length()) {
                    array.getJSONObject(it).let {
                        Category(
                            it.getInt("id"),
                            it.getString("name")
                        )
                    }
                }
            }
        } catch (e : Exception) {
            e.printStackTrace()
            emptyList<Category>()
        } finally {
            connection.disconnect()
        }
    }

    suspend fun getCameraDetail(id : String, context: Context) : CameraDetail? {
        val connection = URL("${http}/camera/${id}").openConnection() as HttpURLConnection

        return try {

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Data not found", Toast.LENGTH_SHORT).show()
                }
                return null
            }

            val response = connection.inputStream.bufferedReader().use { it.readText() }

            JSONObject(response).let {
                val getImage = URL(" http://10.0.2.2:5000/images/${it.getString("photo")}").openStream()
                var img = BitmapFactory.decodeStream(getImage)

                CameraDetail(
                    it.getInt("id"),
                    it.getString("name"),
                    it.getString("sellerShop"),
                    it.getString("sensor"),
                    it.getString("resolution"),
                    it.getString("autoFocusSystem"),
                    it.getString("isoRange"),
                    it.getString("shuterSpeedRange"),
                    it.getString("dimension"),
                    it.getInt("weight"),
                    it.getBoolean("wiFi"),
                    it.getBoolean("touchScreen"),
                    it.getBoolean("flash"),
                    it.getBoolean("bluetooth"),
                    it.getInt("price"),
                    img
                )
            }
        } catch (e : Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getCamera(categoryId : Int?, search : String?) : List<Camera> {
        var normal = "${http}/camera"

        if (categoryId != null) {
            normal += "?categoryID=$categoryId"
        }

        if (search != null) {
            normal += "?search=$search"
        }

        val connection = URL(normal).openConnection() as HttpURLConnection

        return try {
            val response = connection.inputStream.bufferedReader().use { it.readText() }

            JSONArray(response).let { array ->
                List(array.length()) {
                    array.getJSONObject(it).let {
                        val getImage = URL(" http://10.0.2.2:5000/images/${it.getString("photo")}").openStream()
                        var img = BitmapFactory.decodeStream(getImage)

                        Camera(
                            it.getString("id"),
                            it.getString("name"),
                            it.getString("resolution"),
                            it.getInt("price"),
                            img
                        )
                    }
                }
            }
        } catch (e : Exception) {
            e.printStackTrace()
            emptyList<Camera>()
        } finally {
            connection.disconnect()
        }

    }

    suspend fun login(context: Context, email : String, password : String, navController : NavHostController) {
        val connection = URL("${http}/auth").openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            val json = """
                {
                "email": "$email",
                "password": "$password"
                }
            """.trimIndent()

            connection.outputStream.use { out ->
                out.write(json.toByteArray(Charsets.UTF_8))
                out.flush()
            }

            val response = if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().use { it.readText() }
            } else {
                connection.errorStream.bufferedReader().use { it.readText() }
            }

            withContext(Dispatchers.Main) {
                if (response == "User not found") {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
                    return@withContext
                }

                key = "Bearer $response"
                navController.navigate("home") {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        } catch (e : Exception) {
            e.printStackTrace()
        } finally {
            connection.disconnect()
        }

    }
}