package hr.ferit.matijasokol.taskie.model.response

import hr.ferit.matijasokol.taskie.model.BackendTask

data class GetTasksResponse(val notes: MutableList<BackendTask>? = mutableListOf())