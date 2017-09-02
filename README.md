# CurtainFire

Touhou Style shoot 'em up. Features a Pokemon themed overworld to get between battles. 
Utilizes a custom scripting language to easily program enemy actions and LuaJ for scripting bullet patterns.

LuaJ: http://www.luaj.org/luaj/3.0/README.html

![alt tag](https://raw.githubusercontent.com/Hoenn/CurtainFire/master/cf-scrn-0.png)

![alt-tag](https://raw.githubusercontent.com/Hoenn/CurtainFire/master/cf-scrn-1.png)

## Building
### Get the repository

`$ git clone https://github.com/Hoenn/CurtainFire.git`

You should make a fork of this repository and clone that instead of you're uninterested in contributing to this project (no pressure!)

### Workspace

Open Eclipse with a workspace you'd like CurtainFire code to live in.

To have Eclipse recognize the repository as a project:

`File -> Import -> Existing projects into workspace`

and navigate to the directory you cloned to.

### Build Path

Once you have the project imported into your workspace they'll be a few errors, we can fix them by setting up the build path for the project manually.

Right click `CurtainFire-desktop`, hover over `Build Path`, and click `Configure Build Path`.

In the window that pops up we need to make a few changes:
- Add `CurtainFire-core` as a project in the Projects tab
- In the Libraries tab, `Add Jars` and select the `CurtainFire-desktop/libs` folder
- Also be sure to add the jars from `CurtainFire-core/libs` folder
- Ensure that in the Source tab `CurtainFire-desktop/src` and `/user_assets` appear

For the `CurtainFire-core` build path:
- Add `CurtainFire-core/libs` jars in the Libraries tab
- Make sure `CurtainFire-core/src` and `/assets` appear in the Source tab

### Running
After the setup you're good to go. Select the `CurtainFire-desktop/src/DesktopLauncher.java` file in Eclipse and press `Ctrl-F11` to run. 

See the sections about modifying tilemaps and creating battle scripts for more detail about how to leverage our systems to make your own game levels.


## Map Making
To make new maps for the game you'll need to get [Tiled](http://www.mapeditor.org/). The easiest way to get up and running on a new map is to open an existing map and `Save As` something else, so that you'll have the proper layers and tilesets required.

### Layers
There are 5 layers in Tiled that are needed to run the map. Object, Low, Special, Medium, High. These layers allow for z-dimension distinctions of tiles, provide an entire layer for collision detection instead of binding certain tiles as "walls", and the ability to customize Battle sequences through the map builder
#### Object
Create objects with the `BATTLE` property and lay them ontop of a `B` in the Special Layer. This Object provides all the information needed to begin a battle such as the starting script, enemy overworld sprite and their direction, and the enemy battle sprite.
#### Low
Anything the player can stand on-top of such as grass, flowers, and dirt.
#### Special
This layer determines collision, player spawn, and enemy sprite spawns. Use the `specialTileset` symbols to have a nice marker in your map. When map making it might be useful to move the Special layer to the top of the layerlist to get a good view of collision, make sure to move it back before playing!
#### Medium
This is a good layer to put tiles that should appear above the ground, such as the base of a tree, while still not appearing above the player or enemy sprites.
#### High
This layer is rendered last so put tiles here that the player should be drawn under, such as the canopy or the tip of a tree.

### Tileset
The best way to extend the tileset is to just modify `tileset.png` with your new tile images. The tiles are 16x16px.

# CFL Docs

## Summary

CFL scripts are attached to a trainer battle in CurtainFire. They control the construction and direction of actors on the screen. They work in tandem with `lua` scripts which describe bullet behavior to provide fast, iterative, and most importantly run-time safe development. Each command in a CFL script must be on its own separate line.

## Example Script

This is the code from `butterfree.cfl` located in `CurtainFire-desktop/user_assets/`

```
construct Butterfree_1.png Butterfree_2.png 0.2f 200 end
shoot flower1.lua 0 on end
wait 10 end
shoot 360div10.lua 0 on end
-- move commands are percentage of x and y (out of 100)
move 25 def 3.0 swing end
move 75 def 6.0 swing end
move def def 3.0 swing end
```

## Keywords

### `construct`

`construct image1 image2 animationSpeed hitpoints end`

`construct` is required to be the first line of any script.

*Image* `image1`: First animation frame image, should be located in `CurtainFire-desktop/user_assets/enemy`

*Image* `image2`: Second animation frame image, should be located in `CurtainFire-desktop/user_assets/enemy`

*float* `animationSpeed`: A floating point number that is the time between each frame

*int* `hitpoints`: Enemy health

###  `move`

`move xPos yPos time interpolationFunction end`

`move` moves the current position of the constructed enemy. 

*float* `xPos`: The percentage x position to move to (0 - 100)
    
*float* `yPos`: The percentage y position to move to (0 - 100)

*float* `time`: The time elapsed duration travel between the current and target position, a floating point

*string* `interpolationFunction`: Uses Libgdx's [interpolation keywords](https://github.com/libgdx/libgdx/wiki/Interpolation) in order to utilize libgdx interpolated movement. Use `linear` if no interpolation function desired

#### Special Arguments

`rand(float)`: Can be passed in inplace of `xPos` or `yPos`

Example: `rand50` passes a value between -50 and 50 to move by from the enemy's current position

`def`: Can be passed in inplace of `xPos` or `yPos`

`def` moves the enemy back to the x or y position that it spawns in by default. (Center-Top of screen)

### `wait`

`wait seconds end`

`wait` halts on current line for the desired amount of time

*float* `seconds`: The amount of time to wait

### `shoot`

`shoot lua_file.lua index state end`

`shoot` begins the execution of a lua script used to create bullet patterns

*int* `index`: index into array that holds each declared lua script.

*"on"|"off"* `state`: *"on"* adds or sets a script file at `index` and *"off"* removes a script at `index`

### `pushshoot`

`pushshoot lua_file.lua end`

`pushshoot`: begins the execution of a lua script used to create bullet patterns. (Adds to the end of the array that holds each currently running lua script). Looks for lua files in user_assets/lua.

### `removeallscripts`

`removeallscripts end` 

`removeallscripts`: Every lua script that is currently running will terminate and be removed, clearing the array holding all lua scripts declared by either `shoot` or `pushshoot`.

### `print`

`print "example"`

`print`: prints all text after the command on the same line into the java console. Useful for debugging.

### `loop`

```
loop iteration
print code would be in here
endloop end 
```

`loop` runs only the code between `loop` and `endloop` `iteration` number of times.

*int* `iteration`: number of iterations that following code will repeat



### Comments

`--` is the identifier to begin a line of comments

`-*` comment block start

`*-` comment block end

Comment block start and end must be on a separate line from the code they are enclosing.

# Lua

```lua
local bulletTimer = 0;

local num = 10; 
local changeangle1 = 360 / num;
local angle1 = 60;

--reference com.neet.cf.script.entities.Enemy and com.neet.cf.script.entities.Player for usable functions
function tick(enemy, delta, player) --each lua script requires tick(enemy, delta, player) function
    if bulletTimer > 0.20 then
        for i=1, num, 1 do
            enemy:shoot(10, angle1, 250, 1); --radius, angle, speed, index
            angle1 = angle1 + changeangle1;
        end
        angle1 = 60;
        bulletTimer = 0;
    end
    bulletTimer = bulletTimer + delta;
end
```
tick is called every frame of gameplay.

Lua scripts share variable name space if multiple scripts are running at the same time.
