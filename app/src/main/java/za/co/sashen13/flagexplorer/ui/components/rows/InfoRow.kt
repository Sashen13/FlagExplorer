package za.co.sashen13.flagexplorer.ui.components.rows

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(imageVector = icon, contentDescription = null)

        Spacer(modifier = Modifier.width(12.dp))

        Text(text = "$label:", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}