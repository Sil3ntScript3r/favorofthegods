package com.favorofthegods.blocks.rank0;

import com.favorofthegods.FavorOfTheGods;
import com.favorofthegods.altar.TileAltar;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockStefan0 extends Block {
	public static final String NAME = "blockStefan0";
	
	public BlockStefan0()
	{
		super(Material.rock);
		GameRegistry.registerBlock(this, NAME);
		setUnlocalizedName(FavorOfTheGods.prependModID(NAME, '_'));
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
}
