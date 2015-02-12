package com.favorofthegods.gods;

import java.util.List;

import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.favorofthegods.PlayerProps;
import com.favorofthegods.blocks.BlockList;
import com.favorofthegods.favornetwork.Favor;
import com.favorofthegods.favornetwork.FavorHandler;

public class GodStefan extends Gods {
	private static List[] altarBlocks;
	
	public GodStefan()
	{	
		godNames.add(GOD_STEFAN, "Stefan");
		
		altarBlocks = initAltarBlocks();
		godBlocks.add(GOD_STEFAN, altarBlocks);
		
		altarBlocks[0].add(BlockList.stefan0);
		
		altarBlocks[1].add(Blocks.red_flower);
		
		altarBlocks[2].add(Blocks.redstone_wire);
		
		altarBlocks[3].add(Blocks.rail);
		
		altarBlocks[4].add(Blocks.brewing_stand);
		
		altarBlocks[5].add(Blocks.redstone_block);
	}
	
	@SubscribeEvent
	public void onEntityDied(LivingDeathEvent event)
	{
		if(event.source.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.source.getEntity();
			// The Great Stefan hates pigs
			if(event.entity instanceof EntityPig)
			{
					increaseFavor( player, GOD_STEFAN, 1, true);
			}
			// The Great Stefan does not approve of the killing of sheep
			else if(event.entity instanceof EntitySheep)
			{
					decreaseFavor(player, GOD_STEFAN, 2);
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityFall(LivingFallEvent event)
	{
		if(event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			PlayerProps props = PlayerProps.get(player);
			if(props == null || props.getReligionName() == null)
				return;
			
			Favor favor = FavorHandler.getFavor(props.getReligionName());
			if(favor !=  null)
			{
				if(favor.getMain() == GOD_STEFAN)
				{
					if(favor.getHighest() >= 2)
					{
						event.setCanceled(true);
					}
				}
			}
		}
	}
}
