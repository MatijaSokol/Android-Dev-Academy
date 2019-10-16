package hr.ferit.matijasokol.taskie.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.common.RESPONSE_OK
import hr.ferit.matijasokol.taskie.common.displayMessageFromCode
import hr.ferit.matijasokol.taskie.common.displayToast
import hr.ferit.matijasokol.taskie.common.priorityFactory
import hr.ferit.matijasokol.taskie.db.repository.TaskRepository
import hr.ferit.matijasokol.taskie.db.repository.TaskRoomRepository
import hr.ferit.matijasokol.taskie.model.BackendPriorityTask
import hr.ferit.matijasokol.taskie.model.BackendTask
import hr.ferit.matijasokol.taskie.model.request.AddTaskRequest
import hr.ferit.matijasokol.taskie.networking.BackendFactory
import hr.ferit.matijasokol.taskie.persistence.PreferenceManager
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTaskFragmentDialog: DialogFragment() {

    private var taskAddedListener: TaskAddedListener? = null
    private val repository: TaskRepository = TaskRoomRepository()
    private val interactor = BackendFactory.getTaskieInteractor()

    interface TaskAddedListener{
        fun onTaskAdded(task: BackendTask)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    fun setTaskAddedListener(listener: TaskAddedListener){
        taskAddedListener = listener
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
        context?.let {
            prioritySelector.adapter = ArrayAdapter<BackendPriorityTask>(it, android.R.layout.simple_spinner_dropdown_item, BackendPriorityTask.values())
            setPrioritySelector()
        }
    }

    private fun setPrioritySelector(){
        when(PreferenceManager.retrievePriority()){
            PRIORITY_LOW -> prioritySelector.setSelection(0)
            PRIORITY_MEDIUM -> prioritySelector.setSelection(1)
            PRIORITY_HIGH -> prioritySelector.setSelection(2)
        }
    }

    private fun initListeners(){
        saveTaskAction.setOnClickListener{ saveTask() }
    }

    private fun saveTask() {
        if (isInputEmpty()) {
            context?.displayToast(getString(R.string.emptyFields))
            return
        }

        val title = newTaskTitleInput.text.toString()
        val description = newTaskDescriptionInput.text.toString()
        val priority = prioritySelector.priorityFactory()
        interactor.save(AddTaskRequest(title, description, priority.getValue()), addTaskCallback())
        clearUi()
    }

    private fun addTaskCallback(): Callback<BackendTask> = object : Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>?, t: Throwable?) {
            activity?.displayToast(getString(R.string.no_internet))
        }

        override fun onResponse(call: Call<BackendTask>?, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponse(response.body())
                    else -> handleSomethingWentWrong()
                }
            }
            else displayMessageFromCode(response.code())
        }
    }

    private fun handleOkResponse(task: BackendTask?) = task?.run { onTaskiesReceived(this) }

    private fun handleSomethingWentWrong() = this.activity?.displayToast(getString(R.string.sth_wrong))

    private fun onTaskiesReceived(task: BackendTask) {
        taskAddedListener?.onTaskAdded(task)
        repository.addTask(task)
        dismiss()
    }

    private fun clearUi() {
        newTaskTitleInput.text.clear()
        newTaskDescriptionInput.text.clear()
        prioritySelector.setSelection(0)
    }

    private fun isInputEmpty(): Boolean = TextUtils.isEmpty(newTaskTitleInput.text) || TextUtils.isEmpty(
        newTaskDescriptionInput.text
    )

    companion object{
        const val PRIORITY_LOW = "LOW"
        const val PRIORITY_MEDIUM = "MEDIUM"
        const val PRIORITY_HIGH = "HIGH"

        fun newInstance(): AddTaskFragmentDialog {
            return AddTaskFragmentDialog()
        }
    }
}