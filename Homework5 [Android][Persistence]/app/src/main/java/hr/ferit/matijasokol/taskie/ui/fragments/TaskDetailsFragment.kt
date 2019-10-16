package hr.ferit.matijasokol.taskie.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.common.EXTRA_TASK_ID
import hr.ferit.matijasokol.taskie.common.displayToast
import hr.ferit.matijasokol.taskie.db.repository.TaskRoomRepository
import hr.ferit.matijasokol.taskie.model.Task
import hr.ferit.matijasokol.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_details.*

class TaskDetailsFragment : BaseFragment(), ChangeTaskFragmentDialog.TaskChangedListener {

    private val repository = TaskRoomRepository()
    private var taskID = NO_TASK

    override fun getLayoutResourceId(): Int = R.layout.fragment_task_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getLong(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(taskID)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_top_details_frag, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menuTopEdit -> {
                changeTask()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun changeTask() {
        val dialog = ChangeTaskFragmentDialog.newInstance()
        dialog.setTaskChangedListener(this)
        dialog.task = repository.getTask(taskID)
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun tryDisplayTask(id: Long) {
        try {
            val task = repository.getTask(id)
            displayTask(task)
        } catch (e: NoSuchElementException) {
            context?.displayToast(getString(R.string.noTaskFound))
        }
    }

    private fun displayTask(task: Task) {
        detailsTaskTitle.text = task.title
        detailsTaskDescription.text = task.description
        detailsPriorityView.setBackgroundResource(task.priority.getColor())
    }

    override fun onTaskChanged(task: Task) {
        displayTask(task)
    }

    companion object {
        const val NO_TASK: Long = -1

        fun newInstance(taskId: Long): TaskDetailsFragment {
            val bundle = Bundle().apply { putLong(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment().apply { arguments = bundle }
        }
    }
}