****************
Three Card Monte
****************

DESCRIPTION:
------------
A simple Java/JavaFX program for playing (fair) three card monte.
Just made this for fun.

NOTE:
This program depends on JavaFX for the graphical interface. The JavaFX
dependencies are not included with the jar file. If you try to run the
program and get "Error: could not find class javafx/application/Application"
or something similar then you probably do not have JavaFX installed.

USAGE:
------
1. Download/compile. Download the jar file or download the source code and
   compile it yourself with "javac Main.java".
2. Run. The Main class is the entry point for starting the game.
   "java -jar ThreeCardMonte.jar" will run the jar file.
   "java Main" will run the compiled source code.
   A small window should open with the cards and game controls. If you are
   putting the code together yourself, make sure the "images" folder is in the
   same directory as the class files.

GAMEPLAY:
---------
In its most basic form, a game of three card monte consists of a dealer,
a player, a playing surface (the floor or a table) and three cards or disguisable
objects (flipping a card over disguises it). At least one of the cards needs to
be distinct from the other two. In this implementation there are two aces of
clubs and one queen of hearts. At the begining of the game, the cards are placed
face-up on the playing surface. The player then takes note of the location of the
target card (the queen of hearts in this case). The cards are then flipped over
and shuffled by the dealer on the playing surface in front of the player. As the
cards are shuffled, the player tries to keep track of where the target card is
moved. After the shuffling stops, the player guesses where the target card ended
up. If the player guesses correctly, the player wins. If not, the player loses.

In real life, three card monte is usually used to trick people into losing money.
The dealer shuffles at a medium-fast speed but uses sleight of hand to make the
player think the target card was placed somewhere it wasn't. In this computer
implementation, the only sleight of hand is speed. There is no other funny
business happening with the cards.

There are in-game settings for the number of swaps in one shuffle (1-25) and
the shuffle speed (slow, medium, fast, and superfast). Fast is doable but can be 
tricky, especially for shuffles with many swaps. The superfast speed is meant to
make it impossible to follow the queen, so your chance of winning is 1/3.

