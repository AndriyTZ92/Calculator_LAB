package com.example.salarycalculatorjpc


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salarycalculatorjpc.ui.theme.SalaryCalculatorJPCTheme


@Composable
fun Okno2(salary: Double, onReturn: () -> Unit) {
    var showTextResult by remember { mutableStateOf(true) }

    val imageResource = if (!showTextResult) {
        R.drawable.bagofmoney // Replace with your actual image resource
    } else {
        0 // 0 represents no image or a placeholder
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .clickable {
                    showTextResult = !showTextResult
                }
        ) {
            if (showTextResult) {
                Text(
                    text = String.format("%.2f", salary)
                )
            } else {
                val resultText = when {
                    salary <= 1000.0 -> "Jun"
                    salary <= 2000.0 -> "Middl"
                    salary <= 3000.0 -> "Sen"
                    else -> "liberated"
                }
                Text(
                    text = resultText
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!showTextResult) {
            // Making the image clickable with the same action as resultText
            Box(
                modifier = Modifier
                    .clickable {
                        showTextResult = !showTextResult
                    }
            ) {
                Image(
                    painter = painterResource(imageResource),
                    contentDescription = null,
                    modifier = Modifier.size(72.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onReturn,
            modifier = Modifier
                .height(40.dp)
        ) {
            Text("Return")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Screen2Preview() {
    SalaryCalculatorJPCTheme {
        Okno2(salary = 1500.0) {}
    }
}