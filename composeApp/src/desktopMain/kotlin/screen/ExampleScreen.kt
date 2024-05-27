package screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ContextMenuDataProvider
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen

class ExampleScreen : Screen {
    @Composable
    override fun Content() {
        ContextMenuContent()
    }
}

@Composable
fun ScrollableList(modifier: Modifier = Modifier) {
    val verticalScroll = rememberScrollState(0)
    val horizontalScroll = rememberScrollState(0)
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 10.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(verticalScroll)
                .horizontalScroll(horizontalScroll)
                .padding(
                    end = 12.dp, bottom = 12.dp
                )
        ) {
            for (item in 0..30) {
                Text(
                    modifier = modifier.padding(all = 12.dp),
                    text = "This is the item number: #$item"
                )
            }
        }
        VerticalScrollbar(
            modifier = modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(verticalScroll)
        )
        HorizontalScrollbar(
            modifier = modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(end = 12.dp),
            adapter = rememberScrollbarAdapter(horizontalScroll)
        )
    }
}

@Composable
fun ScrollableLazyList(modifier: Modifier = Modifier) {
    val lazyListState = rememberLazyListState()
    val horizontalScroll = rememberScrollState(0)
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 10.dp)
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .horizontalScroll(horizontalScroll)
                .padding(
                    end = 12.dp, bottom = 12.dp
                ),
            state = lazyListState
        ) {
            items(1000) { number ->
                Text(
                    modifier = modifier.padding(all = 12.dp),
                    text = "This is the item number: #$number"
                )
            }
        }
        VerticalScrollbar(
            modifier = modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(lazyListState)
        )
        HorizontalScrollbar(
            modifier = modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(end = 12.dp),
            adapter = rememberScrollbarAdapter(horizontalScroll)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToolTips(modifier: Modifier = Modifier) {
    val buttons = listOf("Contact US", "About")
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        buttons.forEachIndexed { index, title ->
            TooltipArea(
                tooltip = {
                    Surface(
                        modifier = modifier.shadow(8.dp),
                        color = androidx.compose.ui.graphics.Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (index == 0) "Get in touch!" else "This is our team",
                            modifier = modifier.padding(10.dp)
                        )
                    }
                },
                delayMillis = 600,
                tooltipPlacement = TooltipPlacement.CursorPoint(
                    alignment = Alignment.BottomEnd
                )
            ) {
                Button(onClick = {}) { Text(text = title) }
            }

            if (index == 0) Spacer(modifier = modifier.height(12.dp))
        }
    }
}

@Composable
fun KeyEventContent(modifier: Modifier = Modifier) {
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = text1,
            onValueChange = { text1 = it },
            modifier = modifier.onPreviewKeyEvent {
                if (it.key == Key.Delete && it.type == KeyEventType.KeyDown) {
                    text1 = ""
                    true
                } else false
            }
        )
        Spacer(modifier = modifier.height(12.dp))
        TextField(
            value = text2,
            onValueChange = { text2 = it }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClickEventContent(modifier: Modifier = Modifier) {
    var text by remember {
        mutableStateOf("Click the box")
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = modifier
                    .background(Color.Cyan)
                    .fillMaxWidth(0.7f)
                    .fillMaxHeight(0.2f)
                    .combinedClickable(
                        onClick = {
                            text = "Single Click"
                        },
                        onDoubleClick = {
                            text = "Double Click"
                        },
                        onLongClick = {
                            text = "Long Click"
                        }
                    )
            ) {
            }
            Spacer(modifier = modifier.height(12.dp))
            Text(
                text = text,
                fontSize = 40.sp
            )
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MouseHoverContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.background(Color.White),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(10) { index ->
            var hovered by remember {
                mutableStateOf(false)
            }
            val animatedColor by animateColorAsState(
                targetValue = if (hovered) Color.Cyan else Color.White,
                animationSpec = tween(200)
            )
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = animatedColor)
                    .padding(all = 8.dp)
                    .onPointerEvent(PointerEventType.Enter) { hovered = true }
                    .onPointerEvent(PointerEventType.Exit) { hovered = false },
                fontSize = 30.sp,
                text = "Item with the number: $index"
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableContent(modifier: Modifier = Modifier) {
    var topBoxOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    Box(
        modifier = modifier
            .size(100.dp)
            .offset {
                IntOffset(topBoxOffset.x.toInt(), topBoxOffset.y.toInt())
            }
            .background(Color.Blue)
            .pointerInput(Unit) {
                detectDragGestures(matcher = PointerMatcher.Primary) {
                    topBoxOffset += it
                }
            }
    ) {
        Text(
            modifier = modifier
                .align(Alignment.Center),
            text = "Draggable",
            color = Color.White
        )
    }
}


// Cut Copy Paste by default not allowed on Text but allowed on TextField in jetpack compose.Here is the example.
@Composable
fun WhyNeedContextMenuContent(modifier: Modifier = Modifier) {
    var text by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = text, onValueChange = {
            text = it
        })
        Spacer(modifier = modifier.height(12.dp))
        Text(text = "Hello World!")
    }
}

@Composable
fun ContextMenuContent(modifier: Modifier = Modifier) {
    var text by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContextMenuDataProvider(
            items = {
                listOf(
                    ContextMenuItem(
                        label = "Custom Action"
                    ) {
                        println("Custom Action Clicked!")
                    }
                )
            }
        ) {
            TextField(value = text, onValueChange = {
                text = it
            })
            Spacer(modifier = modifier.height(12.dp))
            SelectionContainer {
                Text(text = "Hello World!")
            }
        }
    }
}