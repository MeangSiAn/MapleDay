package com.mosstis.mapleday.feature.connect.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.password
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.mosstis.mapleday.core.designsystem.theme.MapleDimens
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType
import com.mosstis.mapleday.core.designsystem.theme.numeric
import com.mosstis.mapleday.core.ui.component.MapleTextField
import com.mosstis.mapleday.feature.connect.R

/**
 * S04 NEXON Open API 키 입력칸. 기본은 가림(끝 4자리만 보임), 눈 아이콘으로 전체 보기.
 * 키 값은 로그로 남기지 않는다(S04 완료 기준).
 */
@Composable
fun ApiKeyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorText: String? = null,
    enabled: Boolean = true,
    onDone: () -> Unit = {},
) {
    var isVisible by rememberSaveable { mutableStateOf(false) }

    MapleTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        fieldModifier = if (isVisible) Modifier else Modifier.semantics { password() },
        errorText = errorText,
        enabled = enabled,
        textStyle = MapleTheme.typography.body.copy(fontSize = 13.5.sp, letterSpacing = 0.1.em).numeric(),
        visualTransformation = if (isVisible) VisualTransformation.None else ApiKeyMaskTransformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            autoCorrectEnabled = false,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(onDone = { onDone() }),
        trailing = {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable { isVisible = !isVisible },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(if (isVisible) R.drawable.ic_eye_off else R.drawable.ic_eye),
                    contentDescription = stringResource(
                        if (isVisible) R.string.connect_key_hide else R.string.connect_key_show,
                    ),
                    tint = MapleTheme.colors.ink3,
                    modifier = Modifier.size(18.dp),
                )
            }
        },
    )
}

/** 끝 4자리만 남기고 ●로 가린다. 글자 수가 같으므로 커서 위치는 그대로 매핑된다. */
private object ApiKeyMaskTransformation : VisualTransformation {
    private const val VISIBLE_TAIL = 4
    private const val MASK = '●'

    override fun filter(text: AnnotatedString): TransformedText {
        val masked = buildString(text.length) {
            val maskedCount = (text.length - VISIBLE_TAIL).coerceAtLeast(0)
            repeat(maskedCount) { append(MASK) }
            append(text.text, maskedCount, text.length)
        }
        return TransformedText(AnnotatedString(masked), OffsetMapping.Identity)
    }
}

@Composable
private fun ApiKeyTextFieldSample(theme: MapleThemeType) {
    MapleTheme(theme = theme) {
        Column(
            modifier = Modifier
                .background(MapleTheme.colors.ground)
                .padding(MapleDimens.ScreenHorizontalPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ApiKeyTextField(value = "", onValueChange = {})
            ApiKeyTextField(value = "test_0123456789abcdef3f9a", onValueChange = {})
            ApiKeyTextField(
                value = "abc3f9a",
                onValueChange = {},
                errorText = "키를 다시 확인해 주세요. test_ 또는 live_로 시작해요.",
            )
        }
    }
}

@Preview(name = "ApiKeyTextField · 블랙", widthDp = 360)
@Composable
private fun ApiKeyTextFieldBlackPreview() = ApiKeyTextFieldSample(MapleThemeType.BLACK)

@Preview(name = "ApiKeyTextField · 베이지", widthDp = 360)
@Composable
private fun ApiKeyTextFieldBeigePreview() = ApiKeyTextFieldSample(MapleThemeType.BEIGE)
