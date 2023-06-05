package com.example.capstoneredouan.ui.view.map

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.capstoneredouan.R
import com.example.capstoneredouan.data.model.MapState
import com.example.capstoneredouan.data.model.MarkerInfo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun Map(
    state: MapState,
) {
//     Set properties using MapProperties which you can use to recompose the map
    val mapProperties = MapProperties(
        // Only enable if user has accepted location permissions.
        isMyLocationEnabled = state.lastKnownLocation != null,
    )
    val cameraPositionState = rememberCameraPositionState()

    val uiSettings = remember {
        MapUiSettings(myLocationButtonEnabled = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.mosque_green_background))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(668.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(10.dp))
            ,
        ) {
            val mosques = listOf(
                MarkerInfo("Mosque El Fath Amersfoort", LatLng(52.16549249236177, 5.401743050074181)),
                MarkerInfo("Mosque Mevlana", LatLng(52.16269492247719, 5.391944781662562)),
                MarkerInfo("Rahman Mosque Amersfoort", LatLng(52.1557615891582, 5.40925520035691)),
                MarkerInfo("Mosque Tawhid Amersfoort", LatLng(52.13965277965032, 5.381358244609819)),

                MarkerInfo("Mosque Al-Kabir", LatLng(52.35576081528535, 4.908481468264659)),
                MarkerInfo("Mosque Badr", LatLng(52.38716911949453, 4.846218491644835)),
                MarkerInfo("Mosque El Mouhssininne", LatLng(52.38517560542569, 4.913176709124514)),
                MarkerInfo("The Blue Mosque", LatLng(52.350758850912186, 4.8334316215487725)),
            )

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = mapProperties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState
            ) {
                mosques.forEach { markerInfo ->
                    Marker(
                        state = MarkerState(position = markerInfo.latLng),
                        title = markerInfo.title,
                        snippet = markerInfo.latLng.toString()
                    )
                }
            }
        }
    }
}

private suspend fun CameraPositionState.centerOnLocation(
    location: Location
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        LatLng(location.latitude, location.longitude),
        15f
    ),
)