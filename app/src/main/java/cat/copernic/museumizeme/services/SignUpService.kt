package cat.copernic.museumizeme.services

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import cat.copernic.museumizeme.R
import cat.copernic.museumizeme.authentication.AuthService
import cat.copernic.museumizeme.authentication.model.AuthRes
import cat.copernic.museumizeme.dao.UserDaoImpl
import cat.copernic.museumizeme.domain.authentication.User
import cat.copernic.museumizeme.domain.authentication.UserRole

class SignUpService {
    suspend fun signUp(
        email: String,
        password: String,
        name: String,
        surname: String,
        auth: AuthService,
        context: Context,
        navigation: NavController,
    ) {
        val userDao = UserDaoImpl()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            when (val result = auth.createUserWithEmailAndPassword(email, password)) {
                is AuthRes.Success -> {
                    userDao.addUser(User(email, name, surname, UserRole.USER))
                    Toast.makeText(context,
                        context.getString(R.string.signup_success), Toast.LENGTH_SHORT).show()
                    navigation.popBackStack()
                }

                is AuthRes.Error -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.signup_error) + result.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(context, context.getString(R.string.there_are_empty_fields), Toast.LENGTH_SHORT).show()
        }
    }
}