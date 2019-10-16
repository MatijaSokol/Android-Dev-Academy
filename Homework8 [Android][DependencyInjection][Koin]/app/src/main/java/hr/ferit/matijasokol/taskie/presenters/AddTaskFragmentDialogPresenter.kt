package hr.ferit.matijasokol.taskie.presenters

import hr.ferit.matijasokol.taskie.common.RESPONSE_OK
import hr.ferit.matijasokol.taskie.model.BackendTask
import hr.ferit.matijasokol.taskie.model.request.AddTaskRequest
import hr.ferit.matijasokol.taskie.networking.interactors.TaskieInteractor
import hr.ferit.matijasokol.taskie.persistence.repository.TaskRepository
import hr.ferit.matijasokol.taskie.ui.fragments.addTaskFragmentDialog.AddTaskFragmentDialogContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTaskFragmentDialogPresenter(private val repository: TaskRepository, private val interactor: TaskieInteractor) : AddTaskFragmentDialogContract.Presenter{

    private lateinit var view: AddTaskFragmentDialogContract.View

    override fun setView(view: AddTaskFragmentDialogContract.View) {
        this.view = view
    }

    override fun saveTask(title: String, content: String, priority: Int) {
        interactor.save(AddTaskRequest(title, content, priority), addTaskCallback())
    }

    private fun addTaskCallback(): Callback<BackendTask> = object : Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>?, t: Throwable?) {
            view.onAddTaskResponseFailure()
        }

        override fun onResponse(call: Call<BackendTask>?, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponse(response.body())
                    else -> handleSomethingWentWrong()
                }
            }
            else view.onAddTaskResponseFailureWithCode(response.code())
        }
    }

    private fun handleOkResponse(task: BackendTask?){
        repository.addTask(task!!)
        task.run { view.onAddTaskResponseSuccess(this) }
    }

    private fun handleSomethingWentWrong(){
        view.onAddTaskResponseWrong()
    }
}