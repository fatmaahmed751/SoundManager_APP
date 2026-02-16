package com.example.secondapp.home

import BottomMenuContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.secondapp.Feature
import com.example.secondapp.R.drawable
import com.example.secondapp.R.raw
import com.example.secondapp.ui.theme.AquaBlue
import com.example.secondapp.ui.theme.Beige1
import com.example.secondapp.ui.theme.Beige2
import com.example.secondapp.ui.theme.Beige3
import com.example.secondapp.ui.theme.BlueViolet1
import com.example.secondapp.ui.theme.BlueViolet2
import com.example.secondapp.ui.theme.BlueViolet3
import com.example.secondapp.ui.theme.ButtonBlue
import com.example.secondapp.ui.theme.DarkerButtonBlue
import com.example.secondapp.ui.theme.DeepBlue
import com.example.secondapp.ui.theme.LightGreen1
import com.example.secondapp.ui.theme.LightGreen2
import com.example.secondapp.ui.theme.LightGreen3
import com.example.secondapp.ui.theme.OrangeYellow2
import com.example.secondapp.ui.theme.OrangeYellow3
import com.example.secondapp.ui.theme.SoundManager
import com.example.secondapp.ui.theme.TextWhite
import standardQuadFromTo


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlue)
    ) {
        Column()
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)

            )
            Greeting()
            ChipSection(listOf("Sweet Sleep", "Insomnia", "Depression"))
            MeditationSection()
            FeatureSection(
                features = listOf(
                    Feature(
                        title = "Sleep meditation",
                        drawable.ic_headphone,
                        BlueViolet3,
                        BlueViolet2,
                        lightColor = BlueViolet1,
                        raw.birds
                    ),
                    Feature(
                        title = "Tips for sleeping",
                        drawable.ic_videocam,
                        LightGreen1,
                        LightGreen2,
                        LightGreen3,
                        raw.insect
                    ),
                    Feature(
                        title = "Night island",
                        drawable.ic_headphone,
                        OrangeYellow3,
                        OrangeYellow2,
                        OrangeYellow2,
                        raw.intro
                    ),
                    Feature(
                        title = "Calming sounds",
                        drawable.ic_headphone,
                        Beige1,
                        Beige2,
                        Beige3,
                        raw.rain
                    )
                ),
                onFeatureClick = { feature ->
                    val encodedTitle = java.net.URLEncoder.encode(feature.title, "UTF-8")
                    navController.navigate("details/$encodedTitle/${feature.soundResId}")
                }
            )
        }
        BottomMenu(
            items = listOf(
                BottomMenuContent("Home", drawable.ic_home, "home"),
                BottomMenuContent("Meditate", drawable.ic_bubble, "meditate"),
                BottomMenuContent("Sleep", drawable.ic_moon, "sleep"),
                BottomMenuContent("Music", drawable.ic_music, "music"),
                BottomMenuContent("Profile", drawable.ic_profile, "profile"),
            ), modifier = Modifier.align(Alignment.BottomCenter)
        )


    }
}

@Composable
fun MeditationSection(
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        SoundManager.load(context, raw.rain)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xffD4C4D0),
                        Color(0xffCD8BBA),
                    )
                )
            )
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = "Daily Thought",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
            )
            Text(
                text = "Meditation • 3-10 min",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xffD4C4D0))
                .clickable {
                    SoundManager.play(raw.rain)
                }
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = drawable.ic_play),
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
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
        Icon(
            painter = painterResource(id = drawable.ic_search),
            contentDescription = "Search",
            tint = Color(0xff2D3748),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun BottomMenuItem(
    item: BottomMenuContent,
    isSelected: Boolean = false,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    onItemClick: () -> Unit

) {
    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            onItemClick()
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (isSelected) Brush.verticalGradient(
                        colors = listOf(
                            Color(0xffA8B4C4),
                            Color(0xff6785AD),
                        )
                    )
                    else
                        Brush.linearGradient(
                            colors = listOf(Color.Transparent, Color.Transparent)
                        )
                )

                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = item.title,
                tint = if (isSelected) activeTextColor else inactiveTextColor,
                modifier = Modifier.size(14.dp)
            )
        }
        Text(
            text = item.title,
            fontSize = 12.sp,
            color = AquaBlue
            //  if (isSelected) activeTextColor else inactiveTextColor
        )
    }
}

@Composable
fun BottomMenu(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    initialSelectedItemIndex: Int = 0
) {
    var selectedItemIndex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            //.background(DeepBlue)
            .padding(
                top = 10.dp, bottom = 40.dp,
                start = 15.dp, end = 15.dp
            )
    ) {
        items.forEachIndexed { index, item ->
            BottomMenuItem(
                item = item,
                isSelected = index == selectedItemIndex,
                activeHighlightColor = activeHighlightColor,
                activeTextColor = activeTextColor,
                inactiveTextColor = inactiveTextColor
            ) {
                selectedItemIndex = index
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun FeatureSection(features: List<Feature>, onFeatureClick: (Feature) -> Unit) {
    val context = LocalContext.current

    LaunchedEffect(features) {
        features.forEach {
            SoundManager.load(context, it.soundResId)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Text(
            text = "Features",
            fontSize = 25.sp,
            modifier = Modifier.padding(start = 16.dp, top = 2.dp, bottom = 8.dp),
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xff2D3748)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(features.size) {
                NewFeatureItem(feature = features[it], onFeatureClick = onFeatureClick)
            }


        }
    }
}


@Composable
fun NewFeatureItem(
    feature: Feature,
    onFeatureClick: (Feature) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf((feature.lightColor), feature.darkColor)
                )
            )
        //       (feature.darkColor)
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        // Wave path
        val point1 = Offset(0f, height * 0.5f)
        val point2 = Offset(width * 0.4f, height * 0.4f)
        val point3 = Offset(width * 0.7f, height * 0.7f)
        val point4 = Offset(width * 1.4f, height * 0.4f)


        val coloredPath = Path().apply {
            moveTo(point1.x, point1.y)
            standardQuadFromTo(point1, point2)
            standardQuadFromTo(point2, point3)
            standardQuadFromTo(point3, point4)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawPath(
                path = coloredPath,
                color = feature.lightColor.copy(alpha = 0.5f)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
        ) {
            Text(
                text = feature.title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontSize = 18.sp,
                lineHeight = 22.sp,
                maxLines = 2,
                modifier = Modifier.align(Alignment.TopStart)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = feature.iconId),
                    contentDescription = feature.title,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 6.dp,
                            shape = RoundedCornerShape(14.dp),
                            spotColor = Color.Black.copy(alpha = 0.25f)
                        )
                        .clip(RoundedCornerShape(14.dp))
                        .background(feature.lightColor)
                        .clickable { onFeatureClick(feature) }
                        .padding(vertical = 6.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = "Start",
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }
}


@Composable
fun ChipSection(
    chips: List<String>
) {
    var selectChip by remember {
        mutableStateOf(chips[0])
    }
    LazyRow {
        items(chips.size) {
            Box(

                modifier = Modifier
                    .padding(
                        start = 15.dp, top = 15.dp, bottom = 8.dp
                    )
                    .clickable {
                        selectChip = chips[it]
                    }
                    .clip(
                        shape = RoundedCornerShape(13421800.dp)
                    )
                    .background(
                        if (selectChip == chips[it]) ButtonBlue
                        else DarkerButtonBlue
                    )
                    .padding(12.dp)
            ) {

                Text(
                    text = chips[it],
                    color = if (selectChip == chips[it]) TextWhite
                    else Color(0xff5D6774),
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }
}
