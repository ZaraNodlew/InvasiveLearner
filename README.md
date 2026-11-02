# Version 0.0.1

### Current Situation

The non-timer version is the (kinda) original version of the application that the AI made
I did not know that JFrames existed at first and asked them to use html and javascript at first.
But upon finding out, I asked the ai to convert what I had into JFrame Java. 

After asking the AI to implement a timer, the output wasnt what I wanted to I altered the JFrames
and asked the ai the clean up the result. The AI originally implemented an countdown system that 
allowed an inputted time, but I was not for that yet, as I still needed the ability for the program to
get user keyboard inputs in the background and for the application to make sound.

Just now realizing how malicious that sounds, but I will try to make the algorithm so that it only 
detects immense repeated inputs.

# Upcoming:

## The Algorithm:
* Able to detect specific user inputs like m1 positions and keyboard inputs
* Detects for if most m1 positions are in the same range/radius or if most keyboard inputs are in
 recognized video game keybinds (wasd, arrow keys, dfjk (Music Games specificly))
    * Set a recognized limit for detection, first concept for the keyboard input formula would be a 
    check for the minimum of 250 inputs within 2 minutes, and if true, compares the most inputted 
    key inputs (that passed the minimum) with recognized patterns
     (ex: 1000 'w','a','s','d' key presses would trigger a detect while 300 'o' individual presses would not)

## Notifications:
* Still just an idea, no full concept in mind
* Makes noise
* Makes input text into Notification text

## URL Opening:
* There is probably an easy java command that already does this, I will just put it into
* a togglable input Jpanel

# No Longer Implementing:

## Video Overlay Notifcations:
* I feel like there will be too many crashes/problems caused by this, I am being kinda lazy as well
but I think its for the best.

