# ![Cardstock](https://cardstock.xyz/images/long.svg)
[![Travis CI](https://img.shields.io/travis/Cardstock/Cardstock.svg)](https://travis-ci.org/Cardstock/Cardstock)
[![Codecov](https://img.shields.io/codecov/c/github/Cardstock/Cardstock.svg)](https://codecov.io/github/Cardstock/Cardstock)
[![License](https://img.shields.io/github/license/Cardstock/Cardstock.svg)](https://github.com/Cardstock/Cardstock/blob/master/LICENSE)
[![VersionEye](https://www.versioneye.com/user/projects/561a809ba193340f320006f4/badge.svg?style=flat)](https://www.versioneye.com/user/projects/561a809ba193340f320006f4)
[![Gratipay](https://img.shields.io/gratipay/jkcclemens.svg)](https://gratipay.com/~jkcclemens/)

*Cardstock is to bots as cardstock is to cards.*

Cardstock is a framework for card games on IRC.

Cardstock allows for card games to be facilitated and hosted by bots in various channels on IRC networks, giving users
on those channels easy access to various card games.

**Note**: Cardstock is in active development. Large parts of the codebase may change at any time. Some features may be
missing. As I port some of my older card game bots to Cardstock, what is missing and what needs to be changed will
become more apparent.

## How it works

Cardstock handles most of the IRC work in the background, connecting to various servers and channels based on the
configuration. It does the bootstrapping to cut down on time to make a new bot.

Cardstock provides the base classes for making games. Most classes will need to be extended by new bots, since card
games tend to be so nuanced.

## FAQ

### What is this insane language you're using?

It's called [Kotlin](http://kotlinlang.org/). It is 100% compatible with Java.

### So can I use this in Java?

Absolutely. This library can be used in Java and Kotlin for sure. It probably works with other JVM languages, as well.

### How do I get started?

Create a class that extends `Cardstock`. This is the bot class. Once you have configured the bot, call
`Cardstock#start()` to finish configuration and connect to IRC.

At this point, your bot should officially be online. You can then create commands (extending `BaseCommand`) and register
them in the `CommandRegistrar`, which is available through your bot class.

You should extend the `Game` class or one of its subclasses to get your game set up. `Game`s are `Stateful`, meaning
that they always have a `State`. The way that games progress is by advancing the current state.

In many cases, it is not necessary to implement `State` for every state that a game can have. Simply make an enum of
possible states, then use the extension function `Enum#toState()`, which is provided by Cardstock. In order for
`Enum#toState()` to work properly, the enum must list the states of the game in order.

To monitor changes in state (which are initiated by `Game#advanceState()`, generally), register state listeners by
adding them to `Stateful.stateListeners` (`Game` and its subclasses implement `Stateful`).

Generally, in games with rounds, there will be one state that the game stays in while `Round`s are playing out. Every
`Round` is also `Stateful`, and they work in a similar fashion to `Game`s.

### Does Cardstock support multiple IRC servers?

Yes! Multiple server support is baked into the core of Cardstock. Simply set up the configuration file with multiple
server objects, and Cardstock will try its darndest to connect to them.

---

Oh, you made it down here. Nice. If you want to support Cardstock, there's [Gratipay](https://gratipay.com/~jkcclemens/)
above, or you can try out [Salt](https://salt.bountysource.com/teams/cardstock_irc), which has rewards.
