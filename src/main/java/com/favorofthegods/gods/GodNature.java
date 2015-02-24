package com.favorofthegods.gods;

import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.favorofthegods.blocks.BlockList;
import com.favorofthegods.favornetwork.Favor;
import com.favorofthegods.favornetwork.FavorHandler;

public class GodNature extends Gods {
	private static List[] altarBlocks;
	
	public GodNature()
	{
		godNames.add(GOD_NATURE, "Nature");
		
		altarBlocks = initAltarBlocks();
		godBlocks.add(GOD_NATURE, altarBlocks);
		
		altarBlocks[0].add(BlockList.nature0);
		
		altarBlocks[1].add(Blocks.log);
		
		altarBlocks[2].add(Blocks.planks);
		
		altarBlocks[3].add(Blocks.leaves);
		
		altarBlocks[4].add(Blocks.oak_stairs);
		
		altarBlocks[5].add(Blocks.bookshelf);
	}

	@SubscribeEvent
	public void playerPlaceBlock(BlockEvent.PlaceEvent event)
	{
		Favor favor = FavorHandler.getFavor(event.player);
		
		if(favor != null)
		{
			if(event.itemInHand.getItem() instanceof IPlantable || event.placedBlock.getBlock() instanceof IPlantable)
			{
				increaseFavor(event.player, GOD_NATURE, 1, true);
			}
		}
	}
	
	@SubscribeEvent
	public void playerBreakBlock(BlockEvent.BreakEvent event)
	{
		Favor favor = FavorHandler.getFavor(event.getPlayer());
		
		if(favor != null)
		{
			if(event.state.getBlock() instanceof IPlantable)
			{
				decreaseFavor(event.getPlayer(), GOD_NATURE, 1);
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
				if(event.entity instanceof EntityCreature)
				{
					decreaseFavor(player, GOD_NATURE, 1);
				}
			}
		}
	}
}