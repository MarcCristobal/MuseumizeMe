package cat.copernic.museumizeme.services

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import cat.copernic.museumizeme.R
import cat.copernic.museumizeme.authentication.AuthService
import cat.copernic.museumizeme.authentication.model.AuthRes
import cat.copernic.museumizeme.presentation.navigation.model.Routes

class ResetPasswordService {
    suspend fun resetPass(
        email: String,
        auth: AuthService,
        context: Context,
        navigation: NavController
    ) {
        when (val result = auth.resetPassword(email)) {
            is AuthRes.Success -> {
                Toast.makeText(context, context.getString(R.string.email_sent), Toast.LENGTH_SHORT).show()
                navigation.navigate(Routes.Login.route)
            }

            is AuthRes.Error -> {
                Toast.makeText(context,
                    context.getString(R.string.error_sending_the_email), Toast.LENGTH_SHORT).show()
            }
        }
    }
}