package com.favor.gods;

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
	public static List[] altarBlocks;
	
	public GodStefan()
	{	
		altarBlocks = initAltarBlocks();
		
		altarBlocks[0].add(Blocks.dirt);
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
					increaseFavor(1, player, GOD_STEFAN);
			}
			// The Great Stefan does not approve of the killing of sheep
			else if(event.entity instanceof EntitySheep)
			{
					decreaseFavor(2, player, GOD_STEFAN);
			}
		}
	}
}
