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
	// Array index = rank of the block
	// All gods set this themself in their constructor
	//public static List<List[]> altarBlocks;
	
	private static final int NUM_RANKS = 5;
	
	Random rand = new Random();
	
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
	
	List[] initAltarBlocks(List[] list)
	{
		list = new List[NUM_RANKS + 1];
		
		for(int i = 0; i <= NUM_RANKS; i++)
		{
			list[i] = new ArrayList<Block>();
		}
		
		return list;
	}
}
