package hr.ferit.matijasokol.taskie.ui.fragments.changeTaskFragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.common.displayMessageFromCode
import hr.ferit.matijasokol.taskie.common.displayToast
import hr.ferit.matijasokol.taskie.common.priorityFactory
import hr.ferit.matijasokol.taskie.model.BackendTask
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*
import org.koin.android.ext.android.inject

class ChangeTaskFragmentDialog : DialogFragment(), ChangeTaskFragmentDialogContract.View{

    private var taskChangedListener: TaskChangedListener? = null
    private val presenter by inject<ChangeTaskFragmentDialogContract.Presenter>()
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

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
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

        presenter.editTaskInNet(task.id, title, content, priority)
    }

    override fun onEditTaskResponseSuccess() {
        presenter.editTaskInRepository(task.id, newTaskTitleInput.text.toString(), newTaskDescriptionInput.text.toString(), priority)
        taskChangedListener?.onTaskChanged(task)
        dismiss()
    }

    override fun onEditTaskResponseFailure() {
        activity?.displayToast(getString(R.string.no_internet))
    }

    override fun onEditTaskResponseWrong() {
        this.activity?.displayToast(getString(R.string.sth_wrong))
    }

    override fun onEditTaskResponseFailureWithCode(code: Int) {
        displayMessageFromCode(code)
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