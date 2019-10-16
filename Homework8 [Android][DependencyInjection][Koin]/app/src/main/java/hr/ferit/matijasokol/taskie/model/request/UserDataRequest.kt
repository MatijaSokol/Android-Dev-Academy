package hr.ferit.matijasokol.taskie.model.request

data class UserDataRequest(val email: String, val password: String, val name: String? = null)