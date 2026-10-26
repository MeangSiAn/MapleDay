package com.mosstis.mapleday.feature.connect.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType
import com.mosstis.mapleday.core.ui.component.MapleCheckBox
import com.mosstis.mapleday.domain.user.model.AccountCharacter
import com.mosstis.mapleday.feature.connect.R

/**
 * S05 계정 캐릭터 목록 한 행: 이름 / "Lv.{레벨} {직업}" / 선택 체크.
 * 행 전체를 눌러 선택을 토글한다.
 */
@Composable
fun AccountCharacterItem(
    character: AccountCharacter,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = character.name,
                style = MapleTheme.typography.body,
                color = MapleTheme.colors.ink,
            )
            Text(
                text = stringResource(
                    R.string.connect_character_level_class,
                    character.level,
                    character.characterClass,
                ),
                style = MapleTheme.typography.sub,
                color = MapleTheme.colors.ink3,
            )
        }
        MapleCheckBox(checked = selected)
    }
}

@Preview(name = "AccountCharacterItem · 블랙")
@Composable
private fun AccountCharacterItemPreview() {
    MapleTheme(theme = MapleThemeType.BLACK) {
        Column {
            AccountCharacterItem(
                character = AccountCharacter("1", "메이플노트", "스카니아", "비숍", 267),
                selected = true,
                onClick = {},
            )
            AccountCharacterItem(
                character = AccountCharacter("2", "창고지기", "스카니아", "아델", 200),
                selected = false,
                onClick = {},
            )
        }
    }
}
