package net.minecraft.src;

import java.lang.reflect.Field;

public class mod_AnnoyanceFix extends BaseMod {

	@Override
	public String Version() {
		return "AnnoyanceFix v1.4";
	}

	@Override
	public void ModsLoaded() {
		// Fix axe effectiveness
		addEffectiveTools(new Item[] { Item.axeDiamond, Item.axeGold, Item.axeSteel, Item.axeStone, Item.axeWood },
				new Block[] { Block.workbench, Block.stairCompactPlanks, Block.fence, Block.doorWood, Block.ladder,
						Block.signPost, Block.signWall, Block.pumpkin, Block.pumpkinLantern, Block.pressurePlatePlanks,
						Block.jukebox, Block.musicBlock });

		// Fix pickaxe effectiveness
		addEffectiveTools(
				new Item[] { Item.pickaxeDiamond, Item.pickaxeGold, Item.pickaxeSteel, Item.pickaxeStone,
						Item.pickaxeWood },
				new Block[] { Block.stoneOvenActive, Block.stoneOvenIdle, Block.stairCompactCobblestone, Block.brick,
						Block.oreRedstone, Block.oreRedstoneGlowing, Block.doorSteel, Block.rail, Block.railDetector,
						Block.railPowered, Block.dispenser, Block.pressurePlateStone, Block.mobSpawner,
						Block.obsidian });

	}

	/**
	 * Makes specified blocks break faster using specified tools
	 * 
	 * @param effectiveTools   tools to be effective against all vulnerableBlocks
	 * @param vulnerableBlocks blocks that should be broken faster using speficied
	 *                         tools
	 */
	public void addEffectiveTools(Item[] effectiveTools, Block[] vulnerableBlocks) {
		try {
			// Get the blocksEffectiveAgainst field
			Field blocksEffectiveAgainstField = ItemTool.class.getDeclaredFields()[0];
			blocksEffectiveAgainstField.setAccessible(true);

			// Make each tool effective against all specified blocks
			for (Item tool : effectiveTools) {
				Block[] blocksEffectiveAgainstOriginal = (Block[]) blocksEffectiveAgainstField.get(tool);

				Block[] blocksEffectiveAgainst = new Block[blocksEffectiveAgainstOriginal.length
						+ vulnerableBlocks.length];

				// Add original effective block list to new array
				System.arraycopy(blocksEffectiveAgainstOriginal, 0, blocksEffectiveAgainst, 0,
						blocksEffectiveAgainstOriginal.length);

				// Add users' effective blocks to the list
				System.arraycopy(vulnerableBlocks, 0, blocksEffectiveAgainst, blocksEffectiveAgainstOriginal.length,
						vulnerableBlocks.length);

				blocksEffectiveAgainstField.set(tool, blocksEffectiveAgainst);
			}

		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
