package br.com.gifsearch.android.app.data.model

data class GifResponseDTO (
    var data: List<GifDTO>?,
    var pagination: GifPaginationDTO?
)