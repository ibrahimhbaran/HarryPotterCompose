package com.play.harrypottercompose.ui.view.detail

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter


@Composable
fun DetailScreen(
    image: String?, name: String?, info: String?
) {
    Column {
        image?.let { showImage(it) }

        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(16.dp))

            name?.let { setTitle(name = it) }

            Spacer(modifier = Modifier.height(16.dp))

            info?.let { setInfo(it) }
        }
    }
}

@Composable
private fun setInfo(info: String) {
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }
    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn(animationSpec = tween(5000, easing = LinearEasing), initialAlpha = 0.1f),
        exit = fadeOut()
    ) {
        Text(text = info, fontFamily = FontFamily.Monospace)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun showImage(image: String) {
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }
    AnimatedVisibility(
        visibleState = state,
        // By Default, `scaleIn` uses the center as its pivot point. When used with a vertical
        // expansion from the vertical center, the content will be growing from the center of
        // the vertically expanding layout.
        enter = scaleIn(
            tween(
                100,
                easing = LinearOutSlowInEasing
            )
        ) + expandVertically(expandFrom = Alignment.CenterVertically),
        // By Default, `scaleOut` uses the center as its pivot point. When used with an
        // ExitTransition that shrinks towards the center, the content will be shrinking both
        // in terms of scale and layout size towards the center.
        exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)

    ) {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        )
    }
}


@Composable
fun setTitle(name: String) {
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }
    val density = LocalDensity.current

    AnimatedVisibility(visibleState = state, enter = slideInVertically {
        // Slide in from 40 dp from the top.
        with(density) { -40.dp.roundToPx() }
    } + expandVertically(
        // Expand from the top.
        expandFrom = Alignment.Top
    ) + fadeIn(
        // Fade in with the initial alpha of 0.3f.
        initialAlpha = 0.3f
    ), exit = slideOutVertically() + shrinkVertically() + fadeOut()) {
        Text(
            text = name,
            fontSize = 30.sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Monospace
        )
    }
}

