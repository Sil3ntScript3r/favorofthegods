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
	
	private static String name;
	
	// List of all the blocks that can be used for the Altar
	// Array index = rank of the block
	// All gods set this themself in their constructor
	public static List[] goodBlocks = new List[6];
	public static List<Block> goodBlocks0;
	public static List<Block> goodBlocks1;
	public static List<Block> goodBlocks2;
	public static List<Block> goodBlocks3;
	public static List<Block> goodBlocks4;
	public static List<Block> goodBlocks5;
	
	
	Random rand = new Random();
	
	public Gods()
	{
		goodBlocks0 = new ArrayList<Block>();
		goodBlocks1 = new ArrayList<Block>();
		goodBlocks2 = new ArrayList<Block>();
		goodBlocks3 = new ArrayList<Block>();
		goodBlocks4 = new ArrayList<Block>();
		goodBlocks5 = new ArrayList<Block>();
		goodBlocks[0] = goodBlocks0;
		goodBlocks[1] = goodBlocks1;
		goodBlocks[2] = goodBlocks2;
		goodBlocks[3] = goodBlocks3;
		goodBlocks[4] = goodBlocks4;
		goodBlocks[5] = goodBlocks5;
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
