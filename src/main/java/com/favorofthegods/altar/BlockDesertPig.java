package com.favorofthegods.altar;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDesertPig extends Block {
	public BlockDesertPig()
	{
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
}
