package org.example.project.navhost

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import desktopswitchboard.composeapp.generated.resources.*
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

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch { drawerState.open() }

                        }
                    ) {
                        Icon(
                            painterResource(
                                Res.drawable.hamburger_menu_svgrepo_com,
                            ),
                            null

                        )
                    }

                }
            )
        },
    ) { paddingValues ->



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