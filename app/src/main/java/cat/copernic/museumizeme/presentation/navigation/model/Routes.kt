package cat.copernic.museumizeme.presentation.navigation.model

sealed class Routes(val route: String) {
    object Login : Routes("Login Screen")
    object Main : Routes("Main Screen")
    object SignUp : Routes("SignUp Screen")
    object ForgotPassword : Routes("ForgotPassword Screen")
}