package edu.ucne.prioridadlh.presentacion.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.prioridadlh.data.local.database.PrioridadesDb
import edu.ucne.prioridadlh.presentacion.propiedades.PrioridadListSc
import edu.ucne.prioridadlh.presentacion.propiedades.PrioridadScreen


@Composable
fun prioridadlhNavHost(
    navHostController: NavHostController,
    prioridadesDb: PrioridadesDb
){
    val lifecycleOwner = LocalLifecycleOwner.current
    val prioridadDao = remember { prioridadesDb.prioridadesDao() }

    val prioridadList by prioridadDao.getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )

    NavHost(
        navController = navHostController,
        startDestination = Screen.PrioridadesList
    )
    {
        composable<Screen.PrioridadesList> {
            PrioridadListSc(
                prioridadList=  prioridadList,
                goToPrioridad = {
                    navHostController.navigate(Screen.Prioridad(it))
                },
                onAddPrioridad = { navHostController.navigate(Screen.Prioridad(0))
                }
            )
        }

        composable<Screen.Prioridad> {
            val args = it.toRoute<Screen.Prioridad>()
            PrioridadScreen(
                goPrioridadesList = { navHostController.navigate(Screen.PrioridadesList)
                },
                prioridadesDb = prioridadesDb
            )

        }

    }
}