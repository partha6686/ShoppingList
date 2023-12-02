package ps.example.shoppinglist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ItemEditor(item: ShoppingItem, onEditComplete: (String, Int)->Unit, onCancleCLick: ()->Unit){
    var editedName by remember {
        mutableStateOf(item.name)
    }
    var editedQty by remember {
        mutableStateOf(item.qty.toString())
    }
    var isEditing by remember {
        mutableStateOf(item.isEditing)
    }

    AlertDialog(
            onDismissRequest = { isEditing = false },

            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        if (editedName.isNotBlank() && editedQty.isNotBlank()) {
                            isEditing = false
                            onEditComplete(editedName, editedQty.toIntOrNull() ?: 1)
                        }
                    }) {
                        Text(text = "Save")
                    }
                    Button(onClick = { onCancleCLick() }) {
                        Text(text = "Cancel")
                    }
                }
            },
            title = { Text(text = "Edit Item") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        singleLine = true,
                        label = { Text(text = "Item") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)

                    )
                    OutlinedTextField(
                        value = editedQty,
                        onValueChange = { editedQty = it },
                        singleLine = true,
                        label = { Text(text = "Quantity") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
    )
}