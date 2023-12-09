package com.example.salarycalculatorjpc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.salarycalculatorjpc.ui.theme.SalaryCalculatorJPCTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SalaryCalculatorJPCTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "okno1"
                    ) {
                        composable("okno1") {
                            Okno1 {
                                Bundle().apply {
                                    putFloat("salary", it)}
                                navController.navigate("okno2/$it")
                            }
                        }
                            composable(
                            "okno2/{salary}",
                            arguments = listOf(navArgument("salary") { type = NavType.FloatType })
                        ) { backStackEntry ->
                            val salary = backStackEntry.arguments?.getFloat("salary") ?: 0.0f
                            Okno2(salary = salary.toDouble()) {
                                navController.navigateUp()
                            }
                        }
                    }
                }
            }
        }
    }
}