package com.favorofthegods.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.favorofthegods.PlayerProps;
import com.favorofthegods.favornetwork.FavorHandler;
import com.favorofthegods.gods.Gods;

public class ItemFavorCheck extends Item {
	public ItemFavorCheck()
	{
		this.setCreativeTab(CreativeTabs.tabTools);
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{	
		if(!world.isRemote)
		{
			PlayerProps props = PlayerProps.get(player);
			
			if(props.getReligionName() != null)
			{
				System.out.println("--Favor--");
				System.out.println("Stefan: " + FavorHandler.getFavor(props.getReligionName()).getFavor(Gods.GOD_DESERTPIG));
				System.out.println("Desert Pig: " + FavorHandler.getFavor(props.getReligionName()).getFavor(Gods.GOD_STEFAN));
				System.out.println("Altar Location: " + props.getReligionName());
			}
		}

		return itemStack;
	}
}
