package hr.ferit.matijasokol.taskie.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.model.BackendTask

class TaskAdapter(private val onItemSelected: (BackendTask) -> Unit) : RecyclerView.Adapter<TaskHolder>() {

    private val data: MutableList<BackendTask> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskHolder(v)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bindData(data[position], onItemSelected)
    }

    fun setData(data: List<BackendTask>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun getTaskByPosition(position: Int) = data[position]

    fun getPositionByTask(task: BackendTask): Int = data.indexOf(task)

    fun getTaskById(taskId: String): BackendTask?{
        data.forEach {
            if (it.id == taskId)    return it
        }
        return null
    }

    fun deleteTask(task: BackendTask){
        val position = data.indexOf(task)
        data.remove(task)
        notifyItemRemoved(position)
        notifyItemRangeRemoved(position, data.size)
    }

    fun replaceData(data: List<BackendTask>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(item: BackendTask) {
        data.add(item)
        notifyItemInserted(data.size)
    }

    fun sortByPriority(){
        data.sortByDescending { it.taskPriority }
        notifyDataSetChanged()
    }

    fun refreshAdapter() = notifyDataSetChanged()

    fun getData(): MutableList<BackendTask> = data
}