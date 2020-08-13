package net.minecraft.src;

import java.util.List;

public class EntityBoat extends Entity {
    public int boatCurrentDamage;
    public int boatTimeSinceHit;
    public int boatRockDirection;
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;

    public EntityBoat(World var1) {
        super(var1);
        this.boatCurrentDamage = 0;
        this.boatTimeSinceHit = 0;
        this.boatRockDirection = 1;
        this.preventEntitySpawning = true;
        this.setSize(1.5F, 0.6F);
        this.yOffset = this.height / 2.0F;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void entityInit() {
    }

    public AxisAlignedBB getCollisionBox(Entity var1) {
        return var1.boundingBox;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public boolean canBePushed() {
        return true;
    }

    public EntityBoat(World var1, double var2, double var4, double var6) {
        this(var1);
        this.setPosition(var2, var4 + (double)this.yOffset, var6);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = var2;
        this.prevPosY = var4;
        this.prevPosZ = var6;
    }

    public double getMountedYOffset() {
        return (double)this.height * 0.0D - 0.30000001192092896D;
    }

    public boolean attackEntityFrom(Entity var1, int var2) {
        if (!this.worldObj.multiplayerWorld && !this.isDead) {
            this.boatRockDirection = -this.boatRockDirection;
            this.boatTimeSinceHit = 10;
            this.boatCurrentDamage += var2 * 10;
            this.setBeenAttacked();
            if (this.boatCurrentDamage > 40) {
                if (this.riddenByEntity != null) {
                    this.riddenByEntity.mountEntity(this);
                }

                this.dropItemWithOffset(Item.boat.shiftedIndex, 1, 0.0F);

                this.setEntityDead();
            }

            return true;
        } else {
            return true;
        }
    }

    public void performHurtAnimation() {
        this.boatRockDirection = -this.boatRockDirection;
        this.boatTimeSinceHit = 10;
        this.boatCurrentDamage += this.boatCurrentDamage * 10;
    }

    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    public void setPositionAndRotation2(double var1, double var3, double var5, float var7, float var8, int var9) {
        this.boatX = var1;
        this.boatY = var3;
        this.boatZ = var5;
        this.boatYaw = (double)var7;
        this.boatPitch = (double)var8;
        this.boatPosRotationIncrements = var9 + 4;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    public void setVelocity(double var1, double var3, double var5) {
        this.velocityX = this.motionX = var1;
        this.velocityY = this.motionY = var3;
        this.velocityZ = this.motionZ = var5;
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.boatTimeSinceHit > 0) {
            --this.boatTimeSinceHit;
        }

        if (this.boatCurrentDamage > 0) {
            --this.boatCurrentDamage;
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        byte var1 = 5;
        double var2 = 0.0D;

        for(int var4 = 0; var4 < var1; ++var4) {
            double var5 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var4 + 0) / (double)var1 - 0.125D;
            double var7 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var4 + 1) / (double)var1 - 0.125D;
            AxisAlignedBB var9 = AxisAlignedBB.getBoundingBoxFromPool(this.boundingBox.minX, var5, this.boundingBox.minZ, this.boundingBox.maxX, var7, this.boundingBox.maxZ);
            if (this.worldObj.isAABBInMaterial(var9, Material.water)) {
                var2 += 1.0D / (double)var1;
            }
        }

        double var6;
        double var8;
        double var10;
        double var21;
        if (this.worldObj.multiplayerWorld) {
            if (this.boatPosRotationIncrements > 0) {
                var21 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
                var6 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
                var8 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;

                for(var10 = this.boatYaw - (double)this.rotationYaw; var10 < -180.0D; var10 += 360.0D) {
                }

                while(var10 >= 180.0D) {
                    var10 -= 360.0D;
                }

                this.rotationYaw = (float)((double)this.rotationYaw + var10 / (double)this.boatPosRotationIncrements);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
                --this.boatPosRotationIncrements;
                this.setPosition(var21, var6, var8);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            } else {
                var21 = this.posX + this.motionX;
                var6 = this.posY + this.motionY;
                var8 = this.posZ + this.motionZ;
                this.setPosition(var21, var6, var8);
                if (this.onGround) {
                    this.motionX *= 0.5D;
                    this.motionY *= 0.5D;
                    this.motionZ *= 0.5D;
                }

                this.motionX *= 0.9900000095367432D;
                this.motionY *= 0.949999988079071D;
                this.motionZ *= 0.9900000095367432D;
            }

        } else {
            if (var2 < 1.0D) {
                var21 = var2 * 2.0D - 1.0D;
                this.motionY += 0.03999999910593033D * var21;
            } else {
                if (this.motionY < 0.0D) {
                    this.motionY /= 2.0D;
                }

                this.motionY += 0.007000000216066837D;
            }

            if (this.riddenByEntity != null) {
                this.motionX += this.riddenByEntity.motionX * 0.2D;
                this.motionZ += this.riddenByEntity.motionZ * 0.2D;
            }

            var21 = 0.4D;
            if (this.motionX < -var21) {
                this.motionX = -var21;
            }

            if (this.motionX > var21) {
                this.motionX = var21;
            }

            if (this.motionZ < -var21) {
                this.motionZ = -var21;
            }

            if (this.motionZ > var21) {
                this.motionZ = var21;
            }

            if (this.onGround) {
                this.motionX *= 0.5D;
                this.motionY *= 0.5D;
                this.motionZ *= 0.5D;
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            var6 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (var6 > 0.15D) {
                var8 = Math.cos((double)this.rotationYaw * 3.141592653589793D / 180.0D);
                var10 = Math.sin((double)this.rotationYaw * 3.141592653589793D / 180.0D);

                for(int var12 = 0; (double)var12 < 1.0D + var6 * 60.0D; ++var12) {
                    double var13 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
                    double var15 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7D;
                    double var17;
                    double var19;
                    if (this.rand.nextBoolean()) {
                        var17 = this.posX - var8 * var13 * 0.8D + var10 * var15;
                        var19 = this.posZ - var10 * var13 * 0.8D - var8 * var15;
                        this.worldObj.spawnParticle("splash", var17, this.posY - 0.125D, var19, this.motionX, this.motionY, this.motionZ);
                    } else {
                        var17 = this.posX + var8 + var10 * var13 * 0.7D;
                        var19 = this.posZ + var10 - var8 * var13 * 0.7D;
                        this.worldObj.spawnParticle("splash", var17, this.posY - 0.125D, var19, this.motionX, this.motionY, this.motionZ);
                    }
                }
            }

//            if (this.isCollidedHorizontally && var6 > 0.15D) {
//                if (!this.worldObj.multiplayerWorld) {
//                    this.setEntityDead();
//
//                    int var22;
//                    for(var22 = 0; var22 < 3; ++var22) {
//                        this.dropItemWithOffset(Block.planks.blockID, 1, 0.0F);
//                    }
//
//                    for(var22 = 0; var22 < 2; ++var22) {
//                        this.dropItemWithOffset(Item.stick.shiftedIndex, 1, 0.0F);
//                    }
//                	
//                }
//            } else {
                this.motionX *= 0.9900000095367432D;
                this.motionY *= 0.949999988079071D;
                this.motionZ *= 0.9900000095367432D;
//            }

            this.rotationPitch = 0.0F;
            var8 = (double)this.rotationYaw;
            var10 = this.prevPosX - this.posX;
            double var23 = this.prevPosZ - this.posZ;
            if (var10 * var10 + var23 * var23 > 0.001D) {
                var8 = (double)((float)(Math.atan2(var23, var10) * 180.0D / 3.141592653589793D));
            }

            double var14;
            for(var14 = var8 - (double)this.rotationYaw; var14 >= 180.0D; var14 -= 360.0D) {
            }

            while(var14 < -180.0D) {
                var14 += 360.0D;
            }

            if (var14 > 20.0D) {
                var14 = 20.0D;
            }

            if (var14 < -20.0D) {
                var14 = -20.0D;
            }

            this.rotationYaw = (float)((double)this.rotationYaw + var14);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            List var16 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
            int var24;
            if (var16 != null && var16.size() > 0) {
                for(var24 = 0; var24 < var16.size(); ++var24) {
                    Entity var18 = (Entity)var16.get(var24);
                    if (var18 != this.riddenByEntity && var18.canBePushed() && var18 instanceof EntityBoat) {
                        var18.applyEntityCollision(this);
                    }
                }
            }

            for(var24 = 0; var24 < 4; ++var24) {
                int var25 = MathHelper.floor_double(this.posX + ((double)(var24 % 2) - 0.5D) * 0.8D);
                int var26 = MathHelper.floor_double(this.posY);
                int var20 = MathHelper.floor_double(this.posZ + ((double)(var24 / 2) - 0.5D) * 0.8D);
                if (this.worldObj.getBlockId(var25, var26, var20) == Block.snow.blockID) {
                    this.worldObj.setBlockWithNotify(var25, var26, var20, 0);
                }
            }

            if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                this.riddenByEntity = null;
            }

        }
    }

    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            double var1 = Math.cos((double)this.rotationYaw * 3.141592653589793D / 180.0D) * 0.4D;
            double var3 = Math.sin((double)this.rotationYaw * 3.141592653589793D / 180.0D) * 0.4D;
            this.riddenByEntity.setPosition(this.posX + var1, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + var3);
        }
    }

    protected void writeEntityToNBT(NBTTagCompound var1) {
    }

    protected void readEntityFromNBT(NBTTagCompound var1) {
    }

    public float getShadowSize() {
        return 0.0F;
    }

    public boolean interact(EntityPlayer var1) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != var1) {
            return true;
        } else {
            if (!this.worldObj.multiplayerWorld) {
                var1.mountEntity(this);
            }

            return true;
        }
    }
}
