package com.zhaojun.common.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SimpleFlag(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier.size(width = 90.dp, height = 60.dp)
    ) {
        val stripeHeight = size.height / 3

        drawRect(
            color = Color.Red,
            topLeft = Offset(0f, 0f),
            size = Size(size.width, stripeHeight)
        )

        drawRect(
            color = Color.White,
            topLeft = Offset(0f, stripeHeight),
            size = Size(size.width, stripeHeight)
        )

        drawRect(
            color = Color.Blue,
            topLeft = Offset(0f, stripeHeight * 2),
            size = Size(size.width, stripeHeight)
        )
    }
}