package cat.copernic.museumizeme.presentation.screens.authorization

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cat.copernic.museumizeme.R
import cat.copernic.museumizeme.authentication.AuthService
import cat.copernic.museumizeme.authentication.model.AuthRes
import cat.copernic.museumizeme.presentation.navigation.model.Routes
import cat.copernic.museumizeme.presentation.screens.authorization.composables.SocialMediaButton
import cat.copernic.museumizeme.services.LoginService
import cat.copernic.museumizeme.ui.theme.Gold
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(auth: AuthService, navigation: NavController) {

    val loginService = LoginService()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (val account =
            auth.handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(result.data))) {
            is AuthRes.Success -> {
                val credential = GoogleAuthProvider.getCredential(account?.data?.idToken, null)
                scope.launch {
                    val fireUser = auth.signInWithGoogleCredential(credential)
                    if (fireUser != null) {
                        Toast.makeText(context, "Bienvenidx", Toast.LENGTH_SHORT).show()
                        navigation.navigate(Routes.Main.route) {
                            popUpTo(Routes.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }

            is AuthRes.Error -> {
                Toast.makeText(context,
                    context.getString(R.string.error) + account.errorMessage, Toast.LENGTH_SHORT).show()
            }

            else -> {
                Toast.makeText(context,
                    context.getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ClickableText(
            text = AnnotatedString(stringResource(R.string.don_t_have_an_account_sign_up)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(40.dp),
            onClick = {
                navigation.navigate(Routes.SignUp.route)
            },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary
            )
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.museumizeme),
                contentDescription = stringResource(R.string.firebase),
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .size(100.dp)

            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.museumizeme),
                style = TextStyle(fontSize = 30.sp, color = Gold)
            )

            Spacer(modifier = Modifier.height(30.dp))

            TextField(
                label = { Text(text = stringResource(R.string.email)) },
                value = email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { email = it })

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                label = { Text(text = stringResource(R.string.password)) },
                value = password,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { password = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        scope.launch {
                            loginService.emailPassSignIn(email, password, auth, context, navigation)
                        }
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .height(50.dp)
                ) {
                    Text(text = stringResource(R.string.sign_in).uppercase())
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            ClickableText(
                text = AnnotatedString(stringResource(R.string.forgot_your_password)),
                onClick = {
                    navigation.navigate(Routes.ForgotPassword.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(text = stringResource(R.string.o))

            Spacer(modifier = Modifier.height(25.dp))

            SocialMediaButton(
                onClick = {
                    auth.signInWithGoogle(googleSignInLauncher)
                },
                text = stringResource(R.string.continue_with_google),
                icon = R.drawable.google,
                color = Color(0xFFF1F1F1)
            )
        }
    }
}
