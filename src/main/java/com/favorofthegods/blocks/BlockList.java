package com.favorofthegods.blocks;

import net.minecraft.block.material.Material;

import com.favorofthegods.altar.BlockAltar;
import com.favorofthegods.altar.BlockRank0;

public class BlockList {
	public static BlockAltar altar;
	public static BlockRank0 blockRank0;
	
	public static void preInit()
	{
		altar = new BlockAltar(Material.wood);
		
		blockRank0 = new BlockRank0(Material.rock);
	}
}
