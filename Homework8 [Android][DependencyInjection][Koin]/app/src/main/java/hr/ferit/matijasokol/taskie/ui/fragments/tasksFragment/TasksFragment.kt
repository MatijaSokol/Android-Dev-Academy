package hr.ferit.matijasokol.taskie.ui.fragments.tasksFragment

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.common.*
import hr.ferit.matijasokol.taskie.model.BackendTask
import hr.ferit.matijasokol.taskie.networking.BackendFactory
import hr.ferit.matijasokol.taskie.persistence.repository.TaskRoomRepository
import hr.ferit.matijasokol.taskie.presenters.TasksFragmentPresenter
import hr.ferit.matijasokol.taskie.ui.activities.ContainerActivity
import hr.ferit.matijasokol.taskie.ui.adapters.TaskAdapter
import hr.ferit.matijasokol.taskie.ui.fragments.addTaskFragmentDialog.AddTaskFragmentDialog
import hr.ferit.matijasokol.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class TasksFragment : BaseFragment(), AddTaskFragmentDialog.TaskAddedListener, TasksFragmentContract.View{

    private val presenter by inject<TasksFragmentContract.Presenter>()

    private val adapter by lazy { TaskAdapter {onItemSelected(it)} }

    private var sorted = false

    override fun getLayoutResourceId() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        setHasOptionsMenu(true)
    }

    private fun initUi() {
        progress.visible()
        noData.visible()
        setUpRecycler()
        initListeners()
        setSwiper()
        getAllTasks()
    }

    private fun initListeners() {
        addTask.setOnClickListener { addTask() }
        swiper.setOnRefreshListener { refreshTasksNet() }
    }

    private fun refreshTasksNet() {
        swiper.isRefreshing = true
        getAllTasks()
        swiper.isRefreshing = false
    }

    private fun setSwiper() {
        swiper.setColorSchemeColors(
            ContextCompat.getColor(context!!, R.color.blue),
            ContextCompat.getColor(context!!, R.color.green),
            ContextCompat.getColor(context!!, R.color.red)
        )
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

    private fun getAllTasks() {
        progress.visible()
        presenter.getTasks()
    }

    override fun getTasksResponseSucces(tasks: MutableList<BackendTask>?) {
        progress.gone()
        noData.gone()
        adapter.setData(tasks!!)
        checkList()
    }

    override fun getTasksReponseFailure() {
        progress.gone()
        activity?.displayToast(getString(R.string.no_internet))
    }

    override fun getTasksResponseWrong() {
        this.activity?.displayToast(getString(R.string.sth_wrong))
    }

    override fun getTasksResponseFailureWithCode(code: Int) {
        displayMessageFromCode(code)
    }

    override fun deleteTaskResponseSuccess(taskId: String) {
        progress.gone()
        noData.gone()

        val task = adapter.getTaskById(taskId)
        if (task != null){
            adapter.deleteTask(task)
            presenter.deleteTaskFromRepository(task)
        }
        checkList()
        if (sorted) adapter.sortByPriority()
    }

    override fun deleteTaskResponseFailure() {
        progress.gone()
        activity?.displayToast(getString(R.string.no_internet))
    }

    override fun deleteTaskResponseWrong() {
        this.activity?.displayToast(getString(R.string.sth_wrong))
    }

    override fun deleteTaskResponseFailureWithCode(code: Int) {
        displayMessageFromCode(code)
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
            .setNegativeButton(getString(R.string.no)){ _, _ -> adapter.refreshAdapter()}
            .show()
    }

    private fun deleteAllTasks(){
        adapter.getData().forEach {
            presenter.deleteTask(it.id)
        }
        presenter.deleteAllTasksFromRepository()
    }

    private fun showDeleteAlertDialog(task: BackendTask){
        AlertDialog.Builder(context!!)
            .setTitle(getString(R.string.delete_task))
            .setIcon(R.drawable.ic_warning)
            .setPositiveButton(getString(R.string.yes)){_, _->  deleteTask(task)}
            .setNegativeButton(getString(R.string.no)){_, _ -> adapter.refreshAdapter()}
            .show()
    }

    private fun deleteTask(task: BackendTask){
        presenter.deleteTask(task.id)
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.replaceData(presenter.getAllTasksFromRepository())
    }

    private fun onItemSelected(task: BackendTask){
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_SCREEN_TYPE, ContainerActivity.SCREEN_TASK_DETAILS)
            putExtra(EXTRA_TASK_ID, task.id)
        }
        startActivity(detailsIntent)
    }

    private fun addTask() {
        val dialog = AddTaskFragmentDialog.newInstance()
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun onTaskAdded(task: BackendTask) {
        adapter.addData(task)
        checkList()
    }

    private fun checkList() {
        if (adapter.getData().isEmpty()) {
            noData.visible()
        } else {
            noData.gone()
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return TasksFragment()
        }
    }
}
