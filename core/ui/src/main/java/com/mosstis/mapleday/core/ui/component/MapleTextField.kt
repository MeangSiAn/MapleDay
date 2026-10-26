package com.mosstis.mapleday.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mosstis.mapleday.core.designsystem.theme.MapleDimens
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType

private val FieldMinHeight = 46.dp
private val FieldRadius = 14.dp
private val FieldBorder = 1.5.dp
private val FieldHorizontalPadding = 12.dp
private val FieldContentGap = 8.dp
private val LabelGap = 5.dp

/**
 * 입력 폼 (DESIGN.md 5장 `FormField`, S03·S04·P04 공용).
 * 라벨 11.5sp 굵게 `Ink2` / 입력칸 46dp 이상·모서리 14dp·`BoxBg` + 1.5dp `Edge`, 포커스 시 `Orange` / 오류 11.5sp `Bad`.
 *
 * [modifier]는 라벨·오류를 포함한 전체에, [fieldModifier]는 입력칸(BasicTextField)에만 적용된다.
 */
@Composable
fun MapleTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    fieldModifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    errorText: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    textStyle: TextStyle = MapleTheme.typography.body.copy(fontSize = 13.5.sp),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailing: (@Composable () -> Unit)? = null,
) {
    val colors = MapleTheme.colors
    val shape = RoundedCornerShape(FieldRadius)
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Column(modifier = modifier) {
        if (label != null) {
            Text(
                text = label,
                style = MapleTheme.typography.meta.copy(fontWeight = FontWeight.Bold),
                color = colors.ink2,
                modifier = Modifier.padding(bottom = LabelGap),
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = fieldModifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = singleLine,
            textStyle = textStyle.copy(color = colors.ink),
            cursorBrush = SolidColor(colors.orange),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = FieldMinHeight)
                        .background(colors.boxBg, shape)
                        .border(FieldBorder, if (isFocused) colors.orange else colors.edge, shape)
                        .padding(horizontal = FieldHorizontalPadding),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(FieldContentGap),
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty() && placeholder != null) {
                            Text(text = placeholder, style = textStyle, color = colors.ink3)
                        }
                        innerTextField()
                    }
                    trailing?.invoke()
                }
            },
        )

        if (errorText != null) {
            Text(
                text = errorText,
                style = MapleTheme.typography.meta,
                color = colors.bad,
                modifier = Modifier.padding(top = LabelGap),
            )
        }
    }
}

@Composable
private fun TextFieldSample(theme: MapleThemeType) {
    MapleTheme(theme = theme) {
        var name by remember { mutableStateOf("진 힐라 파티") }
        Column(
            modifier = Modifier
                .background(MapleTheme.colors.card)
                .padding(MapleDimens.CardPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            MapleTextField(value = "", onValueChange = {}, label = "이름", placeholder = "파티 이름")
            MapleTextField(value = name, onValueChange = { name = it }, label = "이름")
            MapleTextField(value = "abc", onValueChange = {}, label = "이름", errorText = "다시 확인해 주세요.")
        }
    }
}

@Preview(name = "TextField · 블랙", widthDp = 360)
@Composable
private fun TextFieldBlackPreview() = TextFieldSample(MapleThemeType.BLACK)

@Preview(name = "TextField · 베이지", widthDp = 360)
@Composable
private fun TextFieldBeigePreview() = TextFieldSample(MapleThemeType.BEIGE)
