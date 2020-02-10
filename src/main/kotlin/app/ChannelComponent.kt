package app

import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import model.Channel
import react.*
import react.dom.div
import react.dom.h4
import react.dom.p
import styled.*

interface ChannelProps : RProps {
    var channel: Channel
    var onClickFunction: () -> Unit
}

class ChannelComponent (props: ChannelProps) : RComponent<ChannelProps, RState>(props) {
    override fun RBuilder.render() {
        styledDiv {
            key = props.channel.toString()
            css {
                width = 90.pct
                maxWidth = 500.px
                textAlign = TextAlign.left
                margin(5.px, LinearDimension.auto)
                background = "#ECEFF1"
                borderRadius = 10.px
            }

            attrs {
                onClickFunction = {
                    props.onClickFunction()
                }
            }


            styledH3 {
                css {
                    margin(0.px)
                    padding(
                            top = 10.px,
                            bottom = 0.px,
                            right = 10.px,
                            left = 10.px
                    )
                }
                +props.channel.longName
            }
            styledDiv {
                css {
                    width = 10.pct
                    minWidth = 50.px
                    display = Display.inlineBlock
                }
                styledP {
                    css {
                        margin(0.px)
                        padding(10.px)
                    }
                    +props.channel.number
                }
            }
            styledDiv {
                css {
                    display = Display.inlineBlock
                }
                styledP {
                    css {
                        margin(0.px)
                        padding(10.px)
                    }
                    +props.channel.shortName
                }
            }
        }
    }
}

fun RBuilder.channelComponent(handler: ChannelProps.() -> Unit): ReactElement {
    return this.child(ChannelComponent::class) {
        this.attrs(handler)
    }
}