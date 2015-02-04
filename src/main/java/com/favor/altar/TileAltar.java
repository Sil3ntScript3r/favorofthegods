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

import com.favor.gods.Gods;

public class TileAltar extends TileEntity {
	private static final int ALTAR_DEPTH = 3;
	private static final int BASE_SIZE = 2;
	private static final int SIZE_SCALE = 2;
	private static final int BLOCKS_NEEDED = 5;
	private static final String TAG = "favor";
	
	private int rank;
	private int mainGod;
	private Favor favor;
	private List<Block> surronding;
	private List<EntityPlayer> followers;
	
	public TileAltar()
	{
		favor = new Favor();
		mainGod = -1;
		
		followers = new ArrayList<EntityPlayer>();
		surronding = new ArrayList<Block>();
	}
	
	public void writeToNBT(NBTTagCompound data)
	{
		super.writeToNBT(data);
		NBTTagCompound favorTag = new NBTTagCompound();
		
		favorTag.setInteger("mainGod", mainGod);
		
		List<Integer> godFavors = favor.getFavors();
		int[] favors = new int[godFavors.size()];
		
		for(int i = 0; i < godFavors.size(); i++)
		{
			favors[i] = godFavors.get(i);
		}
		
		favorTag.setIntArray("godFavors", favors);
		
		data.setTag(TAG, favorTag);
	}
	
	public void readFromNBT(NBTTagCompound data)
	{
		super.readFromNBT(data);
		NBTTagCompound favorTag = (NBTTagCompound)data.getTag(TAG);
		
		mainGod = favorTag.getInteger("mainGod");
		
		int[] favors = favorTag.getIntArray("godFavors");
		
		for(int i = 0; i < favors.length; i++)
		{
			favor.setFavor(i, favors[i]);
		}
	}
	
	public void checkRank(World world, EntityPlayer player)
	{	
		if(!player.worldObj.isRemote)
		{
			// Make sure Altar isn't owned by a God already
			if(mainGod == -1)
			{
				// If it's not owned, assign it to one
				if(checkRank0(world))
				{
					player.addChatComponentMessage(new ChatComponentText(Gods.godNames.get(mainGod) + " accepts this Altar of the Gods."));
				}
				else
				{
					player.addChatComponentMessage(new ChatComponentText("No God wants this altar."));
					return;
				}
			}
			else
			{
				// If it is owned, find out if it's still acceptable
				if(Gods.getAltarBlocks(mainGod, 0).contains(world.getBlockState(this.pos.add(0, -1, 0)).getBlock()))
				{
					player.addChatComponentMessage(new ChatComponentText("This Altar is Favored by " + Gods.godNames.get(mainGod) + "."));
				}
				else
				{
					player.addChatComponentMessage(new ChatComponentText("This Altar was Favored by " + Gods.godNames.get(mainGod) + ", but has since lost it's Favor."));
					rank = -1;
					return;
				}
			}
			// Reset the rank before we start counting
			rank = 0;
			
			// Iterate through all the possible Ranks
			// Check the Altar at each Rank to see if it meets the requirements
			for(int i = 1; i <= Gods.NUM_RANKS; i++)
			{
				getBlocks(world, this.pos, BASE_SIZE + (SIZE_SCALE * i));
				int[] b = checkBlockRank();

				if(!checkRanks(i, b))
					break;
				
				rank++;
			}
			
			System.out.println("Rank: " + rank);
		}
	}

	private boolean checkRanks(int i, int[] b)
	{
		if(b[1] >= BLOCKS_NEEDED * i)
		{
			if(b[2] >= BLOCKS_NEEDED * (i - 1))
			{
				if(b[3] >= BLOCKS_NEEDED * (i - 2))
				{
					if(b[4] >= BLOCKS_NEEDED * (i - 3))
					{
						if(b[5] >= BLOCKS_NEEDED * (i - 4))
						{
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean checkRank0(World world)
	{
		for(int i = 0; i < Gods.godBlocks.size(); i++)
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
	
	private int[] checkBlockRank()
	{
		int[] blockCount = new int[Gods.NUM_RANKS + 1];
		
		for(int i = 0; i <= Gods.NUM_RANKS; i++)
		{
			for(Block block : surronding)
			{
				if(Gods.getAltarBlocks(mainGod, i).contains(block))
					blockCount[i]++;
			}
		}
		
		System.out.println(blockCount[0] + " : " + blockCount[1] + " : " + blockCount[2] + " : " + blockCount[3] + " : " + blockCount[4] + " : " + blockCount[5]);
		return blockCount;
	}
	
	private void getBlocks(World world, BlockPos pos, int size)
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
		
		System.out.println("Surronding blocks in radius " + size + ": " + surronding.size());
	}
	
	public Favor getFavor()
	{
		return favor;
	}
	
	public int getRank()
	{
		return rank;
	}
	
	public void addFollower(EntityPlayer player)
	{
		if(!followers.contains(player))
			followers.add(player);
	}
}
