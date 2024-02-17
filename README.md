````
   ____                    _   ____  _                   
  / ___|_      _____ _ __ | |_/ ___|| |_ ___  _ __   ___ 
 | |  _\ \ /\ / / _ \ '_ \| __\___ \| __/ _ \| '_ \ / _ \
 | |_| |\ V  V /  __/ | | | |_ ___) | || (_) | | | |  __/
  \____| \_/\_/ \___|_| |_|\__|____/ \__\___/|_| |_|\___|
  
````
----------------------------------------------------------------
Description
----------------------------------------------------------------
This is a barebones object oriented approach to the backend of a
fictional game named GwentStone(inspired by Gwent and HearthStone).
The game GwentStone, played by two players, is simulated through a
series of commands given as input. Each player receives a deck of
cards and a hero, and executes a number of actions in his turn.
During his turn, the player can place a card on the table, use the
ability of one of the cards on the table, attack another card/hero,
use an environment card from his hand, or use his hero's ability,
as long as he has enough mana to do it. At the end of each player's
turn, his cards are unfrozen, if necessary. When both players
finished their turns, a new round starts, meaning both receive a
card from their decks into their hands and mana, which starts
from 1 and increments every round until it reaches 10. The game
ends whenever one of the hero's health becomes 0. Since the AI can
specify input for multiple games, each player remembers how many
games were played and how many of them were won by him.

----------------------------------------------------------------
Structure && Implementation 
----------------------------------------------------------------
1] cards package:
 - The cards package contains the parent class Card which holds
all data related to cards, all their associated methods
(outputCard and outputCard2 add data to the json output while
the other methods get extended by the subclasses)and all the
subclasses.

 - The subclasses that extend the Card class (Berserker,
Disciple, Firestorm, Goliath, HeartHound, Miraj, Sentinel,
TheCursedOne, TheRipper, Warden and Winterfell) implement the
useEnvironmentCardAbility and the useCardAbility methods based
on the type of card and have different effects.



2] heroes package:
 - The heros package contains the parent class Hero which holds
all data related to heroes, all their associated methods
(outputHero and useHeroAbility) and all thesubclasses.

 - The subclasses that extend the Hero class implement the
useHeroAbility based on the hero type and have different
effects.



3] the Debug class contains all methods related to debugging
the game such as: getPlayerDeck, getCardsInHand, getPlayerHero,
getCardsOnTable etc.



4] the Game class is the main entry point of the program,
the "start" method being the one which parses all the input data
(builds the chosen decks, adds the chosen heroes, uses the given
commands in orderd to effectively "play out" the game) and class
all subsequent methods from the other classes.



5] the GameBoard class is where the cards that were played are
stored in the form of 4 rows of cards (I used this
implementationin order to simplify my own visualisation of the
game instead of working with an array of arrays); this class is
also the place where methods concerning rounds and player turns
are found.



6] the Player class has fields that represent their deck, their
current hand, their hero and their current total mana; the 
methods implemented in the Player class (drawCard, addMana,
placeCard and useEnvironmentCard) all manipulate either the
players' deck or their hand to execute fairly obvious commands
based on the methods' names.



7] the Solver class is something I added last minute to detur
myself from programming in a "C-like fashion"; this class
contains so called "solver" methods that add the required data
(including error messages) into the output nodes (for later
JSON printing) and then call the respective fuctions that
implement the functionality of said commands.

----------------------------------------------------------------
Feedback && Comments
----------------------------------------------------------------
This was a great project that I had lots of fun working on.

The concept behind the game mechanics and story were very
interesting and as a fan of strategy games I found myself loving
developing this hybrid turn-based card strategy game.

As for challanges I faced along the way, what posed the biggest
problem for me was trasitioning from C programming to Java.
I still haven't grasped the concepts of Object Oriented
Programming fully as I think it is apparent in this project, but
I soon hope to improve. 
