package com.dsi.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.dsi.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme(darkTheme = false) {
                val snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
                val scope: CoroutineScope = rememberCoroutineScope()

                Scaffold(
                    topBar = {
                        AppBar(
                            onShowSnackBar = {
                                message -> scope.launch {
                                    snackBarHostState.showSnackbar(message)
                                }
                            }
                        )
                    },
                    snackbarHost = { SnackbarHost(snackBarHostState) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                ) { innerPadding ->
                    // Main screen content goes here
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        Text("Content goes here")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AppBar(onShowSnackBar: (String) -> Unit = {}) {
    TopAppBar(
        title = {
            Text(text = "Home")
        },
        actions = {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Notifications",
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clickable {
                        onShowSnackBar("Notification clicked!")
                    }
            )
        }
    )
}