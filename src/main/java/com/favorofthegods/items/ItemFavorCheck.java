package com.favorofthegods.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.favorofthegods.PlayerProps;
import com.favorofthegods.favornetwork.Favor;
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
				Favor favor = FavorHandler.getFavor(props.getReligionName());
				if(favor != null)
				{
					System.out.println("--Favor--");
					System.out.println("Stefan: " + favor.getFavor(Gods.GOD_STEFAN));
					System.out.println("Desert Pig: " + favor.getFavor(Gods.GOD_DESERTPIG));
					System.out.println("Blood: " + favor.getFavor(Gods.GOD_BLOOD));
					System.out.println("Religion Name: " + props.getReligionName());
					System.out.println("Highest Rank: " + favor.getHighest());
					System.out.println("Main God: " + favor.getMain());
					Gods.gods.get(0).altarCheck();
				}
			}
		}
		else
		{
		PlayerProps props = PlayerProps.get(player);
			
			if(props.getReligionName() != null)
			{
				Favor favor = FavorHandler.getFavor(props.getReligionName());
				if(favor != null)
				{
					System.out.println("--Favor--");
					System.out.println("Stefan: " + favor.getFavor(Gods.GOD_STEFAN));
					System.out.println("Desert Pig: " + favor.getFavor(Gods.GOD_DESERTPIG));
					System.out.println("Religion Name: " + props.getReligionName());
					System.out.println("Highest Rank: " + favor.getHighest());
					System.out.println("Main God: " + favor.getMain());
				}
			}
		}

		return itemStack;
	}
}
