package cat.copernic.museumizeme.services

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import cat.copernic.museumizeme.R
import cat.copernic.museumizeme.authentication.AuthService
import cat.copernic.museumizeme.authentication.model.AuthRes
import cat.copernic.museumizeme.presentation.navigation.model.Routes

class LoginService {
    suspend fun emailPassSignIn(
        email: String,
        password: String,
        auth: AuthService,
        context: Context,
        navigation: NavController
    ) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            when (val result = auth.signInWithEmailAndPassword(email, password)) {
                is AuthRes.Success -> {
                    navigation.navigate((Routes.Main.route)) {
                        popUpTo(Routes.Login.route) {
                            inclusive = true
                        }
                    }
                }

                is AuthRes.Error -> {
                    Toast.makeText(context,
                        context.getString(R.string.signin_error) + result.errorMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } else {
            Toast.makeText(context,
                context.getString(R.string.there_are_empty_fields), Toast.LENGTH_SHORT).show()
        }
    }
}