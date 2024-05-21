package cat.copernic.museumizeme.presentation.screens.domain

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.copernic.museumizeme.R
import cat.copernic.museumizeme.dao.MuseumDaoImpl
import cat.copernic.museumizeme.dao.UserDaoImpl
import cat.copernic.museumizeme.domain.authentication.User
import cat.copernic.museumizeme.domain.authentication.UserRole
import cat.copernic.museumizeme.presentation.screens.domain.composables.AddMuseumDialog
import cat.copernic.museumizeme.presentation.screens.domain.composables.MuseumItem
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MuseumScreen(museumDao: MuseumDaoImpl, currentUser: FirebaseUser?, userDao: UserDaoImpl) {
    var showAddMuseumDialog by remember { mutableStateOf(false) }

    val museums by museumDao.getMuseumsFlow().collectAsState(emptyList())

    val scope = rememberCoroutineScope()

    var user by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(Unit) {
        val retrievedUser = currentUser?.email?.let { userDao.getUserByEmail(it) }
        user = retrievedUser
    }

    val canAddMuseum = user?.role == UserRole.ADMIN

    Scaffold(
        floatingActionButton = {
            if (canAddMuseum) {
                FloatingActionButton(
                    onClick = {
                        showAddMuseumDialog = true
                    },
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.add_museum))
                }
                if (showAddMuseumDialog) {
                    AddMuseumDialog(
                        onMuseumAdded = { museum ->
                            scope.launch {
                                museumDao.addMuseum(museum)
                            }
                            showAddMuseumDialog = false
                        },
                        onDialogDismissed = { showAddMuseumDialog = false },
                    )
                }
            }
        }
    ) {
        if (!museums.isNullOrEmpty()) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(1),
                contentPadding = PaddingValues(4.dp)
            ) {
                museums.forEach {
                    item {
                        MuseumItem(
                            museum = it,
                            museumDaoImpl = museumDao,
                            currentUser = currentUser,
                            userDaoImpl = userDao
                        )
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.no_museums_found),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}