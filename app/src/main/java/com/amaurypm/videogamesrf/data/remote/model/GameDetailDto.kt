package com.amaurypm.videogamesrf.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Creado por Amaury Perea Matsumura el 02/09/23
 */

data class GameDetailDto(
    @SerializedName("titulo")
    var titulo: String? = null,
    @SerializedName("Video")
    var Video: String? = null,
    @SerializedName("long_desc")
    var long_desc: String? = null
)
