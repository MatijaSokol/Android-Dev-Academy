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
import hr.ferit.matijasokol.taskie.common.displayToast
import hr.ferit.matijasokol.taskie.db.repository.TaskRepository
import hr.ferit.matijasokol.taskie.db.repository.TaskRoomRepository
import hr.ferit.matijasokol.taskie.model.Priority
import hr.ferit.matijasokol.taskie.model.Task
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*

class ChangeTaskFragmentDialog : DialogFragment(){

    private var taskChangedListener: TaskChangedListener? = null
    private val repository: TaskRepository =
        TaskRoomRepository()

    lateinit var task: Task

    interface TaskChangedListener{
        fun onTaskChanged(task: Task)
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

    private fun initListeners() {
        saveTaskAction.setOnClickListener { saveTask() }
    }

    private fun saveTask() {
        if (isInputEmpty()){
            context?.displayToast(getString(R.string.emptyFields))
            return
        }

        val title = newTaskTitleInput.text.toString()
        val description = newTaskDescriptionInput.text.toString()
        val priority = prioritySelector.selectedItem as Priority

        val updatedTask = Task(title = title, description = description, priority = priority)

        repository.updateTask(task.taskDbId, title, description, priority)

        clearUi()

        taskChangedListener?.onTaskChanged(updatedTask)
        dismiss()
    }

    private fun clearUi() {
        newTaskTitleInput.text.clear()
        newTaskDescriptionInput.text.clear()
        prioritySelector.setSelection(0)
    }

    private fun initUi(){
        context?.let {
            prioritySelector.adapter = ArrayAdapter<Priority>(it, android.R.layout.simple_spinner_dropdown_item, Priority.values())
        }
    }

    private fun isInputEmpty(): Boolean = TextUtils.isEmpty(newTaskTitleInput.text) || TextUtils.isEmpty(
        newTaskDescriptionInput.text
    )

    companion object{

        fun newInstance(): ChangeTaskFragmentDialog {
            return ChangeTaskFragmentDialog()
        }
    }

}