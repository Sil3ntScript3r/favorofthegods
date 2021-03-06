package com.favorofthegods.gods;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import com.favorofthegods.PlayerProps;
import com.favorofthegods.blocks.BlockList;
import com.favorofthegods.favornetwork.Favor;
import com.favorofthegods.favornetwork.FavorHandler;

public class GodBlood extends Gods {
	private static final String BLOOD_REVIVE = "bloodRevive";

	public GodBlood()
	{	
		name = "Blood";
		
		altarBlocks = initAltarBlocks();
		
		altarBlocks[0].add(BlockList.blood0);
		
		altarBlocks[1].add(Blocks.stone);
		
		altarBlocks[2].add(Blocks.obsidian);
		
		altarBlocks[3].add(Blocks.redstone_block);
		
		altarBlocks[4].add(Blocks.coal_block);
		
		altarBlocks[5].add(Blocks.stained_hardened_clay);
	}
	
	static void tick(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;
		PlayerProps props = PlayerProps.get(player);
		if(props != null)
		{
			Favor favor = FavorHandler.getFavor(player);
			
			if(favor != null)
			{
				if(event.player.worldObj.getTotalWorldTime() % 80L == 0L)
				{
					if(favor.getFavor(GOD_BLOOD) >= 2000)
					{
						int bloodFavor = favor.getFavor(GOD_BLOOD);
						
						if(bloodFavor == FavorHandler.MAX_FAVOR)
						{
							player.removePotionEffect(Potion.damageBoost.id);
							player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 180, 4, true, false));
						}
						else if(bloodFavor >= FavorHandler.MAX_FAVOR * .8)
						{
							player.removePotionEffect(Potion.damageBoost.id);
							player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 180, 3, true, false));
						}
						else if(bloodFavor >= FavorHandler.MAX_FAVOR * .6)
						{
							player.removePotionEffect(Potion.damageBoost.id);
							player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 180, 2, true, false));
						}
						else if(bloodFavor >= FavorHandler.MAX_FAVOR * .4)
						{
							player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 180, 1, true, false));
						}
						else if(bloodFavor >= FavorHandler.MAX_FAVOR * .2)
						{
							player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 180, 0, true, false));
						}
					}
				}
			}
			
			// Every tick, decrease the cooldown for the Blood Revive passive
			if(props.getValue(BLOOD_REVIVE) > 0)
			{
				props.setValue(BLOOD_REVIVE, props.getValue(BLOOD_REVIVE) - 1);
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityDied(LivingDeathEvent event)
	{
		if(event.source.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.source.getEntity();
			Favor favor = FavorHandler.getFavor(player);
			
			if(favor != null)
			{
				// Blood Gain 1: Killing something
				increaseFavor(player, GOD_BLOOD, 1, true);
			}
		}
	}
	
	@SubscribeEvent
	public void playerPlaceBlock(BlockEvent.PlaceEvent event)
	{
		Favor favor = FavorHandler.getFavor(event.player);
		
		if(favor != null)
		{
			// Blood Lose 1: Planting a plant
			if(event.itemInHand.getItem() instanceof IPlantable || event.placedBlock.getBlock() instanceof IPlantable)
			{
				decreaseFavor(event.player, GOD_BLOOD, 1);
			}
		}
	}
	
	// Blood Bonus 5: Death ignore
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void playerDeath(LivingDeathEvent event)
	{
		if(event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			Favor favor = FavorHandler.getFavor(player);
			
			if(favor != null)
			{
				if(favor.getMain() == GOD_BLOOD)
				{
					System.out.println("Blood is followed");
					if(favor.getHighest() >= 5)
					{
						System.out.println("Enough blood has been spilled");
						if(PlayerProps.get(player).getValue(BLOOD_REVIVE) <= 0)
						{
							// TODO: Add an animation to this. Aatrox passive-esque
							System.out.println("You will be reborn");
							event.setCanceled(true);
							player.setHealth(player.getMaxHealth());
							PlayerProps.get(player).setValue(BLOOD_REVIVE, 200);
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerAttack(AttackEntityEvent event)
	{
		Favor favor = FavorHandler.getFavor(event.entityPlayer);
		
		if(favor != null)
		{
			// Blood Punish 2: Inflict weakness
			if(favor.getFavor(GOD_BLOOD) <= -5000)
			{
				if(rand.nextInt(256) == 0)
				{
					event.entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness.id, 100, 0));
					increaseFavor(event.entityPlayer, GOD_BLOOD, 20, false);
				}
			}
		}
	}
}
