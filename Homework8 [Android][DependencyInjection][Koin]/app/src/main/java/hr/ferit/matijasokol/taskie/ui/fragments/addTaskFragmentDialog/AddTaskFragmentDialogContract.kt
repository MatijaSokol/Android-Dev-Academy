package hr.ferit.matijasokol.taskie.ui.fragments.addTaskFragmentDialog

import hr.ferit.matijasokol.taskie.model.BackendTask

interface AddTaskFragmentDialogContract{

    interface View{
        fun onAddTaskResponseSuccess(task: BackendTask)
        fun onAddTaskResponseFailure()
        fun onAddTaskResponseFailureWithCode(code: Int)
        fun onAddTaskResponseWrong()

    }

    interface Presenter{
        fun setView(view: View)
        fun saveTask(title: String, content: String, priority: Int)
    }
}