package com.example.starsign.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.starsign.datamodel.StarSign
import com.example.starsign.datamodel.starSigns

@Composable
fun StarSignList(
    selectedSignId: Int?,
    onSignClick: (StarSign) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.statusBarsPadding()
    ) {
        items(starSigns) { sign ->
            Text(
                text = sign.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSignClick(sign)
                    }
                    .padding(16.dp)
            )
        }
    }
}
