package com.favor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventList {
	@SubscribeEvent
	public void onEntityConstruct(EntityConstructing event)
	{
		// Make sure the entity is a player, and doesn't already have a Favor system. If yes>no, give them one
		if(event.entity instanceof EntityPlayer && Favor.get((EntityPlayer)event.entity) == null)
		{
			Favor.register((EntityPlayer)event.entity);
			System.out.println("FAVOR SYSTEM IN PLACE");
		}
	}
}
