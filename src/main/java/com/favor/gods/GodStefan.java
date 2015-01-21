package com.favor.gods;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.favor.Favor;

public class GodStefan extends Gods {
	@SubscribeEvent
	public void onEntityDied(EntityLivingBase entity, DamageSource source)
	{
		if(entity instanceof EntityPig)
		{
			if(source.getEntity() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)source.getEntity();
				if(Favor.get(player) != null)
				{
					increaseFavor(10, player, GOD_STEFAN);
				}
			}
		}
	}
}