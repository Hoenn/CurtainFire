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



