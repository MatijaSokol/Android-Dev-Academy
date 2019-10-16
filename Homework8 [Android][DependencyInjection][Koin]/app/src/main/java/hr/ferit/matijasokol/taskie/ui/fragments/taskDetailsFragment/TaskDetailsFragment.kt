package hr.ferit.matijasokol.taskie.ui.fragments.taskDetailsFragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.common.*
import hr.ferit.matijasokol.taskie.model.BackendTask
import hr.ferit.matijasokol.taskie.ui.fragments.changeTaskFragment.ChangeTaskFragmentDialog
import hr.ferit.matijasokol.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_details.*
import org.koin.android.ext.android.inject

class TaskDetailsFragment : BaseFragment(),
    ChangeTaskFragmentDialog.TaskChangedListener {

    private var taskID = NO_TASK

    private val presenter by inject<TaskDetailsFragmentContract.Presenter>()

    override fun getLayoutResourceId(): Int = R.layout.fragment_task_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(presenter.getTaskByIdFromRepository(taskID))
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_top_details_frag, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuTopEdit -> {
                changeTask(presenter.getTaskByIdFromRepository(taskID))
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
        detailsTaskTitle.text = presenter.getTaskByIdFromRepository(taskID).title
        detailsTaskDescription.text = presenter.getTaskByIdFromRepository(taskID).content
        detailsPriorityView.setBackgroundResource(toPriority(presenter.getTaskByIdFromRepository(taskID).taskPriority).getColor())
    }

    companion object {
        const val NO_TASK: String = ""

        fun newInstance(taskId: String): TaskDetailsFragment {
            val bundle = Bundle().apply { putString(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment().apply { arguments = bundle }
        }
    }
}