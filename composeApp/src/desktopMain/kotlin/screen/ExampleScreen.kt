package screen

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen

class ExampleScreen : Screen {
    @Composable
    override fun Content() {
        ScrollableList()
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