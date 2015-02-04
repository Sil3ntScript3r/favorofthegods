package com.favor.gods;

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

import com.favor.PlayerProps;
import com.favor.altar.Favor;
import com.favor.altar.TileAltar;

public class GodDesertPig extends Gods {
	private static List[] altarBlocks;
	
	public GodDesertPig()
	{
		godNames.add(GOD_DESERTPIG, "Desert Pig");
		
		altarBlocks = initAltarBlocks();
		godBlocks.add(GOD_DESERTPIG, altarBlocks);
		
		altarBlocks[0].add(Blocks.sandstone);
		altarBlocks[1].add(Blocks.tnt);
		altarBlocks[2].add(Blocks.anvil);
		altarBlocks[3].add(Blocks.iron_ore);
		altarBlocks[4].add(Blocks.jukebox);
		altarBlocks[5].add(Blocks.emerald_block);
		//goodBlocks[5].add(pigBlock); Pepper demands a pig block
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
				if(props.checkAltar())
				{
					Favor favor = props.getAltar().getFavor();
					
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
	}
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		if(!event.entity.worldObj.isRemote)
		{
			String[] players = MinecraftServer.getServer().getConfigurationManager().getAllUsernames();
			
			for(String i : players)
			{
				if(PlayerProps.get(MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(i)) != null);
				{
					EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(i);
					PlayerProps props = PlayerProps.get(player);
					
					if(props.checkAltar())
					{
						Favor favor = props.getAltar().getFavor();
						
						if(favor.getFavor(GOD_DESERTPIG) >= 30)
						{
							if(rand.nextInt(256) == 0)
							{
								if(rand.nextInt(256) == 0)
								{
									if(rand.nextInt(256) == 0)
									{
										ItemStack item = new ItemStack(Items.porkchop, (int)(Math.ceil(favor.getFavor(GOD_DESERTPIG) * .15)));
										EntityItem entityItem = new EntityItem(player.worldObj, player.posX + rand.nextInt(3), player.posY + 5, player.posZ + rand.nextInt(3), item);
										entityItem.setDefaultPickupDelay();
										player.worldObj.spawnEntityInWorld(entityItem);
										player.addChatComponentMessage(new ChatComponentText("Desert Pig has blessed you with his meat!"));
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
