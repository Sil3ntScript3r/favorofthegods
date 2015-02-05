package com.favor.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.favor.PlayerProps;
import com.favor.favornetwork.Favor;
import com.favor.favornetwork.FavorHandler;
import com.favor.gods.Gods;

public class ItemIncreaseFavor extends Item {
	public ItemIncreaseFavor()
	{
		this.setCreativeTab(CreativeTabs.tabTools);
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		PlayerProps props = PlayerProps.get(player);
		Favor favor = FavorHandler.getFavor(props.getReligionName());
		
		if(favor != null)
			if(!player.isSneaking())
				FavorHandler.increaseFavor(props.getReligionName(), Gods.GOD_DESERTPIG, 10);
			else
				FavorHandler.increaseFavor(props.getReligionName(), Gods.GOD_DESERTPIG, 100);
		
		return itemStack;
	}
}
