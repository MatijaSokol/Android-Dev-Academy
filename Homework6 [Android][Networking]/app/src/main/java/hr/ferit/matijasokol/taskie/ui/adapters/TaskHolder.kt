package hr.ferit.matijasokol.taskie.ui.adapters

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.matijasokol.taskie.common.toPriority
import hr.ferit.matijasokol.taskie.model.BackendTask
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_task.view.*

class TaskHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindData(backendTask: BackendTask, onItemSelected: (BackendTask) -> Unit) {

        containerView.setOnClickListener { onItemSelected(backendTask) }

        containerView.taskTitle.text = backendTask.title

        val drawable = containerView.taskPriority.drawable
        val wrapDrawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor
            (containerView.context, toPriority(backendTask.taskPriority).getColor()))
    }
}