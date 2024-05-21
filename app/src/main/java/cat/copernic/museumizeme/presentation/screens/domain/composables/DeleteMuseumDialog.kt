package cat.copernic.museumizeme.presentation.screens.domain.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cat.copernic.museumizeme.R

@Composable
fun DeleteMuseumDialog(onConfirmDelete: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.delete_museum)) },
        text = { Text(text = stringResource(R.string.are_you_sure_you_want_to_delete_the_museum)) },
        confirmButton = {
            Button(
                onClick = onConfirmDelete
            ) {
                Text(text = stringResource(R.string.accept))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}