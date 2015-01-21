package com.favor.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.favor.Favor;

public class ItemFavorCheck extends Item {
	public ItemFavorCheck()
	{
		this.setCreativeTab(CreativeTabs.tabTools);
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		Favor favor = Favor.get(player);
		if(favor != null)
		{
			System.out.println("--Favor--");
			System.out.println("Stefan: " + favor.getFavor(0));
			System.out.println("Desert Pig: " + favor.getFavor(1));
		}
		return itemStack;
	}
}
