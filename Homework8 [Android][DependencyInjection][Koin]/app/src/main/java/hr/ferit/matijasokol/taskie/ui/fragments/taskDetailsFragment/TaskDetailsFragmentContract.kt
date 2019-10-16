package hr.ferit.matijasokol.taskie.ui.fragments.taskDetailsFragment

import hr.ferit.matijasokol.taskie.model.BackendTask

interface TaskDetailsFragmentContract {

    interface View{

    }

    interface Presenter{
        fun getTaskByIdFromRepository(taskId: String): BackendTask
    }
}