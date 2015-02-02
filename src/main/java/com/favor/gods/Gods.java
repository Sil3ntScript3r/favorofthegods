package com.favor.gods;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

import com.favor.Favor;

public class Gods {
	// Ints used to easily talk about a certain God
	public static final int GOD_STEFAN = 0;
	public static final int GOD_DESERTPIG = 1;
	
	// List of all the blocks that can be used for the Altar
	// First list = God to choose, based on it's number
	// Array index = Rank of the block
	// Second list = All the blocks the God has added for that rank
	// All gods set this themself in their constructor
	public static List<List[]> godBlocks = new ArrayList<List[]>();
	public static List<String> godNames = new ArrayList<String>();
	
	public static final int NUM_RANKS = 5;
	
	String name;
	
	Random rand = new Random();
	
	// Return the list of blocks for a certain rank
	public static ArrayList<Block> getAltarBlocks(int god, int rank)
	{
		return (ArrayList<Block>)godBlocks.get(god)[rank];
	}
	
	// Used by the gods to construct their ArrayList
	static List[] initAltarBlocks()
	{
		List[] list = new List[NUM_RANKS + 1];
		
		for(int i = 0; i <= NUM_RANKS; i++)
		{
			list[i] = new ArrayList<Block>();
		}
		
		return list;
	}
	
	void increaseFavor(int num, EntityPlayer player, int god)
	{
		if(Favor.get(player) != null)
		{
			Favor.get(player).increaseFavor(num, god);
		}
	}
	
	void decreaseFavor(int num, EntityPlayer player, int god)
	{
		if(Favor.get(player) != null)
		{
			Favor.get(player).decreaseFavor(num, god);
		}
	}
}
