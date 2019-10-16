package hr.ferit.matijasokol.taskie.presenters

import hr.ferit.matijasokol.taskie.common.RESPONSE_OK
import hr.ferit.matijasokol.taskie.model.request.EditTaskRequest
import hr.ferit.matijasokol.taskie.model.response.EditTaskResponse
import hr.ferit.matijasokol.taskie.networking.interactors.TaskieInteractor
import hr.ferit.matijasokol.taskie.persistence.repository.TaskRepository
import hr.ferit.matijasokol.taskie.ui.fragments.changeTaskFragment.ChangeTaskFragmentDialogContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeTaskFragmentDialogPresenter(private val repository: TaskRepository, private val interactor: TaskieInteractor) : ChangeTaskFragmentDialogContract.Presenter{

    private lateinit var view: ChangeTaskFragmentDialogContract.View

    override fun setView(view: ChangeTaskFragmentDialogContract.View) {
        this.view = view
    }

    override fun editTaskInNet(taskId: String, title: String, content: String, priority: Int) {
        interactor.edit(EditTaskRequest(taskId, title, content, priority), editTaskCallback())
    }

    private fun editTaskCallback(): Callback<EditTaskResponse> = object : Callback<EditTaskResponse> {
        override fun onFailure(call: Call<EditTaskResponse>, t: Throwable) {
            view.onEditTaskResponseFailure()
        }

        override fun onResponse(call: Call<EditTaskResponse>, response: Response<EditTaskResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body().let { handleOkResponse(it) }
                    else -> handleSomethingWentWrong()
                }
            }
            else view.onEditTaskResponseFailureWithCode(response.code())
        }

    }

    private fun handleOkResponse(editTaskResponse: EditTaskResponse?){
        view.onEditTaskResponseSuccess()
    }

    override fun editTaskInRepository(taskId: String, title: String, content: String, priority: Int) {
        repository.updateTask(taskId, title, title, priority)
    }

    private fun handleSomethingWentWrong(){
        view.onEditTaskResponseWrong()
    }
}