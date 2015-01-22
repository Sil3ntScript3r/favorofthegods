package com.favor;

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
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer)
		{
			NBTTagCompound playerData = new NBTTagCompound();
			Favor.get((EntityPlayer)event.entity).saveNBTData(playerData);
			
			CommonProxy.storeEntityData(((EntityPlayer)event.entity).getName(), playerData);
			Favor.saveProxyData((EntityPlayer) event.entity);
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer)
		{
			NBTTagCompound playerData = CommonProxy.getEntityData(((EntityPlayer)event.entity).getName());
			
			if(playerData != null)
			{
				((Favor)(event.entity.getExtendedProperties(Favor.FAVOR_TAG))).loadNBTData(playerData);
			}
			
			//((Favor)(event.entity.getExtendedProperties(Favor.FAVOR_TAG))).syncProperties();
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
