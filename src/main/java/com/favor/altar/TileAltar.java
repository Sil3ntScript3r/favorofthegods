package com.favor.altar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.favor.Favor;

public class TileAltar extends TileEntity {
	private int rank;
	private EntityPlayer ownerPlayer;
	
	public void writeToNBT(NBTTagCompound data)
	{
		super.writeToNBT(data);
	}
	
	public void readFromNBT(NBTTagCompound data)
	{
		super.readFromNBT(data);
	}
	
	// TODO: Finish checking rank of the Altar
	public void checkRank(World world)
	{
		rank = -1;
		Favor favor = Favor.get(ownerPlayer);
		
		// Check if it meets Rank 0 requirements, and see which God is being praised
		if(world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.dirt)
		{
			if(world.getBlockState(pos.add(1, -1, 0)).getBlock() == Blocks.air && world.getBlockState(pos.add(-1, -1, 0)).getBlock() == Blocks.air)
			{
				if(world.getBlockState(pos.add(0, -1, 1)).getBlock() == Blocks.air && world.getBlockState(pos.add(0, -1, -1)).getBlock() == Blocks.air)
				{
					rank++;
					checkStefan();
				}
			}
		}
		
		if(world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.sandstone)
		{
			if(world.getBlockState(pos.add(1, -1, 0)).getBlock() == Blocks.air && world.getBlockState(pos.add(-1, -1, 0)).getBlock() == Blocks.air)
			{
				if(world.getBlockState(pos.add(0, -1, 1)).getBlock() == Blocks.air && world.getBlockState(pos.add(0, -1, -1)).getBlock() == Blocks.air)
				{
					rank++;
					checkDesertPig();
				}
			}
		}
	}
	
	public void checkStefan()
	{
		
	}
	
	public void checkDesertPig()
	{
		
	}
	
	public int getRank()
	{
		return rank;
	}
	
	public void setOwner(EntityPlayer owner)
	{
		ownerPlayer = owner;
	}
}
