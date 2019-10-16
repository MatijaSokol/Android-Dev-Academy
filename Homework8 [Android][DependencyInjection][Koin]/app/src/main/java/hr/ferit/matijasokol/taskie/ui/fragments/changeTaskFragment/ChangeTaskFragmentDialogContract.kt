package hr.ferit.matijasokol.taskie.ui.fragments.changeTaskFragment

interface ChangeTaskFragmentDialogContract {

    interface View{
        fun onEditTaskResponseSuccess()
        fun onEditTaskResponseFailure()
        fun onEditTaskResponseWrong()
        fun onEditTaskResponseFailureWithCode(code: Int)
    }

    interface Presenter{
        fun setView(view: View)
        fun editTaskInNet(taskId: String, title: String, content: String, priority: Int)
        fun editTaskInRepository(taskId: String, title: String, content: String, priority: Int)
    }
}