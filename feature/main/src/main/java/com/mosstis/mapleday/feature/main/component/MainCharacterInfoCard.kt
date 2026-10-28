package com.mosstis.mapleday.feature.main.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mosstis.mapleday.core.designsystem.component.HeroCard
import com.mosstis.mapleday.core.designsystem.theme.MapleTheme
import com.mosstis.mapleday.core.designsystem.theme.MapleThemeType
import com.mosstis.mapleday.core.ui.component.ExpBar
import com.mosstis.mapleday.domain.character.model.CharacterBasic
import com.mosstis.mapleday.feature.main.R

@Composable
fun MainCharacterInfoCard(modifier: Modifier = Modifier, character : CharacterBasic) {
    HeroCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        Column() {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(start = 16.dp)
                        .scale(3f)
                        .graphicsLayer(
                            translationY = -20f
                        )
                    ,
                    filterQuality = FilterQuality.None,
                    contentDescription = "캐릭터이미지",
                    model = character.imageUrl,
                    contentScale = ContentScale.Fit,
                )
                Column(
                ) {
                    Text(
                        text = character.name,
                        style = MapleTheme.typography.cardTitle,
                        color = MapleTheme.colors.ink
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = stringResource(
                            R.string.main_character_info,
                            character.world,
                            character.level,
                            character.characterClass
                        ),
                        style = MapleTheme.typography.sub,
                        color = MapleTheme.colors.ink3
                    )
                }
            }
            ExpBar(expRate = character.expRate)
        }
    }
}

// Preview 전용 샘플 (NAS /app/today 실제 응답 기준)
private val PreviewBasic = CharacterBasic(
    ocid = "e6b02412cfdb40c5186ac5a15c7676c0",
    name = "맹리엔",
    world = "크로아",
    characterClass = "렌",
    classLevel = 6,
    level = 272,
    exp = 1254292389713L,
    expRate = 22.718,
    guild = null,
    imageUrl = null, // Preview는 네트워크가 없어 이미지가 안 뜬다
    createdAt = null,
    fetchedAt = 0L,
)

@Preview(name = "MainCharacterInfoCard · 블랙")
@Composable
private fun MainCharacterInfoCardBlackPreview() {
    MapleTheme(theme = MapleThemeType.BLACK) {
        MainCharacterInfoCard(character = PreviewBasic)
    }
}

@Preview(name = "MainCharacterInfoCard · 베이지")
@Composable
private fun MainCharacterInfoCardBeigePreview() {
    MapleTheme(theme = MapleThemeType.BEIGE) {
        MainCharacterInfoCard(character = PreviewBasic)
    }
}