package hr.ferit.matijasokol.taskie.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.common.RESPONSE_OK
import hr.ferit.matijasokol.taskie.common.displayMessageFromCode
import hr.ferit.matijasokol.taskie.common.displayToast
import hr.ferit.matijasokol.taskie.common.priorityFactory
import hr.ferit.matijasokol.taskie.db.repository.TaskRepository
import hr.ferit.matijasokol.taskie.db.repository.TaskRoomRepository
import hr.ferit.matijasokol.taskie.model.BackendTask
import hr.ferit.matijasokol.taskie.model.request.EditTaskRequest
import hr.ferit.matijasokol.taskie.model.response.EditTaskResponse
import hr.ferit.matijasokol.taskie.networking.BackendFactory
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeTaskFragmentDialog : DialogFragment(){

    private var taskChangedListener: TaskChangedListener? = null
    private val repository: TaskRepository = TaskRoomRepository()
    private val interactor = BackendFactory.getTaskieInteractor()
    private var priority: Int = 0
    lateinit var task: BackendTask

    interface TaskChangedListener{
        fun onTaskChanged(task: BackendTask)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    fun setTaskChangedListener(listener: TaskChangedListener){
        taskChangedListener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_new_task, container)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
    }

    private fun initUi(){
        newTaskHeading.text = getString(R.string.edit_task)
        newTaskTitleInput.setText(task.title)
        newTaskDescriptionInput.setText(task.content)
        prioritySelector.setSelection(task.taskPriority - 1)
    }

    private fun initListeners() {
        saveTaskAction.setOnClickListener { saveTask() }
    }

    private fun saveTask() {
        if (isInputEmpty()){
            context?.displayToast(getString(R.string.emptyFields))
            return
        }

        val title = newTaskTitleInput.text.toString()
        val content = newTaskDescriptionInput.text.toString()
        priority = prioritySelector.priorityFactory().getValue()

        interactor.edit(EditTaskRequest(task.id, title, content, priority), editTaskCallback())
    }

    private fun editTaskCallback(): Callback<EditTaskResponse> = object : Callback<EditTaskResponse> {
        override fun onFailure(call: Call<EditTaskResponse>, t: Throwable) {
            activity?.displayToast(getString(R.string.no_internet))
        }

        override fun onResponse(call: Call<EditTaskResponse>, response: Response<EditTaskResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body().let { handleOkResponse(it) }
                    else -> handleSomethingWentWrong()
                }
            }
            else displayMessageFromCode(response.code())
        }

    }

    private fun handleOkResponse(editTaskResponse: EditTaskResponse?){
        repository.updateTask(task.id, newTaskTitleInput.text.toString(), newTaskDescriptionInput.text.toString(), priority)
        taskChangedListener?.onTaskChanged(task)
        dismiss()
    }

    private fun handleSomethingWentWrong() = this.activity?.displayToast(getString(R.string.sth_wrong))

    private fun isInputEmpty(): Boolean = TextUtils.isEmpty(newTaskTitleInput.text) || TextUtils.isEmpty(
        newTaskDescriptionInput.text
    )

    companion object{

        fun newInstance(): ChangeTaskFragmentDialog {
            return ChangeTaskFragmentDialog()
        }
    }

}