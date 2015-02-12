package com.favorofthegods.blocks.rank0;

import com.favorofthegods.FavorOfTheGods;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockDesertPig0 extends Block {
	public static final String NAME = "blockDesertPig0";
	
	public BlockDesertPig0()
	{
		super(Material.rock);
		GameRegistry.registerBlock(this, NAME);
		setUnlocalizedName(FavorOfTheGods.prependModID(NAME, '_'));
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
}
