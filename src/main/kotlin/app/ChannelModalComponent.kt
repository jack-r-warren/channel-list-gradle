package app

import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.border
import kotlinx.html.js.onClickFunction
import model.Channel
import react.*
import react.dom.b
import react.dom.br
import styled.*

interface ChannelModalProps : RProps {
    var isOpen: Boolean
    var onRequestClose: () -> Unit
    var channel: Channel?
}

class ChannelModalComponent(props: ChannelModalProps) : RComponent<ChannelModalProps, RState>(props) {
    override fun RBuilder.render() {
        styledDiv {
            css {
                display = if (props.isOpen) Display.block else Display.none
                position = Position.fixed
                zIndex = 1
                left = 0.px
                top = 0.px
                width = 100.pct
                height = 100.pct
                overflow = Overflow.auto
                backgroundColor = Color.black
                backgroundColor = Color.black.withAlpha(0.4)
            }
            attrs {
                onClickFunction = {
                    props.onRequestClose()
                }
            }

            styledDiv {
                css {
                    width = 75.pct
                    maxWidth = 500.px
                    textAlign = TextAlign.left
                    backgroundColor = Color("#ECEFF1")
                    borderRadius = 10.px
                    margin(15.pct, LinearDimension.auto)
                    padding(20.px)
                    border(1.px, BorderStyle.solid, Color("#455A64"))
                }

                attrs {
                    onClickFunction = {
                        it.stopPropagation()
                        it.preventDefault()
                    }
                }

                styledSpan {
                    css {
                        color = Color("#aaaaaa")
                        float = Float.right
                        fontSize = 28.px
                        fontWeight = FontWeight.bold

                        hover {
                            color = Color.black
                            textDecoration = TextDecoration.none
                            cursor = Cursor.pointer
                        }

                        focus {
                            color = Color.black
                            textDecoration = TextDecoration.none
                            cursor = Cursor.pointer
                        }
                    }
                    attrs {
                        onClickFunction = {
                            props.onRequestClose()
                        }
                    }
                    +"×"
                }
                styledH1 {
                    css {
                        marginTop = 20.pt
                    }

                    props.channel?.let {
                        +it.longName
                    }
                }
                styledDiv {
                    css {
                        margin(LinearDimension.auto)
                        width = LinearDimension.fitContent
                    }

                    styledP {
                        css {
                            margin(0.pt)
                        }

                        props.channel?.apply {
                            +"\"Alexa,"
                        }
                    }

                    styledP {
                        css {
                            margin(0.pt, 0.pt, 20.pt, 30.pt)
                            textAlign = TextAlign.right
                            lineHeight = LineHeight("20pt")
                        }

                        props.channel?.let {
                            +"... watch "
                            b {
                                if (it.recast) +it.longName
                                else +it.shortName
                            }
                            +"\""
                            br {}
                            +"... set the channel to "
                            b {
                                +it.number
                            }
                            +"\""
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.channelModal(handler: ChannelModalProps.() -> Unit): ReactElement {
    return this.child(ChannelModalComponent::class) {
        this.attrs(handler)
    }
}