package hr.ferit.matijasokol.taskie.di

import hr.ferit.matijasokol.taskie.networking.interactors.TaskieInteractor
import hr.ferit.matijasokol.taskie.networking.interactors.TaskieInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    factory<TaskieInteractor> {TaskieInteractorImpl(get())}
}