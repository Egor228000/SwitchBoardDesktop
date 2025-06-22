package org.example.project

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.navigation.compose.rememberNavController
import desktopswitchboard.composeapp.generated.resources.Res
import desktopswitchboard.composeapp.generated.resources.compose_multiplatform
import org.example.project.navhost.NavigationHost
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.skia.Drawable

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SwitchBoardExe",
        icon = painterResource(Res.drawable.compose_multiplatform)
    ) {
        val navigation = rememberNavController()
        NavigationHost(navigation)
    }
}