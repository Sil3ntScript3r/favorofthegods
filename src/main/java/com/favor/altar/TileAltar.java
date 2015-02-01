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
	private static final int BASE_SIZE = 2;
	private static final int SIZE_SCALE = 2;
	private static final int BLOCKS_NEEDED = 5;
	
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
		
		if(!player.worldObj.isRemote)
		{
			//Favor favor = Favor.get(ownerPlayer);

			// Make sure Altar meets Rank 0 requirements
			if(!checkRank0(world))
			{
				player.addChatComponentMessage(new ChatComponentText("No God wants this altar."));
				return;
			}
			else
			{
				player.addChatComponentMessage(new ChatComponentText(Gods.godNames.get(mainGod) + " accepts this Altar of the Gods."));
			}

			checkBlocks(world, this.pos, BASE_SIZE + (SIZE_SCALE * rank));
		
			surronding.retainAll(Gods.getAltarBlocks(mainGod, rank + 1));
			System.out.println(surronding.size());
		}
	}
	
	private boolean checkRank0(World world)
	{
		for(int i = 0; i <= Gods.godBlocks.size(); i++)
		{
			if(Gods.getAltarBlocks(i, 0).contains(world.getBlockState(this.pos.add(0, -1, 0)).getBlock()))
			{
				rank = 0;
				mainGod = i;
				return true;
			}
		}
		
		return false;
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
