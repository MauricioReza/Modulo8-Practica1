package com.amaurypm.videogamesrf.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Creado por Amaury Perea Matsumura el 02/09/23
 */
data class PokemonDetailDto (
    @SerializedName("sprites")
    var sprites: Sprites? = null
)

data class Sprites (
    @SerializedName("other")
    var other: Other? = null

)

data class Other(
    @SerializedName("official-artwork")
    var officialArtwork: OfficialArtwork? = null
)

data class OfficialArtwork(
    @SerializedName("front_default")
    var frontDefault: String? = null
)




