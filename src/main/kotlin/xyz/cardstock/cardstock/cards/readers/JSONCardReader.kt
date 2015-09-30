/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.cards.readers


import org.json.JSONArray
import org.json.JSONObject
import xyz.cardstock.cardstock.cards.Card
import java.io.File
import java.nio.file.Files

/**
 * Reads cards from a JSON file source. This reader uses a [mapper] to turn a [JSONObject] into a [Card] of type [T].
 *
 * JSON files are expected to be arrays containing objects. A formatted example is below.
 * ```
 * [
 *   {
 *     "suit": "heart",
 *     "pips": 4
 *   },
 *   {
 *     "suit": "club",
 *     "pips": 2
 *   },
 *   ...
 * ]
 * ```
 * @constructor Makes a new JSONCardReader.
 * @param[cardsFile] The JSON file source to be read
 * @param[mapper] The transformer responsible for mapping one [JSONObject] into one [Card] of type [T]. May return null
 * if passed an invalid object. Nulls will be filtered from the resulting list.
 */
public class JSONCardReader<T : Card>(val cardsFile: File, val mapper: JSONObject.() -> T?) : CardReader<T> {

    /**
     * Returns a list containing all of the [Card]s read from the JSON source.
     */
    override public val cards: List<T>
        get() = JSONArray(Files.readAllLines(this.cardsFile.toPath()).join("\n"))
            .map { it as JSONObject }
            .map(this.mapper)
            .filterNotNull()
            .toList()

}
