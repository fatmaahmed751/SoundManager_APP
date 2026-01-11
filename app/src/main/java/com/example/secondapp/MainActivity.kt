package com.example.secondapp

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.secondapp.home.isPermanentlyDenied
import com.example.secondapp.navigation.AppNavigation
import com.example.secondapp.ui.theme.MeditationUIYouTubeTheme
import com.example.secondapp.ui.theme.SoundManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //MainActivity → Navigation() → Screens
        //  SoundManager.initialize(this)
        setContent {
            //  SoundManager.initialize(this)
            MeditationUIYouTubeTheme {
                AppNavigation()
//            HomeScreen(navController)
//                //   MyScaffold()
//            }
                //   }
                //   }
                //  PermissionHandlingComposeTheme()
            }
        }
    }


//            MeditationUIYouTubeTheme {
//                AppNavigation()
//                //  HomeScreen(navController)
//                //   MyScaffold()
//            }
    //   }
    //   }

    override fun onDestroy() {
        super.onDestroy()
        SoundManager.release()
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionHandlingComposeTheme() {

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA

        )
    )
    val lifecycle = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycle, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                permissionState.launchMultiplePermissionRequest()
            }
        }
        lifecycle.lifecycle.addObserver(observer)
        onDispose {
            lifecycle.lifecycle.removeObserver(observer)
        }
    }
    )
    Column(
        modifier = Modifier.fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        permissionState.permissions.forEach { perm ->
            when (perm.permission) {
                Manifest.permission.CAMERA -> {
                    when {
                        perm.status.isGranted -> {
                            Text(text = "Camera permission granted")

                        }

                        perm.status.shouldShowRationale -> {
                            Text(text = "Camera permission is needed")
                        }

                        perm.isPermanentlyDenied() -> {
                            Text(text = "Camera permission was permanently denied")
                        }
                    }
                }

                Manifest.permission.RECORD_AUDIO -> {
                    when {
                        perm.status.isGranted -> {
                            Text("Record audio permission granted")
                        }

                        perm.status.shouldShowRationale -> {
                            Text("Record audio permission is needed")
                        }

                        perm.isPermanentlyDenied() -> {
                            Text("Record audio permission was permanently denied")
                        }
                    }
                }

            }


            Text(text = "Camera permission denied")
            Text(text = "Record audio permission denied")
        }
    }

//
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun MyScaffold() {
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//    var isDismiss by remember { mutableStateOf(false) }
//    Box(modifier = Modifier.fillMaxSize()) {
//       // MyImage()
//        ModalNavigationDrawer(
//            //modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing), // <-- ADD THIS MODIFIER
//            drawerState = drawerState,
//            drawerContent = {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(end = 24.dp)
//                        .background(color = Color.White)
//                ) {
//                    Text(
//                        "Drawer Item 1",
//                        modifier = Modifier.padding(start = 16.dp)
//                    )
//                    Text(
//                        "Drawer Item 2",
//                        modifier = Modifier.scrollable(
//                            state = rememberScrollState(),
//                            orientation = Orientation.Vertical,
//                        )
//                    )
//                }
//            }
//        ) {
//            Scaffold(
//                modifier = Modifier.windowInsetsPadding(
//                    WindowInsets(left = 0.dp, top = 0.dp, right = 0.dp, bottom = 0.dp)
//                ),
//                containerColor = Color.Transparent,
//                topBar = {
//                    MyAppBar(
//                        drawerState = drawerState,
//                        scope = scope
//                    )
//                }
//            ) { innerPadding ->
//                ConstraintLayout(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
//                    val (title, button) = createRefs()
//
//                    Text(
//                        "Title",
//                        modifier = Modifier.constrainAs(title) {
//                            top.linkTo(parent.top, margin = 16.dp)
//                            start.linkTo(parent.start, margin = 16.dp)
//                        }
//                    )
//
//                    Button(
//                        onClick = {},
//                        modifier = Modifier.constrainAs(button) {
//                            top.linkTo(title.bottom, margin = 16.dp)
//                            end.linkTo(parent.end, margin = 16.dp)
//                        }
//                    ) {
//                        Text("Click Me")
//                    }
//                }
//            }
//        }
//    }
//}


//                Column(
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier.padding(innerPadding)
//
//                ) {
//                    val color = remember {
//                        mutableStateOf(Color.Yellow)
//                    }
//
//                    ColorBox(
//                        modifier = Modifier.fillMaxWidth().height(200.dp),
//                    ){
//                       color.value = it
//                    }
//                    Box(Modifier.fillMaxWidth().height(200.dp)
//                        .background(color = color.value))
//                    //MyTextField()
//                    Spacer(modifier = Modifier.height(4.dp))
//                  //  MyTextField()
////                    MyButton(
////                        onClick = { isDismiss = true } // Set state to true on click
////                    )
////                    if (isDismiss) {
////                        MYAlertDialog(
////                            // Pass a lambda to set the state back to false
////                            onDismiss = { isDismiss = false }
////                        )
////                    }
////                    ColorBox(
////                        modifier = Modifier.height(200.dp)
////                    )
//
//                }


    @Composable
    fun MyImage() {


    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyAppBar(
        drawerState: DrawerState,
        scope: CoroutineScope
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                ) {
                    Icon( // This is where the original code was cut off
                        Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }
            },
            title = { Text("My App") },
            colors = TopAppBarDefaults.topAppBarColors(
                // 2. Make the container color transparent
                containerColor = Color.Transparent
            )
        )
    }
}

//@Composable
//fun MyTextField() {
//    var text by remember { mutableStateOf("") }
//    OutlinedTextField(
//        value = text,
//        onValueChange = { text = it },
//        label = { Text("Enter text") },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    )
//}
//
@Preview(showBackground = true)
@Composable
fun PreviewMyScaffold() {
    MaterialTheme {
        // MyScaffold()
        AppNavigation()
    }
}

@Composable
fun MyButton() {
    Button(
        onClick = {

        },
        colors = ButtonDefaults.buttonColors(
        )
    )
    {
        Text(text = "Click Me")
    }
}

@Composable

fun MyRadioButton() {
    val radioButtons = listOf(1, 2, 3)
    val selectedValue = remember { mutableStateOf(radioButtons[1]) }
    Column {
        radioButtons.forEach { index ->
            val isSelected = index == selectedValue.value


            RadioButton(
                selected = isSelected,
                onClick = {
                    selectedValue.value = index
                }
            )
        }
    }
}

@Composable
fun MyButton(onClick: () -> Unit) {
    Button(
        onClick = onClick, // Use the passed-in action
        colors = ButtonDefaults.buttonColors()
    ) {
        Text(text = "Show Dialog") // Changed text for clarity
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MYAlertDialog(onDismiss: () -> Unit) {

    AlertDialog(
        title = {
            Text(text = "Alert Dialog Title")
        },
        text = {
            Text(text = "This is the alert's message.")
        },
        onDismissRequest = onDismiss,
        // isDismiss.value = false

        confirmButton = {

            Button(
                onClick = {
                    // You can add logic for the confirm button here
                    onDismiss() // Also dismiss on confirm
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(text = buildAnnotatedString {
                    withStyle(style = MaterialTheme.typography.labelLarge.toSpanStyle()) {
                        append("Confirm")
                    }

                }

                )

            }


        }
    )
}

@Composable
fun ColorBox(
    modifier: Modifier = Modifier,
    updateColor: (Color) -> Unit
) {
//val color = remember {
//    mutableStateOf(
//        Color.Yellow
//    )
//}
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                color = Color.Red,
            )
            .clickable {
                updateColor(
                    Color(
                        Random.nextFloat(),
                        Random.nextFloat(),
                        Random.nextFloat(),
                        1f
                    )
                )
            }
    )
}

