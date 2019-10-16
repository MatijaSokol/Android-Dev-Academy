package hr.ferit.matijasokol.taskie.ui.fragments

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
import hr.ferit.matijasokol.taskie.db.repository.TaskRoomRepository
import hr.ferit.matijasokol.taskie.model.BackendTask
import hr.ferit.matijasokol.taskie.model.response.DeleteTaskResponse
import hr.ferit.matijasokol.taskie.model.response.GetTasksResponse
import hr.ferit.matijasokol.taskie.networking.BackendFactory
import hr.ferit.matijasokol.taskie.ui.activities.ContainerActivity
import hr.ferit.matijasokol.taskie.ui.adapters.TaskAdapter
import hr.ferit.matijasokol.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TasksFragment : BaseFragment(), AddTaskFragmentDialog.TaskAddedListener {

    private val repository = TaskRoomRepository()
    private val adapter by lazy { TaskAdapter {onItemSelected(it)} }
    private val interactor = BackendFactory.getTaskieInteractor()

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
        interactor.getTasks(getTaskieCallback())
    }

    private fun getTaskieCallback(): Callback<GetTasksResponse> = object : Callback<GetTasksResponse> {
        override fun onFailure(call: Call<GetTasksResponse>?, t: Throwable?) {
            progress.gone()
            activity?.displayToast(getString(R.string.no_internet))
        }

        override fun onResponse(call: Call<GetTasksResponse>?, response: Response<GetTasksResponse>) {
            progress.gone()
            noData.gone()
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponseGetTaskie(response)
                    else -> handleSomethingWentWrongGetTaskie()
                }
            }
            else displayMessageFromCode(response.code())
        }
    }

    private fun handleOkResponseGetTaskie(response: Response<GetTasksResponse>) {
        response.body()?.notes?.let {
            adapter.setData(it)
            checkList()
        }
    }

    private fun handleSomethingWentWrongGetTaskie() = this.activity?.displayToast(getString(R.string.sth_wrong))

    private fun checkList() {
        if (adapter.getData().isEmpty()) {
            noData.visible()
        } else {
            noData.gone()
        }
    }

    private fun deleteTaskCallback(taskId: String): Callback<DeleteTaskResponse> = object  : Callback<DeleteTaskResponse>{
        override fun onFailure(call: Call<DeleteTaskResponse>, t: Throwable) {
            progress.gone()
            activity?.displayToast(getString(R.string.no_internet))
        }

        override fun onResponse(call: Call<DeleteTaskResponse>, response: Response<DeleteTaskResponse>) {
            progress.gone()
            noData.gone()
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponseDeleteTask(taskId)
                    else -> handleSomethingWentWrongDeleteTask()
                }
            }
            else displayMessageFromCode(response.code())
        }
    }

    private fun handleOkResponseDeleteTask(id: String){
        val task = adapter.getTaskById(id)
        if (task != null){
            adapter.deleteTask(task)
            repository.deleteTask(task)
        }
        checkList()
        if (sorted) adapter.sortByPriority()
    }

    private fun handleSomethingWentWrongDeleteTask() = this.activity?.displayToast(getString(R.string.sth_wrong))

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
            interactor.deleteTask(it.id, deleteTaskCallback(it.id))
        }
        repository.deleteAll()
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
        interactor.deleteTask(task.id, deleteTaskCallback(task.id))
    }

    override fun onStart() {
        super.onStart()
        adapter.replaceData(repository.getAllTasks())
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

    companion object {
        fun newInstance(): Fragment {
            return TasksFragment()
        }
    }
}
