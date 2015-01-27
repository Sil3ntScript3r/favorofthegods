package com.favor.gods;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.favor.Favor;

public class GodDesertPig extends Gods {
	public GodDesertPig()
	{
		goodBlocks.add(Blocks.dirt);
		goodBlocks.add(Blocks.yellow_flower);
	}
	
	@SubscribeEvent
	public void onEntityDied(LivingDeathEvent event)
	{
		if(event.source.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.source.getEntity();
			Favor favor = Favor.get(player);
			
			if(favor != null)
			{
				// Desert Pig does not get along with sheep
				if(event.entity instanceof EntitySheep)
				{
						increaseFavor(1, player, GOD_DESERTPIG);
				}
				
				// Desert Pig hates if you kill his own kind
				else if(event.entity instanceof EntityPig)
				{
					decreaseFavor(2, player, GOD_DESERTPIG);
					
					// 1/24 chance to spawn a tough zombie pigman upon pig death
					if(favor.getFavor(GOD_DESERTPIG) <= -50)
					{
						if(rand.nextInt(24) == 0)
						{
							int pigFavor = favor.getFavor(GOD_DESERTPIG);
							EntityPigZombie zomb = new EntityPigZombie(event.entity.worldObj);
							zomb.setAttackTarget(player);
							zomb.setAIMoveSpeed(pigFavor * 2f);
							zomb.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.rotationYaw, 0.0F);
							zomb.setHealth(Math.abs(pigFavor * 2.6f));
							zomb.setEquipmentDropChance(0, 0);
							zomb.setEquipmentDropChance(1, 0);
							zomb.setEquipmentDropChance(2, 0);
							zomb.setEquipmentDropChance(3, 0);
							zomb.setEquipmentDropChance(4, 0);
							
							int minFavor = Favor.MIN_FAVOR;

							if(pigFavor == minFavor)
							{
								// RANK 4 ~ -1000 Favor
								zomb.setCurrentItemOrArmor(0, new ItemStack(Items.diamond_sword));
								zomb.setCurrentItemOrArmor(1, new ItemStack(Items.diamond_helmet));
								zomb.setCurrentItemOrArmor(2, new ItemStack(Items.diamond_chestplate));
								zomb.setCurrentItemOrArmor(3, new ItemStack(Items.diamond_leggings));
								zomb.setCurrentItemOrArmor(4, new ItemStack(Items.diamond_boots));
								zomb.setCustomNameTag("Desert Pig's Incarnate <Rank 4>");
								player.addChatComponentMessage(new ChatComponentText("Desert Pig is VERY upset at the death you caused!"));
							} else if(pigFavor <= minFavor * .75)
							{
								// RANK 3 ~ -750 Favor
								zomb.setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
								zomb.setCurrentItemOrArmor(1, new ItemStack(Items.iron_helmet));
								zomb.setCurrentItemOrArmor(2, new ItemStack(Items.iron_chestplate));
								zomb.setCurrentItemOrArmor(3, new ItemStack(Items.iron_leggings));
								zomb.setCurrentItemOrArmor(4, new ItemStack(Items.iron_boots));
								zomb.setCustomNameTag("Desert Pig's Incarnate <Rank 3>");
								player.addChatComponentMessage(new ChatComponentText("Desert Pig is very upset at the death you caused!"));
							} else if(pigFavor <= minFavor * .50)
							{
								// Rank 2 ~ -500 Favor
								zomb.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
								zomb.setCurrentItemOrArmor(1, new ItemStack(Items.golden_helmet));
								zomb.setCurrentItemOrArmor(2, new ItemStack(Items.golden_chestplate));
								zomb.setCurrentItemOrArmor(3, new ItemStack(Items.golden_leggings));
								zomb.setCurrentItemOrArmor(4, new ItemStack(Items.golden_boots));
								zomb.setCustomNameTag("Desert Pig's Incarnate <Rank 2>");
								player.addChatComponentMessage(new ChatComponentText("Desert Pig is upset at the death you caused!"));
							} else if(pigFavor <= minFavor * .25)
							{
								// Rank 1 ~ -250 Favor
								zomb.setCurrentItemOrArmor(0, new ItemStack(Items.wooden_sword));
								zomb.setCurrentItemOrArmor(1, new ItemStack(Items.leather_helmet));
								zomb.setCurrentItemOrArmor(2, new ItemStack(Items.leather_chestplate));
								zomb.setCurrentItemOrArmor(3, new ItemStack(Items.leather_leggings));
								zomb.setCurrentItemOrArmor(4, new ItemStack(Items.leather_boots));
								zomb.setCustomNameTag("Desert Pig's Incarnate <Rank 1>");
								player.addChatComponentMessage(new ChatComponentText("Desert Pig is very angry at the death you caused!"));
							} else
							{
								// Rank 0 ~ 0 Favor
								//zomb.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
								//zomb.setCurrentItemOrArmor(1, new ItemStack(Items.leather_helmet));
								//zomb.setCurrentItemOrArmor(2, new ItemStack(Items.leather_chestplate));
								//zomb.setCurrentItemOrArmor(3, new ItemStack(Items.leather_leggings));
								//zomb.setCurrentItemOrArmor(4, new ItemStack(Items.leather_boots));
								zomb.setCustomNameTag("Desert Pig's Incarnate <Rank 0>");
								player.addChatComponentMessage(new ChatComponentText("Desert Pig is angry at the death you caused!"));
							}

							event.entity.worldObj.spawnEntityInWorld(zomb);
							favor.increaseFavor(2, GOD_DESERTPIG);
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		if(!event.entity.worldObj.isRemote)
		{
			for(EntityPlayer x : Favor.players)
			{
				Favor favor = Favor.get(x);
				if(favor != null)
				{
					if(favor.getFavor(GOD_DESERTPIG) >= 30)
					{
						if(rand.nextInt(256) == 0)
						{
							if(rand.nextInt(256) == 0)
							{
								if(rand.nextInt(256) == 0)
								{
									ItemStack item = new ItemStack(Items.porkchop, (int)(Math.ceil(favor.getFavor(GOD_DESERTPIG) * .15)));
									EntityItem entityItem = new EntityItem(x.worldObj, x.posX + rand.nextInt(3), x.posY + 5, x.posZ + rand.nextInt(3), item);
									entityItem.setDefaultPickupDelay();
									x.worldObj.spawnEntityInWorld(entityItem);
									x.addChatComponentMessage(new ChatComponentText("Desert Pig has blessed you with his meat!"));
								}
							}
						}
					}
				}
			}
		}
	}
}
