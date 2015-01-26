package com.favor.gods;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
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
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		if(!event.entity.worldObj.isRemote)
		{
			for(EntityPlayer x : Favor.players)
			{
				Favor favor = Favor.get(x);
				if(favor != null)
				{
					if(favor.getFavor(GOD_DESERTPIG) >= 30)
					{
						// TODO: Balance this
						if(rand.nextInt(256) == 0)
						{
							//System.out.print("Check 1 passed : ");
							if(rand.nextInt(256) == 0)
							{
								//System.out.println("Check 2 passed");
								ItemStack item = new ItemStack(Items.porkchop, (int)(Math.ceil(favor.getFavor(GOD_DESERTPIG) * .15)));
								EntityItem entityItem = new EntityItem(x.worldObj, x.posX + rand.nextInt(3), x.posY + 5, x.posZ + rand.nextInt(3), item);
								entityItem.setDefaultPickupDelay();
								x.worldObj.spawnEntityInWorld(entityItem);
								x.addChatComponentMessage(new ChatComponentText("Desert Pig has blessed you with his meat!"));
							}
						}
					}
				}
			}
		}
	}
}
