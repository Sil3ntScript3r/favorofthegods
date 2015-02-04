package com.favor.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.favor.PlayerProps;
import com.favor.altar.Favor;
import com.favor.altar.TileAltar;

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
			if(props != null)
			{
				TileAltar altar = props.getAltar();
				if(altar != null)
				{
					Favor favor = altar.getFavor();
					if(favor != null)
					{
						System.out.println("--Favor--");
						System.out.println("Stefan: " + favor.getFavor(0));
						System.out.println("Desert Pig: " + favor.getFavor(1));
						System.out.println("Altar Location: " + props.getAltarPos());
					} else
					{
						System.out.println("Favor null");
					}
				} else
				{
					System.out.println("Altar null");
				}
			} else
			{
				System.out.println("Props null");
			}
		}

		return itemStack;
	}
}
