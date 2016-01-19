# ud-breakout
Project 1 of Udacity's 2D Game Development Nanodegree program using LibGDX

Quiv's Breakout!!!

What is it?
===========
A re-creation of the popular 'Breakout' game, where the player controls a paddle 
with which to bounce a ball off of. The player bounces the ball off coloured blocks
and tries to clear them all within the time limit and with a certain number of lives.

How To Play
===========
Click or touch a difficulty button to play a game at that difficulty.

On Desktop:
  - Left mouse click to select difficulty and to transition between win/lose screens 
    and the main menu
  - Left/Right arrow keys to move the paddle in the play area
  - Spacebar to launch the ball from the paddle at a 45 degree angle
  - Left mouse click to launch the ball at the clicked position
  
On Mobile:
  - Touch to select difficulties, transition between screens and the main menu
  - Tilt the phone left/right to move the paddle
  - Touch to launch the ball to that position

How To Win
==========
Clear all of the blocks off the screen before the time limit expires or you run out
of lives

Scoring
=======
  - Green Blocks =    100 points
  - Yellow Blocks =   200 points
  - Blue Blocks =     350 points
  - Red Blocks =      500 points

Based on the difficulty, these point values are modified:
  - Easy (Breeze):        0.80x
  - Medium (Gust):        1.00x
  - Hard (Storm):         1.20x
  - Insane (Hurricane):   1.50x

Combo Scoring
=============
Every block you hit increments your combo counter by 1 for that colour. If you 
hit a different coloured block, the combo counter is reset. You receive points
equal to the block value times the combo counter, so to maximize your score,
try to hit as many blocks of the same colour in a row as you can!

ex: A combo of 2 when hitting a red block will increment the counter to 3,
    then award 3 x 500 = 1500 points for that block!

Powerups
========
Four different kinds of Powerups can spawn, with many different effects:
  - Red "M" - Multiball
    - Splits every ball onscreen into 3 extra balls
  - Yellow "W" - Widen
    - Doubles the width of the paddle for a limited time
  - Green "P" - Unstoppa-ball
    - Makes all balls on screen plow straight through blocks without bouncing
  - Blue "S" - Sticky Pad
    - When the ball hits the paddle, it sticks so it can be manually launched

Game Over
=========
When you clear the screen, you are awarded a clear bonus depending on the
difficulty:

  - Easy (Breeze):        4000 points
  - Medium (Gust):        7500 points
  - Hard (Storm):         10000 points
  - Insane (Hurricane):   15000 points

As well, you see your score and the current game's top score! 
Click or tap the screen to play again.
