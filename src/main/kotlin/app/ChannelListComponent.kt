package app

import kotlinx.css.*
import model.Channel
import model.matches
import react.*
import styled.css
import styled.styledDiv

interface ChannelListProps : RProps {
    var channelList: List<Channel>
    var searchTerm: String?
    var onChannelSelect: (Channel) -> Unit
}

class ChannelListComponent(props: ChannelListProps) : RComponent<ChannelListProps, RState>(props) {
    override fun RBuilder.render() {
        styledDiv {
            css {
                marginTop = 5.px
                width = 100.pct
            }
            for (channel in props.channelList) {
                if (props.searchTerm?.let(channel::matches) != false) {
                    channelComponent {
                        this.channel = channel
                        this.onClickFunction = {
                            props.onChannelSelect(channel)
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.channelListComponent(handler: ChannelListProps.() -> Unit): ReactElement {
    return this.child(ChannelListComponent::class) {
        this.attrs(handler)
    }
}