package com.itsmobile.pokedex.ui.moveslist.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.itsmobile.pokedex.R

class Font {
    companion object{
        val poppinsFamily = FontFamily(
            Font(R.font.poppins_light, FontWeight.Light),
            Font(R.font.poppins_medium, FontWeight.Medium),
            Font(R.font.poppins_regular, FontWeight.Normal),
            Font(R.font.poppins_semibold, FontWeight.SemiBold),
            Font(R.font.poppins_bold, FontWeight.Bold)
        )
    }
}