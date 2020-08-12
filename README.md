# AnnoyanceFix for Minecraft beta 1.7.3
_Now using modloader!_

![image](https://i.imgur.com/TyufQHz.jpg)

Mod review by Modification Station/The Zyga (thanks!): https://www.youtube.com/watch?v=m8lX9jv9lhI

The aim of this mod is to fix (major) annoyances or backport useful features that do not affect gameplay. List of fixes:

- Pickaxes are now effective against: furnaces, cobblestone stairs, bricks, redstone ore, iron doors, rails, dispensers, stone pressure plates and monster spawners
- Axes are now effective against: crafting tables, wooden stairs, fences, wooden doors, ladders, signs, pumpkins, wooden pressure plates, jukeboxes and noteblocks
- Fences are now placeable in air
- Stairs now drop themselves
- Pick block (middle mouse button click) now works for blocks that are in the player's inventory, but not in their hotbar
- Pick block now also works on: wooden doors, iron doors, signs, crops, redstone repeaters and redstone wire

To use pick block: Look at a block and click your scrollwheel. It selects to the block currently looked at (if you have it).

## Installation using MultiMC

1. Download Modloader: https://github.com/coffeenotfound/ModloaderFix-b1.7.3/releases/download/v1.0.0/ModLoader.Fix.b1.7.3-1.0.0.jar
2. Download this mod: https://github.com/2zqa/AnnoyanceFix/releases/latest/download/AnnoyanceFix.zip
3. Create or edit your beta 1.7.3 instance
4. Click "Add to minecraft.jar" and select the Modloader and AnnoyanceFix files
6. Run and enjoy! 👍

## Feedback

Got any suggestions on what I should add next? Feel free to let me know by [creating an issue](https://github.com/2zqa/AnnoyanceFix/issues/new). Know how to code and want to do it yourself? Then look below on how to get started.

## Contributing
Thanks for considering contributing! To get started:

1. Download 1.7.3-LTS (MCP fork): `https://github.com/ModificationStation/1.7.3-LTS/`
2. Fork this repository
3. Clone your own fork: `git clone https://github.com/<your_username>/AnnoyanceFix.git`
4. Run setup from 1.7.3-LTS
5. Add modloader classes to jars/bin/minecraft.jar
6. Run decompile script with fixed class for Modloader
7. Run updatemd5 script
8. Copy all files _from_ 1.7.3-LTS _to_ the AnnoyanceFix folder, without overwriting anything
9. Fix the errors in Eclipse (mostly replacing int with boolean)
   * For example: `int j = this.furnaceBurnTime > 0;` to `boolean j = this.furnaceBurnTime > 0;`
   * Remove the int in `int j2 = 4;` in RenderBlocks.java
   * Warnings can be ignored
10. Start modding :)
11. Create a pull request when done

TIP: Use the createmod script to automatically recompile, reobfuscate and create a zip for the mod.
