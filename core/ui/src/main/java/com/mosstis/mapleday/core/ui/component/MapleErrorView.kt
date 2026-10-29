package com.mosstis.mapleday.core.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme

@Composable
fun MapleErrorView(
    modifier: Modifier = Modifier.fillMaxSize(),
    message : String
) {
    Text(
        modifier = modifier,
        text = message,
        color = MapleTheme.colors.ink,
        textAlign = TextAlign.Center,
    )
}


@Preview(name = "토큰 에러")
@Composable
fun MapleErrorViewToken(

) {
//        MapleErrorView(
//
//        )
}
