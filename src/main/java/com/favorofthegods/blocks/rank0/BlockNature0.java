package com.favorofthegods.blocks.rank0;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.favorofthegods.FavorOfTheGods;

public class BlockNature0 extends Block {
	public static final String NAME = "blockNature0";
	
	public BlockNature0()
	{
		super(Material.rock);
		GameRegistry.registerBlock(this, NAME);
		setUnlocalizedName(FavorOfTheGods.prependModID(NAME, '_'));
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
}
