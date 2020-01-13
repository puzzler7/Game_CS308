# Game Plan
## Maverick Chung mc608
### Breakout Variant
I loved how clean and crisp Jet Ball was - the bricks moved around in ways that were almost blasphemous to the
standard breakout, as even the square bricks moved around slightly in their grid. However, it didn't feel or look
*bad*, the movements were aesthetically pleasing.

I also very much liked the visual chaos of Centipong, with all the balls flying about.

### General Level Descriptions
I'll have a traditional layout, with a paddle at the bottom of the screen and bricks above.

Below are some sample level layouts.
```
xxxxxxx      x x x x     xxxxxxx     xxxxxxx                       
xxxxxxx       x x x       o   o            x        
xxxxxxx      x x x x     =======     x=====x                      
                          o   o      x                              
                         xxxxxxx     xxxxxxx                                                               
   _            _           _           _          
```

An `x` is a standard brick, the `_` represents the paddle, the `=` is a high health brick, and the `o` is a 
teleporter.


### Bricks Ideas
- Bricks that break in one hit
- Bricks that break in N hits
- Bricks that explode, destroying/damaging the bricks around them
- Bricks that spawn new bricks if not destroyed (like a growing virus)
- A brick that teleports the ball to a new location, possibly to another teleporting brick (like a portal)
- Extra balls that are trapped in by bricks
- Invincible (or high health) bricks that don't need to be cleared
- Bricks that need to be cleared but leave invincible bricks behind

### Power Up/Down Ideas
Some of these are helpful, some are harmful.
- Shield: Prevent balls from falling out of the screen
- Laser: Fire lasers from the platform
- Plasma Ball: The ball travels through blocks and destroys them instead of bouncing off
- Increase/decrease platform length
- Screen wrap: the balls will warp to the other side of the screen, rather than bouncing off of a wall (a la PAC-MAN)
- Toggle gravity: The ball will accelerate towards the bottom of the screen for some time
- Make mirrored platform: In addition to the usual platform, another platform appears on the opposite side of the
screen (e.g. if the platform is on the left, there's another on the right)
- Tangible balls: balls will bounce off of other balls
- Brick health up: bricks onscreen will require more hits to break
- Increase or decrease score

### Cheat Key Ideas
In addition to the required cheat keys, I'll add keys to increase and decrease the game speed. Going at a faster speed
will increase score gains, but will also be much more difficult. I'll also add a cheat key for every powerup/powerdown I
implement.

### Something Extra
For my extra feature, I'll have the mouse control the platform, and the speed at which the platform is traveling 
will transfer to the balls that are being caught (e.g. moving sharply to catch the ball will cause the ball to move
quickly as a result, while catching the ball with a stationary paddle will slow it down).
Additionally, breaking N bricks will spawn a new ball, requiring the player to quickly move to catch the balls,
increasing their speed.

Additionally, the score for each level will start at some value, and decrease over time. The score can be increased by
breaking bricks, but if the score reaches zero, the player loses a life and gains some score.

I think this is a substantial addition because it requires the player to play "quickly", and not slowly and safely
clear the board.
