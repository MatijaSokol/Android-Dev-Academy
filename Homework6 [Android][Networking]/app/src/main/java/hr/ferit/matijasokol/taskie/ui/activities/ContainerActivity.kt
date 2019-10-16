package hr.ferit.matijasokol.taskie.ui.activities

import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.ui.activities.base.BaseActivity
import hr.ferit.matijasokol.taskie.common.EXTRA_SCREEN_TYPE
import hr.ferit.matijasokol.taskie.common.EXTRA_TASK_ID
import hr.ferit.matijasokol.taskie.ui.fragments.TaskDetailsFragment

class ContainerActivity: BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_container

    override fun setUpUi() {
        val screenType = intent.getStringExtra(EXTRA_SCREEN_TYPE)
        val id = intent.getStringExtra(EXTRA_TASK_ID)
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
