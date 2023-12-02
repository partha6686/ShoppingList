package ps.example.shoppinglist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ShoppingItem (
    val id: Int,
    var name: String,
    var qty: Int,
    var isEditing: Boolean = false
)

@Composable
fun ShoppingList(){
    var listItems by remember {
        mutableStateOf(listOf<ShoppingItem>())
    }
    var showAddDialog by remember {
        mutableStateOf(false)
    }
    var itemName by remember {
        mutableStateOf("")
    }
    var itemQty by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){
        Button(onClick = { showAddDialog = true }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Add Item")
        }
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ){
            items(listItems){
                item->
                if(item.isEditing){
                    ItemEditor(
                        item = item,
                        onEditComplete = {
                            editedName, editedQty ->
                            listItems = listItems.map {
                                it.copy(isEditing = false)
                            }
                            val editedItem = listItems.find { it.id == item.id }
                            editedItem?.let{
                                it.name = editedName
                                it.qty = editedQty
                            }
                        },
                        onCancleCLick = {
                            listItems = listItems.map {
                                it.copy(isEditing = false)
                            }
                        }
                    )
                }
                ListItem(
                    item = item,
                    onEditClick = {
                        listItems = listItems.map { it.copy(isEditing = it.id == item.id) }
                    },
                    onDeleteClick = {
                        listItems = listItems - item
                    }
                )
            }
        }
    }

    if(showAddDialog){
        AlertDialog(
            onDismissRequest = { showAddDialog = false }, 
            confirmButton = {
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween){
                    Button(onClick = {
                        if(itemName.isNotBlank() && itemQty.isNotBlank()){
                            val newItem = ShoppingItem(
                                id = listItems.size+1,
                                name = itemName,
                                qty = itemQty.toIntOrNull()?: 0
                            )
                            listItems =listItems + newItem
                            showAddDialog = false
                            itemQty = ""
                            itemName = ""
                        }
                    }) {
                        Text(text = "Add")
                    }
                    Button(onClick = { showAddDialog = false }) {
                        Text(text = "Cancel")
                    }
                }
            } ,
            title = { Text(text = "Add Shopping Item")},
            text = {
                Column {
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = { itemName = it },
                        singleLine = true,
                        label = { Text(text = "Item")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)

                    )
                    OutlinedTextField(
                        value = itemQty,
                        onValueChange = { itemQty = it },
                        singleLine = true,
                        label = { Text(text = "Quantity")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        )
    }
}