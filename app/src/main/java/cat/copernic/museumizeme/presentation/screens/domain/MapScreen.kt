package cat.copernic.museumizeme.presentation.screens.domain

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cat.copernic.museumizeme.dao.MuseumDaoImpl
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreen(museumDao: MuseumDaoImpl) {
    val museums by museumDao.getMuseumsFlow().collectAsState(emptyList())
    val terrassa = LatLng(41.56667, 2.01667)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(terrassa, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        museums.forEach { museum ->
            Marker(
                state = MarkerState(position = LatLng(museum.lat, museum.lng)),
                title = museum.name,
            )
        }
    }
}
