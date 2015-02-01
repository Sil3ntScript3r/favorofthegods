package com.favor.altar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.favor.Favor;
import com.favor.gods.GodDesertPig;
import com.favor.gods.GodStefan;
import com.favor.gods.Gods;

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
	
	public void checkRank(World world)
	{
		checkRank(world, null);
	}
	
	// TODO: Finish checking rank of the Altar
	public void checkRank(World world, EntityPlayer player)
	{
		rank = -1;
		if(ownerPlayer == null)
			return;
		
		Favor favor = Favor.get(ownerPlayer);
		
		System.out.println(GodStefan.altarBlocks[0]);
		System.out.println(GodDesertPig.altarBlocks[0]);
		// Check if it meets Rank 0 requirements, and see which God is being praised
		if(GodStefan.altarBlocks[0].contains(world.getBlockState(pos.add(0, -1, 0)).getBlock()))
		{
			if(world.getBlockState(pos.add(1, -1, 0)).getBlock() == Blocks.air && world.getBlockState(pos.add(-1, -1, 0)).getBlock() == Blocks.air)
			{
				if(world.getBlockState(pos.add(0, -1, 1)).getBlock() == Blocks.air && world.getBlockState(pos.add(0, -1, -1)).getBlock() == Blocks.air)
				{
					rank++;
					if(player.worldObj.isRemote)
						player.addChatComponentMessage(new ChatComponentText("Stefan accepts this altar."));
					
					checkStefan();
					return;
				}
			}
		}
		
		else if(GodDesertPig.altarBlocks[0].contains(world.getBlockState(pos.add(0, -1, 0)).getBlock()))
		{
			if(world.getBlockState(pos.add(1, -1, 0)).getBlock() == Blocks.air && world.getBlockState(pos.add(-1, -1, 0)).getBlock() == Blocks.air)
			{
				if(world.getBlockState(pos.add(0, -1, 1)).getBlock() == Blocks.air && world.getBlockState(pos.add(0, -1, -1)).getBlock() == Blocks.air)
				{
					rank++;
					if(player.worldObj.isRemote)
						player.addChatComponentMessage(new ChatComponentText("Desert Pig accepts this altar."));
					
					checkDesertPig();
					return;
				}
			}
		}

		if(player.worldObj.isRemote)
			player.addChatComponentMessage(new ChatComponentText("No God wants this altar."));
	}
	
	public void checkStefan()
	{
		System.out.println("Stefan is the God");
	}
	
	public void checkDesertPig()
	{
		System.out.println("Desert Pig is the God");
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
