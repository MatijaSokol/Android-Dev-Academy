package hr.ferit.matijasokol.taskie.di

import hr.ferit.matijasokol.taskie.presenters.*
import hr.ferit.matijasokol.taskie.ui.activities.loginActivity.LoginActivityContract
import hr.ferit.matijasokol.taskie.ui.activities.registerActivity.RegisterActivityContract
import hr.ferit.matijasokol.taskie.ui.activities.splashActivity.SplashActivityContract
import hr.ferit.matijasokol.taskie.ui.fragments.addTaskFragmentDialog.AddTaskFragmentDialogContract
import hr.ferit.matijasokol.taskie.ui.fragments.changeTaskFragment.ChangeTaskFragmentDialogContract
import hr.ferit.matijasokol.taskie.ui.fragments.taskDetailsFragment.TaskDetailsFragmentContract
import hr.ferit.matijasokol.taskie.ui.fragments.tasksFragment.TasksFragmentContract
import org.koin.dsl.module

val presentationModule = module {
    factory<AddTaskFragmentDialogContract.Presenter> {AddTaskFragmentDialogPresenter(get(), get())}
    factory<ChangeTaskFragmentDialogContract.Presenter> {ChangeTaskFragmentDialogPresenter(get(), get())}
    factory<LoginActivityContract.Presenter> {LoginActivityPresenter(get(), get())}
    factory<RegisterActivityContract.Presenter> {RegisterActivityPresenter(get())}
    factory<SplashActivityContract.Presenter> {SplashActivityPresenter(get())}
    factory<TaskDetailsFragmentContract.Presenter> {TaskDetailsFragmentPresenter(get())}
    factory<TasksFragmentContract.Presenter> {TasksFragmentPresenter(get(), get())}
}