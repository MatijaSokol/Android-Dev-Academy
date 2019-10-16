package hr.ferit.matijasokol.taskie.ui.activites

import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.common.EXTRA_SCREEN_TYPE
import hr.ferit.matijasokol.taskie.common.EXTRA_TASK_ID
import hr.ferit.matijasokol.taskie.ui.activites.base.BaseActivity
import hr.ferit.matijasokol.taskie.ui.fragments.TaskDetailsFragment

class ContainerActivity : BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_container

    override fun setUpUi() {
        val screenType = intent.getStringExtra(EXTRA_SCREEN_TYPE)
        val id = intent.getIntExtra(EXTRA_TASK_ID, -1)
        if (screenType.isNotEmpty()) {
            when (screenType) {
                SCREEN_TASK_DETAILS -> showFragment(TaskDetailsFragment.newInstance(id))
            }
        } else {
            finish()
        }
    }

    companion object{
        const val SCREEN_TASK_DETAILS = "task_details"
    }
}
