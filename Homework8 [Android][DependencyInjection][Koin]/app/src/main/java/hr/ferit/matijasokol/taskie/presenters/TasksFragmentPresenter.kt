package hr.ferit.matijasokol.taskie.presenters

import hr.ferit.matijasokol.taskie.ui.fragments.tasksFragment.TasksFragmentContract
import hr.ferit.matijasokol.taskie.common.*
import hr.ferit.matijasokol.taskie.model.BackendTask
import hr.ferit.matijasokol.taskie.model.response.DeleteTaskResponse
import hr.ferit.matijasokol.taskie.model.response.GetTasksResponse
import hr.ferit.matijasokol.taskie.networking.interactors.TaskieInteractor
import hr.ferit.matijasokol.taskie.persistence.repository.TaskRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TasksFragmentPresenter (private val repository: TaskRepository, private val interactor: TaskieInteractor) :
    TasksFragmentContract.Presenter {

    private lateinit var view: TasksFragmentContract.View

    override fun setView(view: TasksFragmentContract.View) {
        this.view = view
    }

    override fun getTasks() {
        interactor.getTasks(getTaskieCallback())
    }

    private fun getTaskieCallback(): Callback<GetTasksResponse> = object : Callback<GetTasksResponse> {
        override fun onFailure(call: Call<GetTasksResponse>?, t: Throwable?) {
            view.getTasksReponseFailure()
        }

        override fun onResponse(call: Call<GetTasksResponse>?, response: Response<GetTasksResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponseGetTaskie(response)
                    else -> handleSomethingWentWrongGetTaskie()
                }
            }
            else view.getTasksResponseFailureWithCode(response.code())
        }
    }

    private fun handleOkResponseGetTaskie(response: Response<GetTasksResponse>) {
        view.getTasksResponseSucces(response.body()?.notes)
    }

    private fun handleSomethingWentWrongGetTaskie(){
        view.getTasksResponseWrong()
    }

    override fun deleteTask(taskId: String) {
        interactor.deleteTask(taskId, deleteTaskCallback(taskId))
    }

    private fun deleteTaskCallback(taskId: String): Callback<DeleteTaskResponse> = object  : Callback<DeleteTaskResponse>{
        override fun onFailure(call: Call<DeleteTaskResponse>, t: Throwable) {
            view.deleteTaskResponseFailure()
        }

        override fun onResponse(call: Call<DeleteTaskResponse>, response: Response<DeleteTaskResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponseDeleteTask(taskId)
                    else -> handleSomethingWentWrongDeleteTask()
                }
            }
            else view.deleteTaskResponseFailureWithCode(response.code())
        }
    }

    private fun handleOkResponseDeleteTask(id: String){
        view.deleteTaskResponseSuccess(id)
    }

    override fun deleteTaskFromRepository(task: BackendTask) {
        repository.deleteTask(task)
    }

    private fun handleSomethingWentWrongDeleteTask(){
        view.deleteTaskResponseWrong()
    }

    override fun deleteAllTasksFromRepository() {
        repository.deleteAll()
    }

    override fun getAllTasksFromRepository(): List<BackendTask> {
        return repository.getAllTasks()
    }
}