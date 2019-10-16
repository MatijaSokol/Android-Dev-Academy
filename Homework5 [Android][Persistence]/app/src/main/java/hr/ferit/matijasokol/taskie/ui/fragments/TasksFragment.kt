package hr.ferit.matijasokol.taskie.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.common.*
import hr.ferit.matijasokol.taskie.db.repository.TaskRoomRepository
import hr.ferit.matijasokol.taskie.model.Task
import hr.ferit.matijasokol.taskie.ui.activities.ContainerActivity
import hr.ferit.matijasokol.taskie.ui.adapters.TaskAdapter
import hr.ferit.matijasokol.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : BaseFragment(), AddTaskFragmentDialog.TaskAddedListener {

    private val repository = TaskRoomRepository()
    private val adapter by lazy { TaskAdapter {onItemSelected(it)} }

    private var sorted = false

    override fun getLayoutResourceId() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
        refreshTasks()
        setHasOptionsMenu(true)
    }

    private fun initUi() {
        progress.visible()
        noData.visible()
        setUpRecycler()
    }

    private fun initListeners() {
        addTask.setOnClickListener { addTask() }
    }

    private fun setUpRecycler(){
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = adapter

        val helper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = adapter.getTaskByPosition(viewHolder.adapterPosition)
                showDeleteAlertDialog(task)
            }
        }

        val itemTouchHelper = ItemTouchHelper(helper)
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_top_tasks_frag, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuTopOrder -> {
                if (adapter.getData().size != 0){
                    adapter.sortByPriority()
                    sorted = true
                }
                else
                    context?.displayToast(getString(R.string.empty_list))
                return true
            }
            R.id.menuTopDelete -> {
                if (adapter.getData().size != 0)
                    showDeleteAllAlertDialog()
                else
                    context?.displayToast(getString(R.string.empty_list))
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDeleteAllAlertDialog(){
        AlertDialog.Builder(context!!)
            .setTitle(getString(R.string.delete_all))
            .setIcon(R.drawable.ic_warning)
            .setPositiveButton(getString(R.string.yes)){ _, _ -> deleteAllTasks()}
            .setNegativeButton(getString(R.string.no)){ _, _ -> refreshTasks()}
            .show()
    }

    private fun deleteAllTasks(){
        repository.deleteAll()
        refreshTasks()
    }

    private fun showDeleteAlertDialog(task: Task){
        AlertDialog.Builder(context!!)
            .setTitle(getString(R.string.delete_task))
            .setIcon(R.drawable.ic_warning)
            .setPositiveButton(getString(R.string.yes)){_, _->  deleteTask(task)}
            .setNegativeButton(getString(R.string.no)){_, _ -> refreshTasks()}
            .show()
    }

    private fun deleteTask(task: Task){
        repository.deleteTask(task)
        refreshTasks()
    }

    override fun onStart() {
        super.onStart()
        refreshTasks()
    }

    private fun refreshTasks() {
        progress.gone()
        val data = repository.getAllTasks()
        if (data.isNotEmpty()) {
            noData.gone()
        } else {
            noData.visible()
        }
        adapter.setData(data)

        if (sorted)
            adapter.sortByPriority()
    }

    private fun onItemSelected(task: Task){
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_SCREEN_TYPE, ContainerActivity.SCREEN_TASK_DETAILS)
            putExtra(EXTRA_TASK_ID, task.taskDbId)
        }
        startActivity(detailsIntent)
    }

    private fun addTask() {
        val dialog = AddTaskFragmentDialog.newInstance()
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun onTaskAdded(task: Task) {
        refreshTasks()
    }

    companion object {
        fun newInstance(): Fragment {
            return TasksFragment()
        }
    }
}
