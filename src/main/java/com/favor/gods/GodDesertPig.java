package com.favor.gods;

import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.favor.Favor;

public class GodDesertPig extends Gods {
	@SubscribeEvent
	public void onEntityDied(LivingDeathEvent event)
	{
		if(event.source.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.source.getEntity();
			// Desert Pig does not get along with sheep
			if(event.entity instanceof EntitySheep)
			{
					increaseFavor(1, player, GOD_DESERTPIG);
			}
			// Desert Pig hates if you kill his own kind
			else if(event.entity instanceof EntityPig)
			{
					decreaseFavor(2, player, GOD_DESERTPIG);
			}
		}
	}
}
