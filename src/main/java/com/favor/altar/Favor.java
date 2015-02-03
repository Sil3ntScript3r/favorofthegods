package com.favor.altar;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.BlockPos;

import com.favor.gods.Gods;

public class Favor {
	public static final int MAX_FAVOR = 5000;
	public static final int MIN_FAVOR = -5000;
	
	private static final String TAG = "Altar";
	
	private BlockPos altarPos;
	private List<Integer> godFavors;
	
	public Favor(BlockPos pos)
	{
		altarPos = pos;
		
		godFavors = new ArrayList<Integer>();
		
		for(String s : Gods.godNames)
		{
			godFavors.add(0);
		}
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
