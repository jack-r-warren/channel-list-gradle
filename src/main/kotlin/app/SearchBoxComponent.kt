package app

import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.js.onInputFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import styled.css
import styled.styledForm
import styled.styledInput

interface SearchBoxProps : RProps {
    var onTextChange: (String) -> Unit
}

class SearchBoxComponent(props: SearchBoxProps) : RComponent<SearchBoxProps, RState>(props) {
    override fun RBuilder.render() {
        styledForm {
            attrs {
                onSubmitFunction = { event: Event ->
                    // We listen to input directly and don't care about form submissions via the Enter key
                    event.preventDefault()
                }
            }

            css {
                textAlign = TextAlign.center
            }

            styledInput {
                attrs {
                    autoComplete = false
                    autoFocus = true
                    type = InputType.search
                    onInputFunction = { event: Event ->
                        // The event is actually a normal HTML event; we know the target will be the source element
                        //      Kotlin's type system doesn't go this far, so it can't disambiguate event targets
                        event.target?.unsafeCast<HTMLInputElement>()?.value?.apply(props.onTextChange)
                    }
                }

                css {
                    background = "#ECEFF1"
                    margin(15.px, 0.px)
                    width = 90.pct
                    maxWidth = 500.px
                    textAlign = TextAlign.center
                    borderRadius = 10.px
                    border = "0px"
                    fontSize = 18.pt
                }
            }
        }
    }
}

fun RBuilder.searchBoxComponent(handler: SearchBoxProps.() -> Unit): ReactElement {
    return this.child(SearchBoxComponent::class) {
        this.attrs(handler)
    }
}