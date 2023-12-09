package com.example.salarycalculatorjpc


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salarycalculatorjpc.ui.theme.SalaryCalculatorJPCTheme
import androidx.compose.ui.Alignment

sealed class JobType {
    object Developer : JobType()
    object Designer : JobType()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Okno1(onSalaryCalculated: (Float) -> Unit) {
    var baseSalary by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var efficiency by remember { mutableStateOf("") }
    var jobType by remember { mutableStateOf<JobType>(JobType.Developer) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = baseSalary,
            onValueChange = { baseSalary = it },
            placeholder = { Text("Base Salary") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 9.dp)
        )

        OutlinedTextField(
            value = experience,
            onValueChange = { experience = it },
            placeholder = { Text("Experience") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 9.dp)
        )

        when (jobType) {
            JobType.Developer -> {
                // No additional fields
            }
            JobType.Designer -> {
                OutlinedTextField(
                    value = efficiency,
                    onValueChange = { efficiency = it },
                    label = { Text("Efficiency (0-1)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }
        }

        // Developer option

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 9.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Row(verticalAlignment = Alignment.CenterVertically){
                RadioButton(
                    selected = jobType == JobType.Developer,
                    onClick = { jobType = JobType.Developer },
                )
                Text("Developer")
            }
        }
            // Designer option

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 9.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically
                ){
            RadioButton(
                selected = jobType == JobType.Designer,
                onClick = { jobType = JobType.Designer },

            )
            Text("Designer") }
        }
        Spacer(modifier = Modifier.height(17.dp))
        Button(
            onClick = {
                // Perform salary calculation
                val calculatedSalary = calculateSalary(jobType, baseSalary.toFloat(), experience.toInt(), efficiency.toFloatOrNull())
                onSalaryCalculated(calculatedSalary)
            },
            modifier = Modifier
                .height(40.dp),
            enabled = isCalculateButtonEnabled(jobType, baseSalary, experience, efficiency)
        ) {
            Text("Get Salary")
        }
    }
}


// Function to calculate salary based on input values
private fun calculateSalary(jobType: JobType, baseSalary: Float, experience: Int, efficiency: Float?): Float {
    return when (jobType) {
        is JobType.Developer -> {
            when {
                experience > 5 -> baseSalary * 1.2f + 500
                experience in 2..4 -> baseSalary + 200
                else -> baseSalary
            }
        }
        is JobType.Designer -> {
            requireNotNull(efficiency) { "Efficiency must not be null for Designer" }
            require(efficiency in 0f..1f) { "Efficiency must be between 0 and 1 for Designer" }

            // Adding the fixed amount for the specified range of experience
            val baseSalaryWithBonus = when {
                experience > 5 -> baseSalary * 1.2f + 500
                experience in 2..4 -> baseSalary + 200
                else -> baseSalary
            }

            baseSalaryWithBonus * efficiency
        }
    }
}

// Function to check if the "Get Salary" button should be enabled
private fun isCalculateButtonEnabled(jobType: JobType, baseSalary: String, experience: String, efficiency: String): Boolean {
    return when (jobType) {
        is JobType.Developer -> true
        is JobType.Designer -> {
            baseSalary.isNotBlank() && experience.isNotBlank() && efficiency.isNotBlank() && efficiency.toFloatOrNull()!! in 0f..1f
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Screen1Preview() {
    SalaryCalculatorJPCTheme {
        Okno1(onSalaryCalculated = {})
    }
}