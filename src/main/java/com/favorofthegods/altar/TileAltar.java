package com.favorofthegods.altar;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.favorofthegods.favornetwork.Favor;
import com.favorofthegods.favornetwork.FavorHandler;
import com.favorofthegods.gods.Gods;

public class TileAltar extends TileEntity {
	// Values used for math
	private static final int ALTAR_DEPTH = 3;
	private static final int BASE_SIZE = 2;
	private static final int SIZE_SCALE = 2;
	private static final int BLOCKS_NEEDED = 5;

	// Used later for calcs
	private List<Block> surronding;
	
	// Name of this religion/altar
	private String name;
	
	// Set whether this Altar used to be following a God
	private boolean hadGod;
	
	// Rank of this Altar
	private int rank;
	private int mainGod;
	
	
	public TileAltar()
	{
		// Init values to default
		surronding = new ArrayList<Block>();
		hadGod = false;
		mainGod = -1;
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		if(name != null)
			tag.setString("religionName", name);
		
		tag.setBoolean("hadGod", hadGod);
		
		tag.setInteger("mainGod", mainGod);
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		if(tag.hasKey("religionName"))
			name = tag.getString("religionName");
		
		hadGod = tag.getBoolean("hadGod");
		
		mainGod = tag.getInteger("mainGod");
	}
	
	// Master method to check the surronding blocks and religion's Favor to see what rank it is
	public void checkRank(World world, EntityPlayer player)
	{	
		// Server is the only one that needs to check
		if(!player.worldObj.isRemote)
		{
			Favor favor = FavorHandler.getFavor(name);
			if(favor == null)
				return;
			
			// Make sure Altar isn't owned by a God already
			if(hadGod == false)
			{
				// If it's not owned, assign it to one
				if(checkRank0(world))
				{
					player.addChatComponentMessage(new ChatComponentText(Gods.godNames.get(mainGod) + " accepts this Altar of the Gods."));
					hadGod = true;
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
				if(favor.getFavor(mainGod) < FavorHandler.RANKS[rank + 1])
					break;
				
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
	
	// Check is Altar meets Rank 0 requirements for any God
	private boolean checkRank0(World world)
	{
		Favor favor = FavorHandler.getFavor(name);
		
		for(int i = 0; i < Gods.godBlocks.size(); i++)
		{
			if(Gods.getAltarBlocks(i, 0).contains(world.getBlockState(this.pos.add(0, -1, 0)).getBlock()))
			{
				rank = 0;
				mainGod = i;
				//favor.setMain(i);
				return true;
			}
		}
		
		return false;
	}
	
	// Sorts all the surronding blocks by which rank they are for the main God
	private int[] checkBlockRank()
	{
		Favor favor = FavorHandler.getFavor(name);
		
		int[] blockCount = new int[Gods.NUM_RANKS + 1];
		
		for(int i = 0; i <= Gods.NUM_RANKS; i++)
		{
			for(Block block : surronding)
			{
				if(Gods.getAltarBlocks(favor.getMain(), i).contains(block))
					blockCount[i]++;
			}
		}
		
		System.out.println(blockCount[0] + " : " + blockCount[1] + " : " + blockCount[2] + " : " + blockCount[3] + " : " + blockCount[4] + " : " + blockCount[5]);
		return blockCount;
	}
	
	// Get all the blocks in the area
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
	
	// Returns the rank of this Altar
	public int getRank()
	{
		return rank;
	}
	
	public String getReligionName()
	{
		return name;
	}
	
	public void setReligionName(String name)
	{
		System.out.println("Altar's religion set to " + name);
		this.name = name;
	}
}
