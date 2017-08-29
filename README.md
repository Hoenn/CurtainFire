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
