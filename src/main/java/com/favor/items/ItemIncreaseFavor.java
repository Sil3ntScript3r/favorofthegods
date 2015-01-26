package com.favor.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.favor.Favor;
import com.favor.gods.Gods;

public class ItemIncreaseFavor extends Item {
	public ItemIncreaseFavor()
	{
		this.setCreativeTab(CreativeTabs.tabTools);
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		Favor favor = Favor.get(player);
		if(favor != null)
		{
			if(!player.isSneaking())
				favor.increaseFavor(10, Gods.GOD_STEFAN);
			else
				favor.increaseFavor(10, Gods.GOD_DESERTPIG);
		}
		return itemStack;
	}
}
