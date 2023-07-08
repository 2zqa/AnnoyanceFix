package net.minecraft.src;

public class InventoryPlayerUtils {

	public void setCurrentItem(EntityPlayerSP player, MovingObjectPosition hoveredObject) {
		int itemID = 0;
		int itemDamage = 0;
		World world = player.worldObj;
		
		if (hoveredObject.typeOfHit == EnumMovingObjectType.ENTITY) {
			Entity entityLookedAt = hoveredObject.entityHit;
			if (entityLookedAt instanceof EntityPainting) {
				itemID = Item.painting.shiftedIndex;
			} else if (entityLookedAt instanceof EntityBoat) {
				itemID = Item.boat.shiftedIndex;
			} else if (entityLookedAt instanceof EntityMinecart) {
				int minecartType = ((EntityMinecart)entityLookedAt).minecartType;
				if (minecartType == ((ItemMinecart)Item.minecartEmpty).minecartType) {
					itemID = Item.minecartEmpty.shiftedIndex;
				} else if (minecartType == ((ItemMinecart)Item.minecartCrate).minecartType) {
					itemID = Item.minecartCrate.shiftedIndex;
				} else if (minecartType == ((ItemMinecart)Item.minecartPowered).minecartType) {
					itemID = Item.minecartPowered.shiftedIndex;
				}
			}
		} else if (hoveredObject.typeOfHit == EnumMovingObjectType.TILE) {
			itemID = world.getBlockId(hoveredObject.blockX, hoveredObject.blockY, hoveredObject.blockZ);
			
			
			//TODO: find right method for block damage value (e.g. for wool/cloth)
			itemDamage = world.getBlockMetadata(hoveredObject.blockX, hoveredObject.blockY, hoveredObject.blockZ);
			
			if (itemID == Block.redstoneWire.blockID) {
				itemID = Item.redstone.shiftedIndex;
	        } else if (itemID == Block.grass.blockID) {
				itemID = Block.dirt.blockID;
	        } else if (itemID == Block.stairDouble.blockID) {
				itemID = Block.stairSingle.blockID;
	        } else if (itemID == Block.doorWood.blockID) {
				itemID = Item.doorWood.shiftedIndex;
	        } else if (itemID == Block.doorSteel.blockID) {
				itemID = Item.doorSteel.shiftedIndex;
	        } else if (itemID == Block.signPost.blockID || itemID == Block.signWall.blockID) {
				itemID = Item.sign.shiftedIndex;
	        } else if (itemID == Block.crops.blockID) {
				itemID = Item.seeds.shiftedIndex;
	        } else if (itemID == Block.redstoneRepeaterIdle.blockID || itemID == Block.redstoneRepeaterActive.blockID) {
				itemID = Item.redstoneRepeater.shiftedIndex;
	        } else if (itemID == Block.blockBed.blockID) {
				itemID = Item.bed.shiftedIndex;
	        } else if (itemID == Block.cake.blockID) {
				itemID = Item.cake.shiftedIndex;
	        } else if (itemID == Block.pistonExtension.blockID) {
				itemID = Block.pistonBase.blockID;
	        }
		}
		
		int itemLocation = this.getInventorySlotContainItem(itemID, itemDamage, player);
		if (itemLocation == -1) {
			// Item is not in inventory
			return;
		}

		// Check if item is in hotbar
		if (itemLocation < 9) {
			player.inventory.currentItem = itemLocation;
		} else {
			if (getCurrentItem(player) != null) {
				int firstEmptyStack = getFirstEmptyStack(player);
				if (firstEmptyStack < 9 && firstEmptyStack >= 0) {
					player.inventory.currentItem = firstEmptyStack;
				} else {
					// No room in hotbar, swap items
					ItemStack tempItem = player.inventory.mainInventory[player.inventory.currentItem];
					player.inventory.mainInventory[player.inventory.currentItem] = player.inventory.mainInventory[itemLocation];
					player.inventory.mainInventory[itemLocation] = tempItem;
					return;
				}
			}

			// Move item to selected hotbar slot
			player.inventory.mainInventory[player.inventory.currentItem] = player.inventory.mainInventory[itemLocation];
			player.inventory.mainInventory[itemLocation] = null;
		}
	}
	
    private int getFirstEmptyStack(EntityPlayerSP player) {
        for(int var1 = 0; var1 < player.inventory.mainInventory.length; ++var1) {
            if (player.inventory.mainInventory[var1] == null) {
                return var1;
            }
        }

        return -1;
    }
    
    public ItemStack getCurrentItem(EntityPlayerSP player) {
        return player.inventory.currentItem < 9 && player.inventory.currentItem >= 0 ? player.inventory.mainInventory[player.inventory.currentItem] : null;
    }
	
    public int getInventorySlotContainItem(int itemID, int itemDamage, EntityPlayerSP player) {
        for(int i = 0; i < player.inventory.mainInventory.length; ++i) {
            if (player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].itemID == itemID && player.inventory.mainInventory[i].getItemDamage() == itemDamage) {
                return i;
            }
        }

        return -1;
    }
}
