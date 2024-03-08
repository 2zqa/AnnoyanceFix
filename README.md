# AnnoyanceFix for Minecraft beta 1.7.3

_Now using modloader!_

![image](https://i.imgur.com/TyufQHz.jpg)

Mod review of v1.4 by Modification Station/The Zyga (outdated): https://www.youtube.com/watch?v=m8lX9jv9lhI

Also included in the Beta Unleashed Modpack by no_mic_. Trailer: https://www.youtube.com/watch?v=RSLuGK0yHrw

## Description

The aim of this mod is to fix (major) annoyances or backport useful features that do not affect gameplay. List of fixes:

- Pickaxes are now effective against: furnaces, cobblestone stairs, bricks, redstone ore, iron doors, rails, dispensers, stone pressure plates and monster spawners
- Axes are now effective against: crafting tables, wooden stairs, fences, wooden doors, ladders, signs, pumpkins, wooden pressure plates, jukeboxes and noteblocks
- Fences are placeable like normal
- Stairs drop themselves
- Boats drop themselves
- Boats don't break due to high velocity
- Never fall through boats anymore when getting out
- Pick block (middle mouse button click) now works for blocks that are in the player's inventory, but not in their hotbar
- Pick block now also works on: paintings, minecarts (all three types), wooden doors, iron doors, signs, crops, redstone repeaters, redstone wire, beds, piston heads and cake

To use pick block: Look at something and click your scrollwheel. If it's in your inventory, it will automatically be selected.

## Installation using Prism Launcher

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
