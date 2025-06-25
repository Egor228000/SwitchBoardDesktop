package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.navigation.compose.rememberNavController
import desktopswitchboard.composeapp.generated.resources.Res
import desktopswitchboard.composeapp.generated.resources.icon_img
import org.example.project.navhost.NavigationHost
import org.jetbrains.compose.resources.painterResource
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SwitchBoardExe",
        icon = painterResource(Res.drawable.icon_img)
    ) {
        window.minimumSize = Dimension(700, 600)
        val navigation = rememberNavController()
        NavigationHost(navigation)
    }
}