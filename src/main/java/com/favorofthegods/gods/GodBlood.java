package com.favorofthegods.gods;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.favorofthegods.PlayerProps;
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
}
