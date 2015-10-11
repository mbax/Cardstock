/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.implementations.games

import xyz.cardstock.cardstock.games.GameRegistrar
import xyz.cardstock.cardstock.implementations.DummyCardstock
import xyz.cardstock.cardstock.implementations.players.DummyPlayer

class DummyGameRegistrar(cardstock: DummyCardstock) : GameRegistrar<DummyCardstock, DummyPlayer, DummyGame>(cardstock, { cardstock, channel -> xyz.cardstock.cardstock.implementations.games.DummyGame(cardstock, channel) })
