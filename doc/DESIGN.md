# Game - Design
#### Maverick Chung mc608 - designer, frontend, backend

## Design Goals
I wanted specifically for new bricks to be easy to implement. This is the case to some extent, as the abstract brick
class is easily extendable for any shape or type of brick. However, to actually load in a new type of brick, the 
format for reading it from the file must be defined in a switch/case statement SceneHandler.readBricks(), which is far
from ideal.

Additionally, I wanted to be able to have multiple of any type of object. Right now, there is only one ball and one paddle
at any given time, but the current update loop iterates through a list of balls and paddles, meaning that powerups
that add more balls or paddles can just add to those lists to have their object update.

Finally, I wanted for the user to have a significant degree of control over how they move the ball at low speed,
and for them to lose that control as the ball sped up. This is also partially accomplished, as the user can knock the
ball in a surprisingly precise direction to start, and once the ball is moving quickly, it is difficult to slow or stop
the ball due to the tendency to move the paddle very quickly to catch the ball. The largest failure in accomplishing this
is that, for unknown reasons, the paddle/ball collision is not symmetrical - knocking the ball to the left tends to send
it on a largely vertical path, while knocking the ball to the right sends it on a horizontal path.

## High Level Design
The Main class is the main class of the application, where it starts. It then calls for the menu scene from the
SceneHandler, which is a mostly static class that generates the various scenes in the game. All of the scenes except
the level scenes have buttons which change the displayScene instance variable in Main, changing the scene.

After the user chooses to start playing, the SceneHandler reads the data from the text files in the levels folder. These
text files are space-separated grids, where each brick is represented by two characters. The first character corresponds
to the type of brick, and the second character corresponds to some information about that brick (health or size).

The SceneHandler then loads these bricks into a list in Main, as well as loading in a ball and paddle. Main.levelUpdate()
then updates these according to the game physics. When the ball collides with the paddle, the paddle's velocity is added
to the ball, causing it to change course. When a brick breaks, it has a 50% chance to drop a powerup, which is an extra
life, a score bonus, or a score penalty. When the ball touches the bottom, the player loses a life. Additionally, the
score decrements by 1 each frame, and if the score drops below zero, the player loses a life and gains 500 score.

When the user beats all of the levels, or dies, the scores from all the levels are added, and a 500 point bonus is added
for every extra life. (If the player plays certain levels multiple times using the cheat keys, all of these scores will
be added, not just the highest.) This score is then displayed, and the user can click the button to back to the menu.

## Assumptions
The biggest assumption that I made is that all of my inputs are valid - all the files are in the correct places with the
correct names, the user's computer has the font Courier New, I never construct an object with bad inputs, I never make
a button with more text than it can hold, the level files are in the correct format, and so on. 
In essence, I'm wildly and recklessly tempting a *"But it works on
my machine..."* error. I did not spend much time working on error handling, so I don't know what happens if any of these
assumptions are violated.

I also assume the user doesn't maximize the screen. The game continues working as intended, but it does not expand to
fit the new screen size, which can make playing the game more difficult, as the right wall is no longer clearly defined.

Another assumption that I made early in the design process was that I would want to be able to make bricks of any shape,
which caused me to make the Brick abstract class extend Shape, allowing me to have both rectangular and circular bricks
(as well as presumably any other type of brick with relatively minimal coding).

Finally, as noted above, I assumed I would want to have any number of any type of object, leading me to store a list of
all types of object, even for objects which there are only one of for the time being (e.g. Paddle).

## Adding New Features
### Brick
Create a class that extends Brick or one of the classes that extends Brick. In SceneHandler.readBricks(), in the for loop,
add in an additional case that creates a Brick according to your class' constructor when a certain character is read.

### Level
Create a file of the form leveln.txt, and add it to the levels file. Change Main.MAX_LEVEL to represent the new highest
level.

The file should be a space separated grid of two character strings. The first row should just have a number of string
equal to the number of bricks you want across the screen (e.g. "== == ==" for 3 bricks). From there, each newline is a
new row of bricks.

The brick codes are:
* 's' - creates a rectangular brick. The second character is its health.
* 'o' - creates a circular brick. The second character is its health.
* 'b' - creates a bumper. The second character is its size.
* 't' - creates a teleporting brick. The second character indicated the portal's pair. For example, 'ta' will pair with
another 'ta' portal.

Any string that begins with something other than these characters gets ignored, creating a gap in the bricks.

The method does not perform any input validation, so actions including but not limited to placing bricks offscreen, 
placing bricks inside bumpers, giving bricks non-integer sizes, or creating portals without pairs will almost certainly
result in either a crash or unbeatable levels, or both.

### Powerup
Add the id string to the Main.POWERUP_CODES array. Add the corresponding image to the powerups folder. Add a case in
Main.activatePowerup() to run when the id is received. If the powerup has a duration, such as a temporarily longer
paddle, add a case in Main.deactivatePowerup() to deactivate the powerup when the duration expires.