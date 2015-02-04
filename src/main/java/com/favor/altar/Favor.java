package com.favor.altar;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.BlockPos;

import com.favor.gods.Gods;

public class Favor {
	public static final int MAX_FAVOR = 10000;
	public static final int MIN_FAVOR = -10000;
	public static final int[] RANKS = new int[]{0, 500, 2500, 5000, 7500, MAX_FAVOR};
	
	private static final String TAG = "Altar";
	
	private List<Integer> godFavors;
	
	public Favor()
	{
		godFavors = new ArrayList<Integer>();
		
		for(String s : Gods.godNames)
		{
			godFavors.add(0);
		}
	}
	
	public List<Integer> getFavors()
	{
		return godFavors;
	}
	
	public int getFavor(int god)
	{
		return godFavors.get(god);
	}
	
	public void setFavor(int god, int num)
	{
		godFavors.set(god, num);
	}
	
	public void increaseFavor(int god, int num)
	{
		godFavors.set(god, godFavors.get(god) + num);
		
		if(godFavors.get(god) > MAX_FAVOR)
			godFavors.set(god, MAX_FAVOR);
	}
	
	public void decreaseFavor(int god, int num)
	{
		godFavors.set(god, godFavors.get(god) - num);
		
		if(godFavors.get(god) < MIN_FAVOR)
			godFavors.set(god, MIN_FAVOR);
	}
}
