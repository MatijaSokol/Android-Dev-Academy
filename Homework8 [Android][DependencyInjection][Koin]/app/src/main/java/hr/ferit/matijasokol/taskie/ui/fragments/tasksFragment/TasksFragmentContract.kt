package hr.ferit.matijasokol.taskie.ui.fragments.tasksFragment

import hr.ferit.matijasokol.taskie.model.BackendTask

interface TasksFragmentContract {

    interface View{
        fun getTasksResponseSucces(tasks: MutableList<BackendTask>?)
        fun getTasksReponseFailure()
        fun getTasksResponseWrong()
        fun getTasksResponseFailureWithCode(code: Int)
        fun deleteTaskResponseSuccess(taskId: String)
        fun deleteTaskResponseFailure()
        fun deleteTaskResponseWrong()
        fun deleteTaskResponseFailureWithCode(code: Int)
    }

    interface Presenter{
        fun setView(view: View)
        fun getTasks()
        fun deleteTask(taskId: String)
        fun deleteTaskFromRepository(task: BackendTask)
        fun deleteAllTasksFromRepository()
        fun getAllTasksFromRepository(): List<BackendTask>
    }
}