/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.games

import org.kitteh.irc.client.library.element.Channel
import xyz.cardstock.cardstock.Cardstock
import xyz.cardstock.cardstock.games.rounds.Round
import xyz.cardstock.cardstock.players.Player

// TODO: KDoc
public abstract class GameWithRounds<PlayerType : Player>(cardstock: Cardstock, channel: Channel) : Game<PlayerType>(cardstock, channel) {

    var currentRound: Round<PlayerType>? = null
        protected set

}
