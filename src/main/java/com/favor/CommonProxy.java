package com.favor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.favor.items.ItemFavorCheck;

public abstract class CommonProxy {
	public static ItemFavorCheck favorCheck;
	
	public void preInit()
	{
		favorCheck = (ItemFavorCheck)new ItemFavorCheck().setUnlocalizedName("favorCheck");
		GameRegistry.registerItem(favorCheck, "favorCheck");
	}

	public void init()
	{

	}

	public void postInit()
	{

	}
	
	abstract public boolean playerIsInCreativeMode(EntityPlayer player);
}
