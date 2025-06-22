package org.example.project.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferAction
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.DragAndDropTransferable
import androidx.compose.ui.draganddrop.awtTransferable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import switchboardexe.composeapp.generated.resources.Res
import switchboardexe.composeapp.generated.resources.img_box_svgrepo_com
import java.awt.datatransfer.DataFlavor
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO

@Composable
fun AddScreen() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Row(
        ) {
            GalleryWithDrop()

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxSize()
            ) {
                Card(
                    shape = RoundedCornerShape(5),
                    modifier = Modifier
                        .size(450.dp)
                ) {
                    Image(
                        painterResource(Res.drawable.img_box_svgrepo_com),
                        null,
                        modifier = Modifier.fillMaxSize(0.2f),

                        )
                }
            }

        }
    }

}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImageDropZone(
    modifier: Modifier = Modifier,
    onImagesDropped: (List<File>) -> Unit
) {
    var isHovering by remember { mutableStateOf(false) }

    // Create a DragAndDropTarget that handles file drops
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
                // Extract the list of files from the transferable
                val dropped = (event.awtTransferable
                    .getTransferData(DataFlavor.javaFileListFlavor) as? List<*>)
                    ?.filterIsInstance<File>()
                    ?: emptyList()

                // Filter to common image extensions
                val images = dropped.filter { file ->
                    file.extension.lowercase() in listOf("jpg", "jpeg", "png", "gif", "bmp", "webp")
                }
                if (images.isNotEmpty()) {
                    onImagesDropped(images)
                    return true
                }
                return false
            }
        }
    }

    Box(
        modifier = modifier
            .size(300.dp)
            .background(if (isHovering) Color(0xFFE0E0E0) else Color(0xFFF5F5F5))
            .border(2.dp, if (isHovering) Color.Blue else Color.Gray)
            .dragAndDropTarget(
                shouldStartDragAndDrop = { true },
                target = dropTarget
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(if (isHovering) "" else "Перетащите изображение сюда")
    }
}

@Composable
fun GalleryWithDrop() {
    // Keep ImageBitmaps in a mutable list so Compose will recompose when new images arrive
    val images = remember { mutableStateListOf<ImageBitmap>() }

    Column {
        ImageDropZone { files ->
            files.forEach { file ->
                // Load each file into an ImageBitmap via Skia
                val bytes = file.readBytes()
                val bmp = org.jetbrains.skia.Image.makeFromEncoded(bytes).asImageBitmap()
                images += bmp
            }
        }
        Spacer(Modifier.height(16.dp))
        Row() {
            images.forEach { img ->
                Image(
                    bitmap = img,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}
