package cat.copernic.museumizeme.presentation.screens.domain.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.copernic.museumizeme.R
import cat.copernic.museumizeme.dao.MuseumDaoImpl
import cat.copernic.museumizeme.dao.UserDaoImpl
import cat.copernic.museumizeme.domain.authentication.User
import cat.copernic.museumizeme.domain.authentication.UserRole
import cat.copernic.museumizeme.domain.screens.Museum
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MuseumItem(
    museum: Museum,
    museumDaoImpl: MuseumDaoImpl,
    currentUser: FirebaseUser?,
    userDaoImpl: UserDaoImpl
) {
    var showDeleteMuseumDialog by remember { mutableStateOf(false) }

    val onDeleteNoteConfirmed: () -> Unit = {
        CoroutineScope(Dispatchers.Default).launch {
            museumDaoImpl.deleteMuseum(museum.id ?: "")
        }
    }

    var user by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(Unit) {
        val retrievedUser = currentUser?.email?.let { userDaoImpl.getUserByEmail(it) }
        user = retrievedUser
    }

    val canDeleteMuseum = user?.role == UserRole.ADMIN

    if (showDeleteMuseumDialog) {
        DeleteMuseumDialog(
            onConfirmDelete = {
                onDeleteNoteConfirmed()
                showDeleteMuseumDialog = false
            },
            onDismiss = {
                showDeleteMuseumDialog = false
            }
        )
    }

    Card(
        modifier = Modifier.padding(6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Row {
                Image(
                    painter = painterResource(R.drawable.museumizeme),
                    contentDescription = stringResource(R.string.logo),
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = museum.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = museum.description,
                fontSize = 15.sp,
                lineHeight = 15.sp
            )
            if (canDeleteMuseum) {
                Spacer(modifier = Modifier.height(8.dp))
                IconButton(onClick = { showDeleteMuseumDialog = true }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.delete_icon))
                }
            }
        }
    }
}