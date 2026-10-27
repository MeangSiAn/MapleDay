package com.mosstis.mapleday.feature.connect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mosstis.mapleday.core.designsystem.theme.MapleDimens
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType
import com.mosstis.mapleday.core.ui.component.MapleButton
import com.mosstis.mapleday.core.ui.component.MapleCheckBox
import com.mosstis.mapleday.core.ui.component.MapleDashedDivider
import com.mosstis.mapleday.core.ui.component.MapleLoading
import com.mosstis.mapleday.domain.user.model.AccountCharacter
import com.mosstis.mapleday.feature.connect.component.AccountCharacterItem
import com.mosstis.mapleday.feature.connect.component.ApiKeyTextField

// TODO: S04 본문(얻는 것 3줄, 키 입력칸 마스킹/눈 토글, 키 발급 방법 링크, 안내 문구, 연결하기 버튼) 구현
@Composable
fun ConnectScreen(
    uiState: ConnectContract.State,
    onAction: (ConnectContract.Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MapleTheme.colors.ground)
            .safeDrawingPadding()
            .padding(horizontal = MapleDimens.ScreenHorizontalPadding, vertical = MapleDimens.BadgeVerticalPadding),
    ) {
        when (uiState.step) {
            ConnectContract.Step.Loading -> MapleLoading(isLoading = true)
            ConnectContract.Step.InputKey -> {
                Text(
                    text = stringResource(R.string.connect_title),
                    style = MapleTheme.typography.screenTitle,
                    color = MapleTheme.colors.ink,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = MapleTheme.colors.card2)
                        .padding(32.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MapleCheckBox(checked = true)
                            Text(
                                modifier = Modifier.weight(1f)
                                    .padding(horizontal = 4.dp),
                                text = stringResource(R.string.connect_object1),
                                style = MapleTheme.typography.body,
                                color = MapleTheme.colors.ink,
                            )
                        }
                        MapleDashedDivider(modifier = Modifier.padding(vertical = 4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MapleCheckBox(checked = true)
                            Text(
                                modifier = Modifier.weight(1f)
                                    .padding(horizontal = 4.dp),
                                text = stringResource(R.string.connect_object2),
                                style = MapleTheme.typography.body,
                                color = MapleTheme.colors.ink,
                            )
                        }
                        MapleDashedDivider(modifier = Modifier.padding(vertical = 4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MapleCheckBox(checked = true)
                            Text(
                                modifier = Modifier.weight(1f)
                                    .padding(horizontal = 4.dp),
                                text = stringResource(R.string.connect_object3),
                                style = MapleTheme.typography.body,
                                color = MapleTheme.colors.ink,
                            )
                        }
                    }

                }

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(R.string.connect_nexon_api_key_title),
                    style = MapleTheme.typography.body,
                    color = MapleTheme.colors.ink
                )

                ApiKeyTextField(
                    value = uiState.apiKey,
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
                    onValueChange = {
                        onAction(ConnectContract.Action.KeyChange(it))
                    }
                )

                uiState.error?.let { error ->
                    Text(
                        text = stringResource(error.messageRes()),
                        style = MapleTheme.typography.body,
                        color = MapleTheme.colors.bad,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                MapleButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.connect_btn),
                    onClick = {
                        onAction(ConnectContract.Action.OnConnect)
                    }
                )
            }
            ConnectContract.Step.SelectCharacter -> {
                val count = uiState.userCharacterList.size
                val countText = count.toString()
                val fullTitle = stringResource(R.string.connect_character_title, count)
                val highlight = MapleTheme.colors.orange
                val title = remember(fullTitle, highlight) {
                    buildAnnotatedString {
                        append(fullTitle)
                        val start = fullTitle.indexOf(countText)
                        if (start >= 0) addStyle(SpanStyle(color = highlight), start, start + countText.length)
                    }
                }
                Text(
                    text = title,
                    style = MapleTheme.typography.screenTitle,
                    color = MapleTheme.colors.ink,
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = stringResource(R.string.connect_character_title_sub),
                    style = MapleTheme.typography.sub,
                    color = MapleTheme.colors.ink3,
                )

                Spacer(modifier = Modifier.height(16.dp))
                // 버튼을 뺀 남은 높이를 차지하고, 카드는 그 안에서 내용만큼만 커진다 (길어지면 카드 안에서 스크롤)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(16.dp))
                            .background(color = MapleTheme.colors.card2)
                            .padding(32.dp)
                    ) {
                        LazyColumn {
                            items(uiState.userCharacterList, key = { it.ocid }) { character ->
                                AccountCharacterItem(
                                    character = character,
                                    selected = character.ocid in uiState.selectedOcids,
                                    onClick = { onAction(ConnectContract.Action.ToggleCharacter(character.ocid)) },
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                MapleButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.connect_character_save_btn, uiState.selectedOcids.size),
                    enabled = uiState.selectedOcids.isNotEmpty(),
                    isLoading = uiState.isSaving,
                    onClick = {
                        onAction(ConnectContract.Action.OnSaveCharacters)
                    }
                )
            }
        }
    }
}

@Preview(name = "Connect · 블랙")
@Composable
private fun ConnectScreenBlackPreview() {
    MapleTheme(theme = MapleThemeType.BLACK) {
        ConnectScreen(uiState = ConnectContract.State(step = ConnectContract.Step.InputKey), onAction = {})
    }
}

//@Preview(name = "Connect · 베이지")
//@Composable
//private fun ConnectScreenBeigePreview() {
//    MapleTheme(theme = MapleThemeType.BEIGE) {
//        ConnectScreen(uiState = ConnectContract.State(), onAction = {})
//    }
//}
@Preview(name = "Connect · 블랙")
@Composable
private fun ConnectScreenCharacterPreview() {
    val list : List<AccountCharacter> = listOf(
        AccountCharacter(
            ocid = "1",
            name = "테스트1",
            world = "크로아",
            characterClass = "전사",
            level = 200,
        ),
        AccountCharacter(
            ocid = "2",
            name = "테스트1",
            world = "크로아",
            characterClass = "전사",
            level = 200,
        ),
        AccountCharacter(
            ocid = "3",
            name = "테스트1",
            world = "크로아",
            characterClass = "전사",
            level = 200,
        ),
        AccountCharacter(
            ocid = "4",
            name = "테스트1",
            world = "크로아",
            characterClass = "전사",
            level = 200,
        ),
        AccountCharacter(
            ocid = "5",
            name = "테스트1",
            world = "크로아",
            characterClass = "전사",
            level = 200,
        ),
        AccountCharacter(
            ocid = "6",
            name = "테스트1",
            world = "크로아",
            characterClass = "전사",
            level = 200,
        ),
        AccountCharacter(
            ocid = "7",
            name = "테스트1",
            world = "크로아",
            characterClass = "전사",
            level = 200,
        ),
        AccountCharacter(
            ocid = "8",
            name = "테스트1",
            world = "크로아",
            characterClass = "전사",
            level = 200,
        ),
        AccountCharacter(
            ocid = "9",
            name = "테스트1",
            world = "크로아",
            characterClass = "전사",
            level = 200,
        ),
        AccountCharacter(ocid = "10", name = "테스트1", world = "크로아", characterClass = "전사", level = 200),
        AccountCharacter(ocid = "11", name = "테스트1", world = "크로아", characterClass = "전사", level = 200),
        AccountCharacter(ocid = "12", name = "테스트1", world = "크로아", characterClass = "전사", level = 200),
        AccountCharacter(ocid = "13", name = "테스트1", world = "크로아", characterClass = "전사", level = 200),
    )

    MapleTheme(theme = MapleThemeType.BLACK) {
        ConnectScreen(uiState = ConnectContract.State(step = ConnectContract.Step.SelectCharacter, userCharacterList = list), onAction = {})
    }
}