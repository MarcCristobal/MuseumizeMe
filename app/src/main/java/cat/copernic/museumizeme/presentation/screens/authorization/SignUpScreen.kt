package cat.copernic.museumizeme.presentation.screens.authorization

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
import cat.copernic.museumizeme.presentation.navigation.model.Routes
import cat.copernic.museumizeme.services.SignUpService
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(auth: AuthService, navigation: NavController) {
    val signUpService = SignUpService()

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.museumizeme),
            contentDescription = stringResource(id = R.string.firebase),
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .size(100.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))


        Text(
            text = "Create Account",
            style = TextStyle(fontSize = 30.sp, color = MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            label = { Text(text = stringResource(R.string.name)) },
            value = name,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { name = it })


        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            label = { Text(text = stringResource(R.string.surname)) },
            value = surname,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { surname = it })

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            label = { Text(text = stringResource(id = R.string.email)) },
            value = email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { email = it })

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            label = { Text(text = stringResource(id = R.string.password)) },
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password = it })

        Spacer(modifier = Modifier.height(30.dp))

        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    scope.launch {
                        signUpService.signUp(
                            email,
                            password,
                            name,
                            surname,
                            auth,
                            context,
                            navigation
                        )
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .height(50.dp)
            ) {
                Text(text = stringResource(R.string.sign_up))
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        ClickableText(
            text = AnnotatedString(stringResource(id = R.string.already_have_an_account_log_in)),
            onClick = {
                navigation.navigate(Routes.Login.route)
            },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}