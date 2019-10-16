package hr.ferit.matijasokol.taskie.networking

import hr.ferit.matijasokol.taskie.model.BackendTask
import hr.ferit.matijasokol.taskie.model.request.AddTaskRequest
import hr.ferit.matijasokol.taskie.model.request.EditTaskRequest
import hr.ferit.matijasokol.taskie.model.request.UserDataRequest
import hr.ferit.matijasokol.taskie.model.response.*
import retrofit2.Call
import retrofit2.http.*

interface TaskieApiService {

    @POST("/api/register")
    fun register(@Body userData: UserDataRequest): Call<RegisterResponse>

    @POST("/api/login")
    fun login(@Body userData: UserDataRequest): Call<LoginResponse>

    @GET("/api/note")
    fun getTasks(): Call<GetTasksResponse>

    @GET("/api/note/{id}")
    fun getTaskById(@Path("id") id: String): Call<BackendTask>

    @POST("/api/note")
    fun save(@Body taskData: AddTaskRequest): Call<BackendTask>

    @POST("/api/note/delete")
    fun delete(@Query("id") id: String): Call<DeleteTaskResponse>

    @POST("/api/note/edit")
    fun edit(@Body taskData: EditTaskRequest) : Call<EditTaskResponse>
}