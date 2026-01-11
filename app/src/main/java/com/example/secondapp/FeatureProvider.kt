package com.example.secondapp

import com.example.secondapp.R.drawable
import com.example.secondapp.R.raw
import com.example.secondapp.ui.theme.Beige1
import com.example.secondapp.ui.theme.Beige2
import com.example.secondapp.ui.theme.Beige3
import com.example.secondapp.ui.theme.BlueViolet1
import com.example.secondapp.ui.theme.BlueViolet2
import com.example.secondapp.ui.theme.BlueViolet3
import com.example.secondapp.ui.theme.LightGreen1
import com.example.secondapp.ui.theme.LightGreen2
import com.example.secondapp.ui.theme.LightGreen3
import com.example.secondapp.ui.theme.OrangeYellow1
import com.example.secondapp.ui.theme.OrangeYellow2
import com.example.secondapp.ui.theme.OrangeYellow3

object FeatureProvider {
    val features = listOf(
        Feature(
            title = "Sleep meditation",
            drawable.ic_headphone,
            BlueViolet1,
            BlueViolet2,
            BlueViolet3,
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
            OrangeYellow1,
            OrangeYellow2,
            OrangeYellow3,
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
    )
}
