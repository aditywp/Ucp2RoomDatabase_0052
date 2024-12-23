package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.dokter.HomeDokterView
import com.example.ucp2.ui.view.dokter.InsertDokterView
import com.example.ucp2.ui.view.jadwal.HomeJadwalView
import com.example.ucp2.ui.view.jadwal.InsertJadwalView
import com.example.ucp2.ui.view.jadwal.UpdateJadwalView
import com.example.ucp2.ui.view.screen.SplashScreen

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiSplash.route
    ) {
        composable(
            route = DestinasiSplash.route
        ) {
            SplashScreen(
                navigateToHome = {
                    navController.navigate(DestinasiHome.route)
                },
            )
        }

        composable(
            route = DestinasiHome.route
        ) {
            HomeDokterView(

                onAddDokter = {
                    navController.navigate(DestinasiInsert.route)
                },
                onDetailJadwal = {
                    navController.navigate(DestinasiHomeJadwal.route)
                },
                onAddJadwal = {
                    navController.navigate(DestinasiInsertJadwal.route)
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiInsert.route) {
            InsertDokterView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier =modifier

            )
        }
        composable(route = DestinasiInsertJadwal.route) {
            InsertJadwalView(
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
                onBack = {
                    navController.popBackStack()
                },
                onDokter = {  },
                onJadwal = {

                }
            )
        }

        composable(route = DestinasiHomeJadwal.route) {
            HomeJadwalView(
                onBackClick = {
                    navController.popBackStack()
                },
                onAddJadwal = {
                    navController.navigate(DestinasiInsertJadwal.route)
                },
                onDetailJadwal = {
                    navController.navigate(DestinasiDetailJadwal.route)
                },
                onEditJadwal = {
                    navController.navigate("${DestinasiUpdateJadwal.route}/$it")
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiUpdateJadwal.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateJadwal.idJadwal) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateJadwalView(
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
                onBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}