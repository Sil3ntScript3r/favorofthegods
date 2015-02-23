package com.favorofthegods.gods;

import java.util.List;

import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.favorofthegods.favornetwork.Favor;
import com.favorofthegods.favornetwork.FavorHandler;

public class GodBlood extends Gods {
	private static List[] altarBlocks;
	
	public GodBlood()
	{
		godNames.add(GOD_BLOOD, "Blood");
		
		altarBlocks = initAltarBlocks();
		godBlocks.add(GOD_BLOOD, altarBlocks);
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
}
