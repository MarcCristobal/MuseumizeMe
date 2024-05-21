package cat.copernic.museumizeme.presentation.screens.domain.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cat.copernic.museumizeme.R
import cat.copernic.museumizeme.domain.screens.Museum

@Composable
fun AddMuseumDialog(
    onMuseumAdded: (Museum) -> Unit,
    onDialogDismissed: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var lat by remember { mutableStateOf("") }
    var lng by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = stringResource(R.string.add_museum)) },
        confirmButton = {
            Button(
                onClick = {
                    val newMuseum = Museum(
                        name = name,
                        description = description,
                        lat = lat.toDouble(),
                        lng = lng.toDouble()
                    )
                    onMuseumAdded(newMuseum)
                    name = ""
                    description = ""
                    lat = ""
                    lng = ""
                }
            ) {
                Text(text = stringResource(R.string.add))
            }
        },
        dismissButton = {
            Button(onClick = {
                onDialogDismissed()
            }) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        text = {
            Column {
                TextField(value = name,
                    onValueChange = { name = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    label = { Text(text = stringResource(id = R.string.name)) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                    value = description,
                    onValueChange = { description = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    maxLines = 4,
                    label = { Text(text = stringResource(R.string.description)) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(value = lat,
                    onValueChange = { lat = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = { Text(text = stringResource(R.string.latitude)) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(value = lng,
                    onValueChange = { lng = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = { Text(text = stringResource(R.string.longitude)) }
                )

                Spacer(modifier = Modifier.height(8.dp))

            }
        })
}



