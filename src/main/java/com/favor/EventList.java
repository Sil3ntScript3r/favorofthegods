package com.favor;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventList {
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		// Check if a player died on the server only
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer)
		{
			// It was a player, so copy their NBTTag...
			NBTTagCompound playerData = new NBTTagCompound();
			Favor.get((EntityPlayer)event.entity).saveNBTData(playerData);
			
			// ...And save it to the CommonProxy
			CommonProxy.storeEntityData(((EntityPlayer)event.entity).getName(), playerData);
			Favor.saveProxyData((EntityPlayer) event.entity);
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		// Make sure it's a player joining
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer)
		{
			System.out.println("Player joined the world");
			// Get their Favor data out of the CommonProxy
			NBTTagCompound playerData = CommonProxy.getEntityData(((EntityPlayer)event.entity).getName());
			
			// Make sure they have Favor
			if(playerData != null)
			{
				((Favor)(event.entity.getExtendedProperties(Favor.FAVOR_TAG))).loadNBTData(playerData);
			}
			
			((Favor)(event.entity.getExtendedProperties(Favor.FAVOR_TAG))).syncProperties();
		}
	}
	
	@SubscribeEvent
	public void onEntityConstruct(EntityConstructing event)
	{
		// Make sure the entity is a player, and doesn't already have a Favor system. If yes and no, give them one
		if(event.entity instanceof EntityPlayer && Favor.get((EntityPlayer)event.entity) == null)
		{
			Favor.register((EntityPlayer)event.entity);
			System.out.println("FAVOR SYSTEM IN PLACE");
		}
	}
}
