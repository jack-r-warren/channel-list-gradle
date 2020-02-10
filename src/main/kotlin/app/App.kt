package app

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.css.*
import model.Channel
import react.*
import styled.*
import kotlin.browser.window

interface AppState : RState {
    var channelList: List<Channel>
    var searchTerm: String?
    var selectedChannel: Channel?
    var viewingSelectedChannel: Boolean
}

class App : RComponent<RProps, AppState>() {
    override fun AppState.init() {
        channelList = listOf()
        viewingSelectedChannel = false

        MainScope().launch {
            val channels = fetchChannels().toList().sortedBy { channel: Channel -> channel.number.toDoubleOrNull() }
            setState {
                channelList = channels
            }
        }
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                fontFamily = "\"Trebuchet MS\", Helvetica, sans-serif"
            }

            channelModal {
                this.channel = state.selectedChannel
                this.isOpen = state.viewingSelectedChannel
                this.onRequestClose = {
                    setState {
                        viewingSelectedChannel = false
                    }
                }
            }

            searchBoxComponent {
                onTextChange = { searchTerm: String ->
                    setState {
                        this.searchTerm = searchTerm
                    }
                }
            }

            channelListComponent {
                this.searchTerm = state.searchTerm
                this.channelList = state.channelList
                this.onChannelSelect = {
                    setState {
                        selectedChannel = it
                        viewingSelectedChannel = true
                    }
                }
            }

            styledP {
                css {
                    color = Color("#ECEFF1")
                    textAlign = TextAlign.center
                }
                +"Copyright Jack Warren 2019"
            }
        }
    }

}

suspend fun fetchChannels(): Array<Channel> =
        window.fetch("assets/channels.json")
                .await()
                .json()
                .await()
                .unsafeCast<Array<Channel>>()