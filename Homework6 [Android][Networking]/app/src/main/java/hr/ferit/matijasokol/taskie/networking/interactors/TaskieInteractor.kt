package hr.ferit.matijasokol.taskie.networking.interactors

import hr.ferit.matijasokol.taskie.model.BackendTask
import hr.ferit.matijasokol.taskie.model.request.AddTaskRequest
import hr.ferit.matijasokol.taskie.model.request.EditTaskRequest
import hr.ferit.matijasokol.taskie.model.request.UserDataRequest
import hr.ferit.matijasokol.taskie.model.response.*
import retrofit2.Callback

interface TaskieInteractor {

    fun getTasks(taskieResponseCallback: Callback<GetTasksResponse>)

    fun getTaskById(id: String, taskieResponseCallback: Callback<BackendTask>)

    fun register(request: UserDataRequest, registerCallback: Callback<RegisterResponse>)

    fun login(request: UserDataRequest, loginCallback: Callback<LoginResponse>)

    fun save(request: AddTaskRequest, saveCallback: Callback<BackendTask>)

    fun deleteTask(id: String, deleteTaskCallback: Callback<DeleteTaskResponse>)

    fun edit(request: EditTaskRequest, editCallback: Callback<EditTaskResponse>)
}