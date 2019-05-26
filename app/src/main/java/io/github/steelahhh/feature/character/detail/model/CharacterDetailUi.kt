package io.github.steelahhh.feature.character.detail.model

import android.os.Parcelable
import io.github.steelahhh.core.ui.CharacteristicItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterDetailUi(
    val name: String = "",
    val characteristics: List<CharacteristicItem> = listOf(),
    val speciesInfo: List<CharacteristicItem> = listOf(),
    val planetInfo: List<CharacteristicItem> = listOf(),
    val films: List<FilmItem> = listOf()
) : Parcelable
