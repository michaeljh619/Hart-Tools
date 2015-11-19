**********************************************************************
Hart Tools
-----------------------------------------
Author: Michael James Hart
Email: mrhartgames@yahoo.com
-----------------------------------------
This repository contains all the tools I use to make video games with libgdx. Feel
free to use them all you want in you video game making adventures! There are no
requirements for using my code, however I would deeply appreciate two things and
both are completely optional:
1.) Please provide a link to this repository so others can use these tools as well.
2.) Send me an email so I can check out your video game!

If you have any questions on how anything works, feel free to email me.
**********************************************************************

=======
Commits
=======
11/19/15:
- Created "scripts" folder and created standardize.py for use with montage to merge multiple png files into a single strip png file
- Created .gitignore
- Added thumbs.db to .gitignore, to get rid of the horrendous file that pops up everytime viewing icons in the directory

11/08/15:
- Created Background class for renderable backgrounds
- Created ScrollableBackground class for backgrounds that can scroll (Not yet finished)

11/06/15:
- Renamed JoystickCommands 'scale' instance variable to 'normalized' for more readability
- Added updateVectors(float delta) to Sprite for standard vector updates.

11/05/15:
- Renamed "Assets" to "assets"
- Turned GameState into a list of constant int states, removed all deprecated request functions, now a pure static class.
- Made Button into an abstract class, isInRange is now an abstract function so that at the child level the user can define whether the touch is in range of a Circle or Rectangle.
- Created Button_InGame for in game buttons
- Changed "UI" assets directory to "input"

11/04/15:
- Added ID's to timers for freezable functionality.
- Added freeze and unfreeze functionality for Timers, has been tested and works so far with no errors.
- Added Main method to Timer for testing 
- Timer.initialize() must now be called at the beginning of your program to get the ArrayList of freeze ID's to initialize.
- Removed deprecated Task class and code referencing it (was commented out to begin with, is now deleted).
- Deleted all legacy code
- Added Global Reset timer to Logo Mode so assets can load properly
- Settings class now contains Timer IDs
- Fixed Game Class to no longer be abstract, is now instantiated straight from DesktopLauncher, AndroidLauncher, ...

11/03/15:
- Fixed a few errors with not calling Settings, instead SpaceGame was being called (from Space Game project).
- Fixed an error with Assets createRegionsFromTextures method, the array SHOULD be initialized to begin with.
- Assets createRegionsFromTextures no longer initializes the Texture Array.

11/01/15:
- Moved majority of code to legacy
- Timer from tools no longer uses tasks
- Collisions are now handled with collision areas
- Joystick and Button no longer rely on GamePad or InputHandler, directly detect touches from Gdx class.
- Added Settings class for a one stop class for all the major game settings (screen width, height, Joystick directions, etc.)
- Updated heart beat logo to signature logo
- Added backend package with: Rotation, Touch, Debuggable interface, Messages.
- Added mode package for cleaner transitions between game modes
=======
5/2/15:
- Added transparent dark joystick and buttons

4/14/15:
- Added "Assets" tools file to Assets package

4/9/15:
- Added all the latest code used from Twinjas mobile game.
