package com.mosstis.mapleday.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mosstis.mapleday.core.designsystem.theme.MapleDimens
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme.colors

@Composable
fun MapleBaseTextView(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MapleDimens.CardRadius))
            .background(colors.card)
            .border(MapleDimens.CardBorder, colors.edge, RoundedCornerShape(MapleDimens.CardRadius))
            .padding(MapleDimens.CardPadding)
    ) {
        content()
    }
}

@Preview(name = "MapleBaseTextView · 블랙")
@Composable
private fun DashedDividerBlackPreview() = MapleBaseTextView() {
    Text(
        text = "test",
        style = MapleTheme.typography.cardTitle,
        color = MapleTheme.colors.ink
    )
}