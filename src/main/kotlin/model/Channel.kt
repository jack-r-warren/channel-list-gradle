package model

data class Channel(val number: String, val shortName: String, val longName: String, val recast: Boolean)

/**
 * Declaring this as an receiver function allows it to be imported selectively and gives
 * it better visibility in the JavaScript (Kotlin can transpile the reference to this
 * better)
 */
fun Channel.matches(searchTerm: String): Boolean {
    return number.contains(searchTerm, true) ||
            shortName.contains(searchTerm, true) ||
            longName.contains(searchTerm, true)
}