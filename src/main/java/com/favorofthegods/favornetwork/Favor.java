package com.favorofthegods.favornetwork;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.util.Constants;

import com.favorofthegods.gods.Gods;

public class Favor extends net.minecraft.world.WorldSavedData {
	// The list of every player following this religion
	public List<EntityPlayer> followers;
	
	// Every God's Favor
	private List<Integer> godFavors;
	
	// Which God is the main Favor of this religion
	private int mainGod;
	
	public Favor(String string)
	{
		super(string);
		// Init all the values to a default before it gets modified, and init the lists
		mainGod = -1;
		followers = new ArrayList<EntityPlayer>();
		godFavors = new ArrayList<Integer>();
		
		for(String s : Gods.godNames)
		{
			godFavors.add(0);
		}
	}

	// Load the Favors and main God from NBT
	public void readFromNBT(NBTTagCompound nbt)
	{
		// Load all the God Favors
		int[] favors = nbt.getIntArray("godFavors");
		
		for(int i = 0; i < favors.length; i++)
		{
			setFavor(i, favors[i]);
		}
		
		// Load which God is being Favored
		mainGod = nbt.getInteger("mainGod");
	}

	// Save the Favor and main God to NBT
	public void writeToNBT(NBTTagCompound nbt)
	{
		// Save all the God Favors
		int[] favors = new int[godFavors.size()];
		
		for(int i = 0; i < godFavors.size(); i++)
		{
			favors[i] = godFavors.get(i);
		}
		
		nbt.setIntArray("godFavors", favors);
		
		// Save which God is being Favored
		nbt.setInteger("mainGod", mainGod);
	}
	
	// Return the list of Favors
	public List<Integer> getFavors()
	{
		return godFavors;
	}
	
	// Get a certain God's Favor
	public int getFavor(int god)
	{
		return godFavors.get(god);
	}
	
	// Set a God's Favor to a certain amount
	public void setFavor(int god, int num)
	{
		godFavors.set(god, num);
	}
	
	// Get which God is the main
	public int getMain()
	{
		return mainGod;
	}
	
	// Set which God is the main
	public void setMain(int god)
	{
		mainGod = god;
	}
	
	// Increase a God's Favor by some amount
	public void increaseFavor(int god, int num)
	{
		godFavors.set(god, godFavors.get(god) + num);
		
		if(godFavors.get(god) > FavorHandler.MAX_FAVOR)
			godFavors.set(god, FavorHandler.MAX_FAVOR);
	}
	
	// Decrease a God's Favor by some amount
	public void decreaseFavor(int god, int num)
	{
		godFavors.set(god, godFavors.get(god) - num);
		
		if(godFavors.get(god) < FavorHandler.MIN_FAVOR)
			godFavors.set(god, FavorHandler.MIN_FAVOR);
	}
}
