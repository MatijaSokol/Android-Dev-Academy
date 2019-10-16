package hr.ferit.matijasokol.taskie.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.common.*
import hr.ferit.matijasokol.taskie.db.repository.TaskRoomRepository
import hr.ferit.matijasokol.taskie.model.BackendTask
import hr.ferit.matijasokol.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_details.*

class TaskDetailsFragment : BaseFragment(), ChangeTaskFragmentDialog.TaskChangedListener {

    private val repository = TaskRoomRepository()
    private var taskID = NO_TASK

    override fun getLayoutResourceId(): Int = R.layout.fragment_task_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(repository.getTask(taskID))
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_top_details_frag, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuTopEdit -> {
                changeTask(repository.getTask(taskID))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun changeTask(task: BackendTask) {
        val dialog = ChangeTaskFragmentDialog.newInstance()
        dialog.setTaskChangedListener(this)
        dialog.task = task
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun tryDisplayTask(task: BackendTask) {
        try {
            displayTask(task)
        } catch (e: NoSuchElementException) {
            context?.displayToast(getString(R.string.noTaskFound))
        }
    }

    private fun displayTask(task: BackendTask) {
        detailsTaskTitle.text = task.title
        detailsTaskDescription.text = task.content
        detailsPriorityView.setBackgroundResource(toPriority(task.taskPriority).getColor())
    }

    override fun onTaskChanged(task: BackendTask) {
        detailsTaskTitle.text = repository.getTask(taskID).title
        detailsTaskDescription.text = repository.getTask(taskID).content
        detailsPriorityView.setBackgroundResource(toPriority(repository.getTask(taskID).taskPriority).getColor())
    }

    companion object {
        const val NO_TASK: String = ""

        fun newInstance(taskId: String): TaskDetailsFragment {
            val bundle = Bundle().apply { putString(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment().apply { arguments = bundle }
        }
    }
}