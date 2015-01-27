package com.favor.altar;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockAltar extends Block implements ITileEntityProvider {
	public BlockAltar(Material material)
	{
		super(material);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		if(world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.stone)
		{
			if(world.getBlockState(pos.add(1, -1, 0)).getBlock() == Blocks.air)
			{
				if(world.getBlockState(pos.add(-1, -1, 0)).getBlock() == Blocks.air)
				{
					if(world.getBlockState(pos.add(0, -1, 1)).getBlock() == Blocks.air)
					{
						if(world.getBlockState(pos.add(0, -1, -1)).getBlock() == Blocks.air)
						{
							System.out.println("Altar is complete");
						}
					}
				}
			}
		}
	}

	public TileEntity createNewTileEntity(World world, int meta) {
		System.out.println("Altar Entity Created");
		return new TileAltar();
	}

}
