package com.favorofthegods.gods;

import java.util.List;

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
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import com.favorofthegods.PlayerProps;
import com.favorofthegods.PlayerUtils;
import com.favorofthegods.blocks.BlockList;
import com.favorofthegods.favornetwork.Favor;
import com.favorofthegods.favornetwork.FavorHandler;

public class GodDesertPig extends Gods {
	private static List[] altarBlocks;
	
	public GodDesertPig()
	{
		godNames.add(GOD_DESERTPIG, "Desert Pig");
		
		altarBlocks = initAltarBlocks();
		godBlocks.add(GOD_DESERTPIG, altarBlocks);
		
		altarBlocks[0].add(BlockList.desertPig0);
		
		altarBlocks[1].add(Blocks.tnt);
		
		altarBlocks[2].add(Blocks.sandstone);
		
		altarBlocks[3].add(Blocks.diamond_ore);
		
		altarBlocks[4].add(Blocks.jukebox);
		
		altarBlocks[5].add(Blocks.emerald_block);
		// TODO: Pepper demands a pig block
	}
	
	static void tick(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;
		Favor favor = FavorHandler.getFavor(player);
		
		if(favor != null)
		{
			// Check if they hold a red flower
			if(player.inventory.hasItemStack(new ItemStack(Blocks.red_flower)))
			{
				decreaseFavor(player, GOD_DESERTPIG, 1);
			}
			
			// Check if they follow Desert Pig
			if(favor.getMain() == GOD_DESERTPIG)
			{
				if(favor.getHighest() >= 1)
				{
					// If they have atleast a rank 1 altar, spawn some pork after a 1/16777216 chance
					if(rand.nextInt(256) == 0)
					{
						if(rand.nextInt(256) == 0)
						{
							if(rand.nextInt(256) == 0)
							{
								ItemStack item = new ItemStack(Items.porkchop, (int)(Math.ceil(favor.getFavor(GOD_DESERTPIG) * .003)));
								EntityItem entityItem = new EntityItem(player.worldObj, player.posX, player.posY + 2, player.posZ, item);
								entityItem.setDefaultPickupDelay();
								player.worldObj.spawnEntityInWorld(entityItem);
								PlayerUtils.sendChat(player, "Desert Pig has blessed you with his meat!");
							}
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityDied(LivingDeathEvent event)
	{
		if(event.source.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.source.getEntity();
			PlayerProps props = PlayerProps.get(player);
			
			if(props != null)
			{
				if(props.hasReligion())
				{
					Favor favor = FavorHandler.getFavor(player);
					
					// Desert Pig does not get along with sheep
					if(event.entity instanceof EntitySheep)
					{
							increaseFavor(player, GOD_DESERTPIG, 1, true);
					}
					
					// Desert Pig hates if you kill his own kind
					else if(event.entity instanceof EntityPig && player.getFoodStats().getFoodLevel() > 1)
					{
						decreaseFavor(player, GOD_DESERTPIG, 2);
						
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
								
								int minFavor = FavorHandler.MIN_FAVOR;
	
								if(pigFavor == minFavor)
								{
									// RANK 4 ~ -10000 Favor
									zomb.setCurrentItemOrArmor(0, new ItemStack(Items.diamond_sword));
									zomb.setCurrentItemOrArmor(1, new ItemStack(Items.diamond_helmet));
									zomb.setCurrentItemOrArmor(2, new ItemStack(Items.diamond_chestplate));
									zomb.setCurrentItemOrArmor(3, new ItemStack(Items.diamond_leggings));
									zomb.setCurrentItemOrArmor(4, new ItemStack(Items.diamond_boots));
									zomb.setCustomNameTag("Desert Pig's Incarnate <Rank 4>");
									player.addChatComponentMessage(new ChatComponentText("Desert Pig is VERY upset at the death you caused!"));
								}
								else if(pigFavor <= minFavor * .75)
								{
									// RANK 3 ~ -7500 Favor
									zomb.setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
									zomb.setCurrentItemOrArmor(1, new ItemStack(Items.iron_helmet));
									zomb.setCurrentItemOrArmor(2, new ItemStack(Items.iron_chestplate));
									zomb.setCurrentItemOrArmor(3, new ItemStack(Items.iron_leggings));
									zomb.setCurrentItemOrArmor(4, new ItemStack(Items.iron_boots));
									zomb.setCustomNameTag("Desert Pig's Incarnate <Rank 3>");
									player.addChatComponentMessage(new ChatComponentText("Desert Pig is very upset at the death you caused!"));
								}
								else if(pigFavor <= minFavor * .50)
								{
									// Rank 2 ~ -5000 Favor
									zomb.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
									zomb.setCurrentItemOrArmor(1, new ItemStack(Items.golden_helmet));
									zomb.setCurrentItemOrArmor(2, new ItemStack(Items.golden_chestplate));
									zomb.setCurrentItemOrArmor(3, new ItemStack(Items.golden_leggings));
									zomb.setCurrentItemOrArmor(4, new ItemStack(Items.golden_boots));
									zomb.setCustomNameTag("Desert Pig's Incarnate <Rank 2>");
									player.addChatComponentMessage(new ChatComponentText("Desert Pig is upset at the death you caused!"));
								}
								else if(pigFavor <= minFavor * .25)
								{
									// Rank 1 ~ -2500 Favor
									zomb.setCurrentItemOrArmor(0, new ItemStack(Items.wooden_sword));
									zomb.setCurrentItemOrArmor(1, new ItemStack(Items.leather_helmet));
									zomb.setCurrentItemOrArmor(2, new ItemStack(Items.leather_chestplate));
									zomb.setCurrentItemOrArmor(3, new ItemStack(Items.leather_leggings));
									zomb.setCurrentItemOrArmor(4, new ItemStack(Items.leather_boots));
									zomb.setCustomNameTag("Desert Pig's Incarnate <Rank 1>");
									player.addChatComponentMessage(new ChatComponentText("Desert Pig is very angry at the death you caused!"));
								}
								else
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
								increaseFavor(player, GOD_DESERTPIG, 2, false);
							}
						}
					}
				}
			}
		}
	}
}
