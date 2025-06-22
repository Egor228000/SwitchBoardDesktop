package org.example.project.navhost

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import desktopswitchboard.composeapp.generated.resources.Res
import desktopswitchboard.composeapp.generated.resources.edit_1_svgrepo_com
import desktopswitchboard.composeapp.generated.resources.folder_add_svgrepo_com
import desktopswitchboard.composeapp.generated.resources.folder_del_svgrepo_com
import kotlinx.coroutines.launch
import org.example.project.Screen
import org.example.project.screens.AddScreen
import org.example.project.screens.DeleteScreen
import org.example.project.viewmodel.AddViewModel
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost(navigation: NavHostController) {
    val navBackStackEntry by navigation.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val addViewModel = remember { AddViewModel() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        when (currentRoute) {
                            Screen.Add.route -> {
                                "Добавление товара"
                            }

                            Screen.Delete.route -> {
                                "Удаление товара"
                            }

                            else -> {
                                ""
                            }
                        }
                    )
                }
            )
        },
    ) { paddingValues ->


        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val items =
            listOf(
                painterResource(Res.drawable.folder_add_svgrepo_com),
                painterResource(Res.drawable.folder_del_svgrepo_com),
                painterResource(Res.drawable.edit_1_svgrepo_com),

            )
        val label = listOf(
            "Добавить товар",
            "Удалить товар",
            "Редактировать товар"
        )
        val selectedItem = remember { mutableIntStateOf(0) }
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(drawerState) {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        Spacer(Modifier.height(12.dp))
                        items.forEachIndexed { index, item ->
                            NavigationDrawerItem(
                                icon = {
                                    Icon(
                                        item,
                                        contentDescription = null,
                                        modifier = Modifier.size(80.dp),
                                        tint = Color.Black
                                    )
                                },
                                label = {
                                    Text(label[index])
                                },
                                selected = index == selectedItem.value,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    selectedItem.value = index
                                    if (index == 0)
                                        navigation.navigate(Screen.Add.route)
                                    else
                                        navigation.navigate(Screen.Delete.route)
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                        }
                    }
                }
            },
            modifier = Modifier
                .padding(paddingValues),
            content = {
                NavHost(
                    navController = navigation,
                    startDestination = Screen.Add.route,

                ) {

                    composable(Screen.Add.route) {
                        AddScreen(addViewModel)
                    }
                    composable(Screen.Delete.route) {
                        DeleteScreen()
                    }
                }
            }
        )
    }
}