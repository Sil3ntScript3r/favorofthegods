package com.favorofthegods.blocks;

import net.minecraft.block.material.Material;

import com.favorofthegods.altar.BlockAltar;
import com.favorofthegods.blocks.rank0.BlockDesertPig0;
import com.favorofthegods.blocks.rank0.BlockStefan0;

public class BlockList {
	public static BlockAltar altar;
	public static BlockDesertPig0 desertPig0;
	public static BlockStefan0 stefan0;
	
	public static void preInit()
	{
		altar = new BlockAltar();
		
		desertPig0 = new BlockDesertPig0();
		stefan0 = new BlockStefan0();
	}
}
