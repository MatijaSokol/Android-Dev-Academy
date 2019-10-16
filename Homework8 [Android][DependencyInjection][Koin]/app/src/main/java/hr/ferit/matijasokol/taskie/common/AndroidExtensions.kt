package hr.ferit.matijasokol.taskie.common

import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import hr.ferit.matijasokol.taskie.app.Taskie
import hr.ferit.matijasokol.taskie.model.BackendPriorityTask

fun FragmentActivity.showFragment(containerId: Int, fragment: Fragment, shouldAddToBackStack: Boolean = false, tag: String = ""){
    supportFragmentManager.beginTransaction().apply {
        if(shouldAddToBackStack){
            addToBackStack(tag)
        }
    }.replace(containerId, fragment).commitAllowingStateLoss()
}

fun Spinner.priorityFactory(): BackendPriorityTask {
    return  when (this.selectedItemPosition) {
        0 -> BackendPriorityTask.LOW
        1 -> BackendPriorityTask.MEDIUM
        else -> BackendPriorityTask.HIGH
    }
}

fun toPriority(num: Int): BackendPriorityTask{
    return when(num){
        1 -> BackendPriorityTask.LOW
        2 -> BackendPriorityTask.MEDIUM
        else -> BackendPriorityTask.HIGH
    }
}

fun displayMessageFromCode(code: Int){
    when(code){
        RESPONSE_BAD_REQUEST -> Taskie.instance.displayToast("Bad request")
        RESPONSE_NOT_FOUND -> Taskie.instance.displayToast("Not found")
        SERVER_ERROR -> Taskie.instance.displayToast("Server error")
    }
}