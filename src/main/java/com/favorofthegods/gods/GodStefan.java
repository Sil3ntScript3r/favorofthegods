package com.favorofthegods.gods;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GodStefan extends Gods {
	private static List[] altarBlocks;
	
	public GodStefan()
	{	
		godNames.add(GOD_STEFAN, "Stefan");
		
		altarBlocks = initAltarBlocks();
		godBlocks.add(GOD_STEFAN, altarBlocks);
		
		altarBlocks[0].add(Blocks.dirt);
		
		altarBlocks[1].add(Blocks.red_flower);
		altarBlocks[1].add(Blocks.yellow_flower);
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
					increaseFavor( player, GOD_STEFAN, 1);
			}
			// The Great Stefan does not approve of the killing of sheep
			else if(event.entity instanceof EntitySheep)
			{
					decreaseFavor(player, GOD_STEFAN, 2);
			}
		}
	}
}
