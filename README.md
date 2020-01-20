game
====

This project implements the game of Breakout.

Name: Maverick Chung mc608

### Timeline

Start Date: 1/15/20

Finish Date: 1/20/20

Hours Spent: Approximately 15 hours, although I didn't really keep track.

### Resources Used
I used the the [official JavaFX documentation](https://docs.oracle.com/javase/8/javafx/api/) extensively. Additionally,
I used these free sprites from [OpenGameArts](https://opengameart.org/content/breakout-brick-breaker-tile-set-free).


### Running the Program

Main class: breakout.Main

Data files needed: Everything in data and levels

Key/Mouse inputs: Move your mouse to move the paddle. The paddle's velocity transfers over to the ball, so if you're
moving the paddle very quickly when the ball hits it, the ball will gain this velocity. Additionally, your score is 
constantly draining. If it drops below zero, you lose a life.

Cheat keys:
- L: Add a life.
- R: Reset ball position. Paddle position does not reset, as it is tied to the mouse position.
- Numbers: Jump to the corresponding level.
- -/_: Decrease the game speed, decreasing score gains. This caps at 1/10th of normal speed.
- =/+: Increase the game speed, increasing score gains. This caps at 5 times normal speed.
- P: Add 1000 to your score
- M: Subtract 1000 from your score

Known Bugs:
- Grabbing two of the same powerup on the same frame will result in only one being used (e.g. grabbing two +1000 powerups
will only grant +1000)
- Not a bug, per se, but it's possible to hit the ball such that the velocity is almost entirely horizontal, forcing the
user to either reset the ball or wait for it to slowly make its way to the top and back down.
- When the level score is 7 digits or more, it will overlap with the life counter.

Extra credit: It seems that I've failed to find the extra credit, despite my searching.


### Notes/Assumptions
I'm assuming that the level files are all in my format, and that there are at least MAX_LEVEL levels. Additionally, I'm 
assuming the user has Courier New on their computers, and that all of the data files are in the right place with the
right names.


### Impressions
I would have loved to spend time to make the game *feel* good, like making the paddle and bouncers bounce slightly on
hit, have the ball velocity slowly curve to be more vertical over time,
or have particle effects on brick destruction, but I didn't have the time.

Additionally, my initial plan is an absolutely monstrous list of tasks. I must have been incredibly overconfident when I
wrote that, because while I *could* implement most of the list without too much refactoring, I don't have the time.

