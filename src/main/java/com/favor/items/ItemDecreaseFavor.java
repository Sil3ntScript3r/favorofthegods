package com.favor.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.favor.PlayerProps;
import com.favor.altar.Favor;
import com.favor.gods.Gods;

public class ItemDecreaseFavor extends Item {
	public ItemDecreaseFavor()
	{
		this.setCreativeTab(CreativeTabs.tabTools);
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		PlayerProps props = PlayerProps.get(player);
		Favor favor = props.getAltar().getFavor();
		
		if(favor != null)
			if(!player.isSneaking())
				favor.decreaseFavor(10, Gods.GOD_STEFAN);
			else
				favor.decreaseFavor(250, Gods.GOD_DESERTPIG);
		
		return itemStack;
	}
}
