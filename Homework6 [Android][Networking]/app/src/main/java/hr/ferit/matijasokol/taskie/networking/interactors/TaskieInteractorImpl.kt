package hr.ferit.matijasokol.taskie.networking.interactors

import hr.ferit.matijasokol.taskie.model.BackendTask
import hr.ferit.matijasokol.taskie.model.request.AddTaskRequest
import hr.ferit.matijasokol.taskie.model.request.EditTaskRequest
import hr.ferit.matijasokol.taskie.model.request.UserDataRequest
import hr.ferit.matijasokol.taskie.model.response.*
import hr.ferit.matijasokol.taskie.networking.TaskieApiService
import retrofit2.Callback

class TaskieInteractorImpl(private val apiService: TaskieApiService) : TaskieInteractor {

    override fun getTasks(taskieResponseCallback: Callback<GetTasksResponse>) {
        apiService.getTasks().enqueue(taskieResponseCallback)
    }

    override fun getTaskById(id: String, taskieResponseCallback: Callback<BackendTask>) {
        apiService.getTaskById(id).enqueue(taskieResponseCallback)
    }

    override fun register(request: UserDataRequest, registerCallback: Callback<RegisterResponse>) {
        apiService.register(request).enqueue(registerCallback)
    }

    override fun login(request: UserDataRequest, loginCallback: Callback<LoginResponse>) {
        apiService.login(request).enqueue(loginCallback)
    }

    override fun save(request: AddTaskRequest, saveCallback: Callback<BackendTask>) {
        apiService.save(request).enqueue(saveCallback)
    }

    override fun deleteTask(id: String, deleteTaskCallback: Callback<DeleteTaskResponse>) {
        apiService.delete(id).enqueue(deleteTaskCallback)
    }

    override fun edit(request: EditTaskRequest, editCallback: Callback<EditTaskResponse>) {
        apiService.edit(request).enqueue(editCallback)
    }
}