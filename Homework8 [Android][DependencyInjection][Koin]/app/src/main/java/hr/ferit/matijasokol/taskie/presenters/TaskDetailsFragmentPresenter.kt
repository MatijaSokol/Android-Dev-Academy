package hr.ferit.matijasokol.taskie.presenters

import hr.ferit.matijasokol.taskie.model.BackendTask
import hr.ferit.matijasokol.taskie.persistence.repository.TaskRepository
import hr.ferit.matijasokol.taskie.ui.fragments.taskDetailsFragment.TaskDetailsFragmentContract

class TaskDetailsFragmentPresenter (private val repository: TaskRepository) : TaskDetailsFragmentContract.Presenter{

    override fun getTaskByIdFromRepository(taskId: String): BackendTask {
        return repository.getTask(taskId)
    }
}