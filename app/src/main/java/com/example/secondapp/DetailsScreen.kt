package com.example.secondapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.secondapp.home.NewFeatureItem
import com.example.secondapp.ui.theme.ButtonBlue
import com.example.secondapp.ui.theme.DeepBlue
import com.example.secondapp.ui.theme.SoundManager
import com.example.secondapp.ui.theme.TextWhite

@Composable
fun DetailsScreen(title: String?, soundResId: Int?) {
    val context = LocalContext.current
    val decodedTitle = title?.let { java.net.URLDecoder.decode(it, "UTF-8") }
    val feature = FeatureProvider.features.find { it.title == decodedTitle }

    // Load sound when screen opens
    LaunchedEffect(soundResId) {
        soundResId?.let { SoundManager.load(context, it) }
    }

    var isPlaying by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlue)
            .padding(
                start = 6.dp,
                end = 6.dp,
                top = 32.dp
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            //  verticalArrangement = Arrangement.Center,
            // modifier = Modifier.fillMaxSize()
        ) {
            Greeting()
            feature?.let {
                // Reuse the FeatureItem card UI
                Box(
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    NewFeatureItem(feature = it, onFeatureClick = {})
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Playback Controls
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Rewind 10s
                Button(
                    onClick = {
                        soundResId?.let { SoundManager.rewind(it, 10000) }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = feature?.lightColor ?: ButtonBlue
                    ),
                    shape = CircleShape,
                    modifier = Modifier.size(70.dp)
                ) {
                    Text("-10", color = TextWhite)
                }

                // Play/Pause
                Button(
                    onClick = {
                        soundResId?.let {
                            if (isPlaying) {
                                SoundManager.pause(it)
                            } else {
                                SoundManager.resume(it)
                            }
                            isPlaying = !isPlaying
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = feature?.lightColor ?: ButtonBlue
                    ),
                    shape = CircleShape,
                    modifier = Modifier.size(80.dp)
                ) {
                    Icon(
                        painter = painterResource(id = if (isPlaying) R.drawable.is_pause else R.drawable.ic_play),
                        contentDescription = "Play/Pause",
                        tint = TextWhite,
                        modifier = Modifier.size(30.dp)
                    )
                }

                // Forward 10s
                Button(
                    onClick = {
                        soundResId?.let { SoundManager.forward(it, 10000) }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = feature?.lightColor ?: ButtonBlue
                    ),
                    shape = CircleShape,
                    modifier = Modifier.size(72.dp)
                ) {
                    Text("+10", color = TextWhite)
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String = "Dear"
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Listening Our, $name",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xff5D6774),
                fontSize = 20.sp
            )
            Text(
                text = "we wish you Good Day",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xff5D6774),
                fontSize = 18.sp

            )
        }


    }
}
