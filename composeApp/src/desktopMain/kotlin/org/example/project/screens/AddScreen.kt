package org.example.project.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.awtTransferable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import desktopswitchboard.composeapp.generated.resources.Res
import desktopswitchboard.composeapp.generated.resources.img_box_svgrepo_com
import org.example.project.viewmodel.AddViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.skia.Image
import java.awt.datatransfer.DataFlavor
import java.io.File

@Composable
fun AddScreen(
    addViewModel: AddViewModel,
) {

    val images by addViewModel.images.collectAsStateWithLifecycle()
    val name by addViewModel.name.collectAsStateWithLifecycle()
    var selectedChip by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                AssistChip(
                    onClick = {
                        selectedChip = 1
                    },
                    label = {Text("Клавиатура")},
                    colors = AssistChipDefaults.assistChipColors(
                        if (selectedChip == 1) Color.LightGray else Color.White
                    )
                )
                AssistChip(
                    onClick = {
                        selectedChip = 2
                    },
                    label = {Text("Свитчи")},
                    colors = AssistChipDefaults.assistChipColors(
                        if (selectedChip == 2) Color.LightGray else Color.White
                    )
                )
                AssistChip(
                    onClick = {
                        selectedChip = 3
                    },
                    label = {Text("Кейкапы")},
                    colors = AssistChipDefaults.assistChipColors(
                        if (selectedChip == 3) Color.LightGray else Color.White
                    )
                )
            }


        }
        Row(

        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(0.5f)

            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { newValue ->
                        addViewModel.updateName(newValue)

                    },
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    label = {Text("Имя")},
                    maxLines = 1
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { newValue ->
                        addViewModel.updateName(newValue)

                    },
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    label = {Text("Описание")},
                    maxLines = 3


                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { newValue ->
                        addViewModel.updateName(newValue)

                    },
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    label = {Text("Свитчи")},
                    maxLines = 1
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { newValue ->
                        addViewModel.updateName(newValue)

                    },
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    label = {Text("Кейкапы")},
                    maxLines = 1
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { newValue ->
                        addViewModel.updateName(newValue)

                    },
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    label = {Text("Форм-фактор")},
                    maxLines = 1
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { newValue ->
                        addViewModel.updateName(newValue)

                    },
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    label = {Text("Цена")},
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 16.dp))
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .weight(0.5f)
            ) {


                ImageGalleryDropZone(
                    images,
                    onImageDropped = { addViewModel.addImage(it) },
                    onClickClear = {addViewModel.clearImage()}
                )


            }

        }
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImageGalleryDropZone(
    images: List<ImageBitmap>,
    onImageDropped: (ImageBitmap) -> Unit,
    onClickClear: () -> Unit
) {

    // State to track hover effect and loaded bitmaps
    var isHovering by remember { mutableStateOf(false) }

    // Drag-and-drop target implementation
    val dropTarget = remember {
        object : DragAndDropTarget {
            override fun onStarted(event: DragAndDropEvent) {
                isHovering = true
            }

            override fun onEnded(event: DragAndDropEvent) {
                isHovering = false
            }

            override fun onDrop(event: DragAndDropEvent): Boolean {
                isHovering = false
                val dropped = (event.awtTransferable
                    .getTransferData(DataFlavor.javaFileListFlavor) as? List<*>)
                    ?.filterIsInstance<File>()
                    ?: emptyList()

                // Keep only common image files
                val imageFiles = dropped.filter { file ->
                    file.extension.lowercase() in listOf("jpg", "jpeg", "png", "gif", "bmp", "webp")
                }
                if (imageFiles.isNotEmpty()) {
                    // Load each into an ImageBitmap
                    imageFiles.forEach { file ->
                        val bytes = file.readBytes()
                        val bmp = Image
                            .makeFromEncoded(bytes)
                            .toComposeImageBitmap()
                        onImageDropped(bmp)    //
                    }
                    return true
                }
                return false
            }
        }
    }

    // Fixed size drop area
    val height =
        if (images.size == 1) 600.dp
        else if (images.size == 2) 600.dp
        else if (images.size >= 3) 880.dp
        else if (images.isEmpty()) 600.dp
        else 300.dp
    val width = if (images.isEmpty()) 600.dp else if(images.size == 1) 600.dp else 300.dp

    Card(
        modifier = Modifier
            .width(width)
            .height(height)
            .dragAndDropTarget(
                shouldStartDragAndDrop = { true },
                target = dropTarget
            ),
        border = BorderStroke(
            width = if (isHovering) 2.dp else 1.dp,
            color = if (isHovering) Color.Blue else Color.Gray
        )
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(if (isHovering) Color(0xFFE0E0E0) else Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {

            if (images.isEmpty()) {
                if (isHovering) {
                    Image(
                        painterResource(Res.drawable.img_box_svgrepo_com),
                        null,
                        modifier = Modifier
                            .size(100.dp)
                    )
                } else {
                    Text(
                        text = "Перетащите изображение сюда",
                        textAlign = TextAlign.Center
                    )
                }

            } else {
                Column(
                    horizontalAlignment = if (images.size == 1) Alignment.CenterHorizontally else Alignment.End,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    images.forEach { img ->
                        Image(
                            bitmap = img,
                            contentDescription = null,
                            modifier = Modifier
                                .size(if (images.size == 1) 600.dp else 300.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                }

            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxHeight()
            ) {
                Button(
                    onClick = {
                        onClickClear()
                    },
                    modifier = Modifier.fillMaxWidth(1f)
                ) {
                    Text("Очистить")
                }
            }
        }
    }
}