package com.favor.altar;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.favor.Favor;
import com.favor.gods.GodDesertPig;
import com.favor.gods.GodStefan;
import com.favor.gods.Gods;

public class TileAltar extends TileEntity {
	private static final int ALTAR_DEPTH = 3;
	
	private int rank;
	private int mainGod;
	private EntityPlayer ownerPlayer;
	private List<Block> surronding;
	
	public TileAltar()
	{
		surronding = new ArrayList<Block>();
		rank = -1;
	}
	
	public void writeToNBT(NBTTagCompound data)
	{
		super.writeToNBT(data);
	}
	
	public void readFromNBT(NBTTagCompound data)
	{
		super.readFromNBT(data);
	}
	
	// TODO: Finish checking rank of the Altar
	public void checkRank(World world, EntityPlayer player)
	{
		rank = -1;
		if(ownerPlayer == null)
			return;
		
		if(!player.worldObj.isRemote)
		{
			Favor favor = Favor.get(ownerPlayer);
			
			// Check if it meets Rank 0 requirements, and see which God is being praised
			// STEFAN
			if(GodStefan.altarBlocks[0].contains(world.getBlockState(this.pos.add(0, -1, 0)).getBlock()))
			{
				if(world.getBlockState(this.pos.add(1, -1, 0)).getBlock() == Blocks.air && world.getBlockState(this.pos.add(-1, -1, 0)).getBlock() == Blocks.air)
				{
					if(world.getBlockState(this.pos.add(0, -1, 1)).getBlock() == Blocks.air && world.getBlockState(this.pos.add(0, -1, -1)).getBlock() == Blocks.air)
					{
						rank = 0;
						player.addChatComponentMessage(new ChatComponentText("Stefan accepts this altar."));
						
						mainGod = Gods.GOD_STEFAN;
						checkBlocks(world, this.pos, 5);
					}
				}
			}
			
			// DESERT PIG
			else if(GodDesertPig.altarBlocks[0].contains(world.getBlockState(this.pos.add(0, -1, 0)).getBlock()))
			{
				if(world.getBlockState(this.pos.add(1, -1, 0)).getBlock() == Blocks.air && world.getBlockState(this.pos.add(-1, -1, 0)).getBlock() == Blocks.air)
				{
					if(world.getBlockState(this.pos.add(0, -1, 1)).getBlock() == Blocks.air && world.getBlockState(this.pos.add(0, -1, -1)).getBlock() == Blocks.air)
					{
						rank = 0;
						player.addChatComponentMessage(new ChatComponentText("Desert Pig accepts this altar."));
						
						mainGod = Gods.GOD_DESERTPIG;
					}
				}
			}
			
			// Altar is not correct to reach Rank 0
			else
			{
				player.addChatComponentMessage(new ChatComponentText("No God wants this altar."));
			}
	
			// Continue checking ranks
			if(rank != -1)
			{
				
			}
		}
	}
	
	private void checkBlocks(World world, BlockPos pos, int size)
	{
		surronding.clear();
		
		int startX = pos.getX() - size;
		int startY = pos.getY() - ALTAR_DEPTH;
		int startZ = pos.getZ() - size;
		
		for(int x = startX; x <= pos.getX() + size; x++)
		{
			for(int z = startZ; z <= pos.getZ() + size; z++)
			{
				for(int y = startY; y <= pos.getY() + ALTAR_DEPTH; y++)
				{
					if(world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.air)
						surronding.add(world.getBlockState(new BlockPos(x, y, z)).getBlock());
				}
			}
		}
		
		System.out.println(surronding.size());
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
