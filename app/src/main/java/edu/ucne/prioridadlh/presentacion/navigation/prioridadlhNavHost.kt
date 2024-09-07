//package edu.ucne.prioridadlh.presentacion.navigation
//
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.compose.LocalLifecycleOwner
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import edu.ucne.prioridadlh.data.local.database.PrioridadesDb
//import edu.ucne.prioridadlh.presentacion.propiedades.PrioridadListSc
//import edu.ucne.prioridadlh.presentacion.propiedades.PrioridadScreen
//
//
//@Composable
//fun prioridadlhNavHost(
//    navHostController: NavHostController,
//    prioridadesDb: PrioridadesDb
//){
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val prioridadDao = remember { prioridadesDb.prioridadesDao() }
//
//    val prioridadList by prioridadDao.getAll()
//        .collectAsStateWithLifecycle(
//            initialValue = emptyList(),
//            lifecycleOwner = lifecycleOwner,
//            minActiveState = Lifecycle.State.STARTED
//        )
//
//    NavHost(
//        navController = navHostController,
//        startDestination = Screen.PrioridadesList
//    )
//    {
//
//
//        composable<Screen.PrioridadesList> {
//            PrioridadListSc(
//                prioridadList=  prioridadList,
//                goToPrioridad = {
//                    navHostController.navigate(Screen.Prioridad(it))
//                },
//                onAddPrioridad = { navHostController.navigate(Screen.Prioridad(0))
//                },
//                onEditPrioridad = {prioridades -> navHostController.navigate(Screen.Prioridad(prioridades.PrioridadId))},
//                onDeletePrioridad = {}
//            )
//        }
//
//        composable<Screen.Prioridad> {
//            PrioridadScreen(
//                goPrioridadesList = { navHostController.navigate(Screen.PrioridadesList)
//                },
//                prioridadesDb = prioridadesDb,
//                prioridadId = {prioridades -> navHostController.navigate(Screen.Prioridad(prioridades.PrioridadId))}
//            )
//
//        }
//
//    }
//}

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
import edu.ucne.prioridadlh.data.local.database.PrioridadesDb
import edu.ucne.prioridadlh.presentacion.propiedades.PrioridadListSc
import edu.ucne.prioridadlh.presentacion.propiedades.PrioridadScreen

@Composable
fun prioridadlhNavHost(
    navHostController: NavHostController,
    prioridadesDb: PrioridadesDb
) {
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
        startDestination = Screen.PrioridadesList.route
    ) {
        composable(Screen.PrioridadesList.route) {
            PrioridadListSc(
                prioridadList = prioridadList,
                onAddPrioridad = {
                    navHostController.navigate(Screen.Prioridad.createRoute(0))
                },
                prioridadesDb = prioridadesDb,
                onEditPrioridad = { id ->
                    navHostController.navigate(Screen.Prioridad.createRoute(id))
                },
            )
        }

        composable(
            route = "prioridad/{prioridadId}"
        ) { backStackEntry ->
            val prioridadId = backStackEntry.arguments?.getString("prioridadId")?.toIntOrNull() ?: 0
            PrioridadScreen(
                goPrioridadesList = {
                    navHostController.navigate(Screen.PrioridadesList.route)
                },
                prioridadesDb = prioridadesDb,
                prioridadId = prioridadId
            )
        }
    }
}