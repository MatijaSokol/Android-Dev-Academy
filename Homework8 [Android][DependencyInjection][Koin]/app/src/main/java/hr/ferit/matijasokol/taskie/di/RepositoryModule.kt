package hr.ferit.matijasokol.taskie.di

import hr.ferit.matijasokol.taskie.persistence.repository.TaskRepository
import hr.ferit.matijasokol.taskie.persistence.repository.TaskRoomRepository
import hr.ferit.matijasokol.taskie.prefs.SharedPrefsHelper
import hr.ferit.matijasokol.taskie.prefs.provideSharedPrefs
import org.koin.dsl.module

val repositoryModule = module {
    factory<TaskRepository> {TaskRoomRepository()}
    factory<SharedPrefsHelper> { provideSharedPrefs()}
}