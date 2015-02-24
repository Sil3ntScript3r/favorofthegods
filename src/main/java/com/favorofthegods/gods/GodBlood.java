package com.favorofthegods.gods;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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
	
	private static List[] altarBlocks;
	
	public GodBlood()
	{
		godNames.add(GOD_BLOOD, "Blood");
		
		altarBlocks = initAltarBlocks();
		godBlocks.add(GOD_BLOOD, altarBlocks);
		
		altarBlocks[0].add(BlockList.blood0);
		
		altarBlocks[1].add(Blocks.stone);
		
		altarBlocks[2].add(Blocks.obsidian);
		
		altarBlocks[3].add(Blocks.redstone_block);
		
		altarBlocks[4].add(Blocks.coal_block);
		
		altarBlocks[5].add(Blocks.stained_hardened_clay);
	}
	
	static void tick(PlayerTickEvent event)
	{
		PlayerProps props = PlayerProps.get(event.player);
		if(props != null)
		{
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
			if(event.itemInHand.getItem() instanceof IPlantable || event.placedBlock.getBlock() instanceof IPlantable)
			{
				decreaseFavor(event.player, GOD_BLOOD, 2);
			}
		}
	}
	
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
}
