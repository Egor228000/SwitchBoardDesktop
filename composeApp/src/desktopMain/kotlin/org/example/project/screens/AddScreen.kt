package org.example.project.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import desktopswitchboard.composeapp.generated.resources.Res
import desktopswitchboard.composeapp.generated.resources.clear_svgrepo_com
import desktopswitchboard.composeapp.generated.resources.img_box_svgrepo_com
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.launch
import org.example.project.screens.addKeyBoard.AddKeyBoardEvent
import org.example.project.viewmodel.AddViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.skia.Image
import java.awt.datatransfer.DataFlavor
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    addViewModel: AddViewModel,
) {

    LaunchedEffect(Unit) {
        addViewModel.loadSwitchNames()
        addViewModel.loadSwichType()
        addViewModel.loadKeycapsType()
        addViewModel.loadKeycapsMaterial()
        addViewModel.loadFormFactor()
    }


    var expandedSwichName by remember { mutableStateOf(false) }
    val listSwichName by addViewModel.listSwitchName.collectAsStateWithLifecycle()

    var expandedSwichType by remember { mutableStateOf(false) }
    val listSwichType by addViewModel.listSwichType.collectAsStateWithLifecycle()

    var expandedkeycapsType by remember { mutableStateOf(false) }
    val listKeycapsType by addViewModel.listKeycapsType.collectAsStateWithLifecycle()

    var expandedkeycapsMaterial by remember { mutableStateOf(false) }
    val listKeycapsMaterial by addViewModel.listKeycapsMaterial.collectAsStateWithLifecycle()


    var expandedformFactor by remember { mutableStateOf(false) }
    val listFormFactor by addViewModel.listFormFactor.collectAsStateWithLifecycle()


    val uiState by addViewModel.uiState.collectAsStateWithLifecycle()
    var selectedChip by remember { mutableStateOf(1) }



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
                    label = { Text("Клавиатура") },
                    colors = AssistChipDefaults.assistChipColors(
                        if (selectedChip == 1) Color.LightGray else Color.White
                    )
                )
                AssistChip(
                    onClick = {
                        selectedChip = 2
                    },
                    label = { Text("Свитчи") },
                    colors = AssistChipDefaults.assistChipColors(
                        if (selectedChip == 2) Color.LightGray else Color.White
                    )
                )
                AssistChip(
                    onClick = {
                        selectedChip = 3
                    },
                    label = { Text("Кейкапы") },
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
                    value = uiState.name,
                    onValueChange = { newValue ->
                        addViewModel.onEvent(AddKeyBoardEvent.AddKeyBoardName(newValue))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text("Имя") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences,
                    )
                )
                OutlinedTextField(
                    value = uiState.description,
                    onValueChange = { newValue ->
                        addViewModel.onEvent(AddKeyBoardEvent.AddKeyBoardDescription(newValue))


                    },
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    label = { Text("Описание") },
                    maxLines = 3
                )




                ExposedDropdownMenuBox(
                    expanded = expandedSwichName,
                    onExpandedChange = { expandedSwichName = !expandedSwichName },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // 1) The text field that shows the current selection
                    OutlinedTextField(
                        value = uiState.swichName,
                        onValueChange = { /* readOnly = true, so ignore */ },
                        readOnly = true,
                        label = { Text("Switch Name") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSwichName)
                        },
                        modifier = Modifier
                            .menuAnchor(
                                ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                                true
                            )   // anchors the menu to the text field
                            .fillMaxWidth()
                    )

                    // 2) The dropdown menu itself
                    ExposedDropdownMenu(
                        expanded = expandedSwichName,
                        onDismissRequest = { expandedSwichName = false }
                    ) {
                        listSwichName.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    addViewModel.onEvent(AddKeyBoardEvent.AddKeyBoardSwichName(option))
                                    expandedSwichName = false
                                }
                            )
                        }
                    }
                }



                ExposedDropdownMenuBox(
                    expanded = expandedSwichType,
                    onExpandedChange = { expandedSwichType = !expandedSwichType },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // 1) The text field that shows the current selection
                    OutlinedTextField(
                        value = uiState.swichType,
                        onValueChange = { /* readOnly = true, so ignore */ },
                        readOnly = true,
                        label = { Text("Switch Type") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSwichType)
                        },
                        modifier = Modifier
                            .menuAnchor(
                                ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                                true
                            )   // anchors the menu to the text field
                            .fillMaxWidth()
                    )

                    // 2) The dropdown menu itself
                    ExposedDropdownMenu(
                        expanded = expandedSwichType,
                        onDismissRequest = { expandedSwichType = false }
                    ) {
                        listSwichType.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    addViewModel.onEvent(AddKeyBoardEvent.AddKeyBoardSwichType(option))

                                    expandedSwichType = false
                                }
                            )
                        }
                    }
                }
                ExposedDropdownMenuBox(
                    expanded = expandedkeycapsType,
                    onExpandedChange = { expandedkeycapsType = !expandedkeycapsType },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // 1) The text field that shows the current selection
                    OutlinedTextField(
                        value = uiState.keycapsType,
                        onValueChange = { /* readOnly = true, so ignore */ },
                        readOnly = true,
                        label = { Text("Keycap Type") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedkeycapsType)
                        },
                        modifier = Modifier
                            .menuAnchor(
                                ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                                true
                            )   // anchors the menu to the text field
                            .fillMaxWidth()
                    )

                    // 2) The dropdown menu itself
                    ExposedDropdownMenu(
                        expanded = expandedkeycapsType,
                        onDismissRequest = { expandedkeycapsType = false }
                    ) {
                        listKeycapsType.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    addViewModel.onEvent(AddKeyBoardEvent.AddKeyBoardKeycapsType(option))

                                    expandedkeycapsType = false
                                }
                            )
                        }
                    }
                }
                ExposedDropdownMenuBox(
                    expanded = expandedkeycapsMaterial,
                    onExpandedChange = { expandedkeycapsMaterial = !expandedkeycapsMaterial },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // 1) The text field that shows the current selection
                    OutlinedTextField(
                        value = uiState.keycapsMaterial,
                        onValueChange = { /* readOnly = true, so ignore */ },
                        readOnly = true,
                        label = { Text("Keycap Material") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedkeycapsMaterial)
                        },
                        modifier = Modifier
                            .menuAnchor(
                                ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                                true
                            )   // anchors the menu to the text field
                            .fillMaxWidth()
                    )

                    // 2) The dropdown menu itself
                    ExposedDropdownMenu(
                        expanded = expandedkeycapsMaterial,
                        onDismissRequest = { expandedkeycapsMaterial = false }
                    ) {
                        listKeycapsMaterial.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    addViewModel.onEvent(AddKeyBoardEvent.AddKeyBoardKeycapsMaterial(option))

                                    expandedkeycapsMaterial = false
                                }
                            )
                        }
                    }
                }
                ExposedDropdownMenuBox(
                    expanded = expandedformFactor,
                    onExpandedChange = { expandedformFactor = !expandedformFactor },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // 1) The text field that shows the current selection
                    OutlinedTextField(
                        value = uiState.formFactor,
                        onValueChange = { /* readOnly = true, so ignore */ },
                        readOnly = true,
                        label = { Text("Форм-фактор") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedformFactor)
                        },
                        modifier = Modifier
                            .menuAnchor(
                                ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                                true
                            )   // anchors the menu to the text field
                            .fillMaxWidth()
                    )

                    // 2) The dropdown menu itself
                    ExposedDropdownMenu(
                        expanded = expandedformFactor,
                        onDismissRequest = { expandedformFactor = false }
                    ) {
                        listFormFactor.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    addViewModel.onEvent(AddKeyBoardEvent.AddKeyBoardFormFactor(option))

                                    expandedformFactor = false
                                }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = uiState.stockQuantity,
                    onValueChange = { newValue ->
                        addViewModel.onEvent(AddKeyBoardEvent.AddKeyBoardStockQuantity(newValue))

                    },
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    label = { Text("Количество") },
                    maxLines = 1
                )
                OutlinedTextField(
                    value = uiState.price,
                    onValueChange = { newValue ->
                        addViewModel.onEvent(AddKeyBoardEvent.AddKeyBoardPrice(newValue))


                    },
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    label = { Text("Цена") },
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
                    uiState.listImages,
                    onImageDropped = { addImage ->
                        addViewModel.onEvent(AddKeyBoardEvent.AddKeyBoardListImages(addImage))
                    },
                    onClickClear = { addViewModel.onEvent(AddKeyBoardEvent.onClearButton) }
                )


            }

        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxHeight(1f)
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth(1f)
            ) {
                Text("Добавить товар")
            }
        }

    }

}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ImageGalleryDropZone(
    images: List<ImageBitmap>,
    onImageDropped: (ImageBitmap) -> Unit,
    onClickClear: () -> Unit,
) {
    val scope = rememberCoroutineScope()
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
                    file.extension.lowercase() in listOf("jpg", "jpeg", "png", "webp")
                }
                if (imageFiles.isNotEmpty()) {
                    // Load each into an ImageBitmap
                    imageFiles.forEach { file ->
                        val bytes = file.readBytes()
                        val bmp = Image
                            .makeFromEncoded(bytes)
                            .toComposeImageBitmap()
                        onImageDropped(bmp)
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
        else if (images.size >= 3) 600.dp
        else if (images.isEmpty()) 600.dp
        else 300.dp
    val width = if (images.isEmpty()) 600.dp else if (images.size == 1) 600.dp else 300.dp
    val stateVertical = rememberScrollState(0)
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
                Box() {
                    Column(
                        horizontalAlignment = if (images.size == 1) Alignment.CenterHorizontally else Alignment.End,
                        modifier = Modifier
                            .verticalScroll(stateVertical)
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
                    VerticalScrollbar(
                        modifier = Modifier.align(Alignment.CenterEnd)
                            .fillMaxHeight(),
                        adapter = rememberScrollbarAdapter(stateVertical),
                        style = ScrollbarStyle(
                            minimalHeight = 10.dp,
                            thickness = 10.dp,
                            shape = RoundedCornerShape(10.dp),
                            hoverDurationMillis = 10,
                            unhoverColor = Color.LightGray,
                            hoverColor = Color.DarkGray
                        )
                    )
                }


            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxHeight()
            ) {

                SplitButtonLayout(
                    leadingButton = {
                        SplitButtonDefaults.LeadingButton(
                            onClick = {
                                scope.launch {
                                    val file =
                                        FileKit.openFilePicker(type = FileKitType.Image, title = "Выберите изображение")
                                    if (file != null) {
                                        onImageDropped(file.toImageBitmap())
                                    }
                                }

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black,
                                disabledContainerColor = Color.White,
                                disabledContentColor = Color.White
                            ),
                        ) {
                            Text("Добавить файлы")
                        }
                    },
                    trailingButton = {
                        SplitButtonDefaults.TrailingButton(
                            onClick = {
                                onClickClear()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black,
                                disabledContainerColor = Color.White,
                                disabledContentColor = Color.White
                            ),
                        ) {
                            Icon(
                                painterResource(Res.drawable.clear_svgrepo_com),
                                modifier = Modifier.size(SplitButtonDefaults.LeadingIconSize),
                                contentDescription = null,
                            )
                        }
                    },
                )
            }
        }
    }
}

suspend fun PlatformFile.toImageBitmap(): ImageBitmap {
    val bytes = this.readBytes()
    val skiaImage = Image.makeFromEncoded(bytes)
    return skiaImage.toComposeImageBitmap()
}