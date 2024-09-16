package edu.ucne.prioridadlh.presentacion.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.prioridadlh.MainActivity
import edu.ucne.prioridadlh.NavigationItem
import edu.ucne.prioridadlh.data.local.database.PrioridadesDb
import edu.ucne.prioridadlh.presentacion.propiedades.PrioridadListSc
import edu.ucne.prioridadlh.presentacion.propiedades.PrioridadScreen
import edu.ucne.prioridadlh.presentacion.ticket.TicketListScreen
import edu.ucne.prioridadlh.presentacion.ticket.TicketScreen
import kotlinx.coroutines.launch

@Composable
fun prioridadlhNavHost(
    navHostController: NavHostController,
    prioridadesDb: PrioridadesDb,
    items: List<NavigationItem>
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val lifecycleOwner = LocalLifecycleOwner.current
    val prioridadDao = remember { prioridadesDb.prioridadesDao() }
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    val prioridadList by prioridadDao.getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )


    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "  Menu",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(item.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            if (item.route == MainActivity.Route.PRIORIDAD)
                                navHostController.navigate(Screen.PrioridadesList)
                            else if (item.route == MainActivity.Route.TICKET)
                                navHostController.navigate(Screen.TicketList)
                            selectedItemIndex = index
                            scope.launch { drawerState.close() }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    )
    {
        NavHost(
            navController = navHostController,
            startDestination = Screen.PrioridadesList
        ) {


            composable<Screen.PrioridadesList> {
                PrioridadListSc(
                    drawerState = drawerState,
                    scope = scope,
                    onAddPrioridad = {
                        navHostController.navigate(Screen.Prioridad(0))
                    },
                    onPrioridadClick = { prioridadId ->
                        navHostController.navigate(Screen.Prioridad(prioridadId = prioridadId))
                    }
                )
            }
            composable<Screen.Prioridad> { argumento ->
                val id = argumento.toRoute<Screen.Prioridad>().prioridadId

                PrioridadScreen(
                    prioridadId = id,
                    goPrioridadList = {
                        navHostController.navigate(
                            Screen.Prioridad
                        )
                    }
                )
            }
            composable<Screen.TicketList> {
                TicketListScreen(
                    drawerState = drawerState,
                    scope = scope,
                    onAddTicket = {
                        navHostController.navigate(Screen.Ticket(0))
                    },
                    onTicketClick = { ticketId ->
                        navHostController.navigate(Screen.Ticket(ticketId = ticketId))
                    }
                )
            }
            composable<Screen.Ticket> { argumento ->
                val ticketId = argumento.toRoute<Screen.Ticket>().ticketId

                TicketScreen(
                    ticketId = ticketId,
                    goTicketList = {
                        navHostController.navigate(
                            Screen.TicketList
                        )
                    }
                )
            }
        }
    }
}
