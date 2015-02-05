package com.favor.favornetwork;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
/**
 * @author Sil3ntScript3r
 * Main class that handles the entire Favor system
 */
public class FavorHandler {
	// Init some values for use other places
	public static final int MAX_FAVOR = 10000;
	public static final int MIN_FAVOR = -10000;
	public static final int[] RANKS = new int[]{0, 500, 2500, 5000, 7500, MAX_FAVOR};
	
	// Load the Favor object from the world
	public static Favor getFavor(String ownerName)
	{
		// Make sure there is a server
		if(MinecraftServer.getServer() == null)
			return null;
		
		// Get the World object, and the Favor data
		World world = MinecraftServer.getServer().worldServers[0];
		Favor data = (Favor)world.loadItemData(Favor.class, ownerName);
		
		// If the Favor data doesn't exist, make it
		if(data == null)
		{
			data = new Favor(ownerName);
			world.setItemData(ownerName, data);
		}
		
		return data;
	}
	
	/**
	 * Increase the Favor of the specified religion, for the god, by an amount
	 * @param ownerName Religion it should increase
	 * @param god 		Which god is getting Favor
	 * @param amount 	How much it increases by
	 */
	public static void increaseFavor(String ownerName, int god, int amount)
	{
		Favor data = getFavor(ownerName);
		
		if(data == null)
		{
			System.out.println("Error: data was null");
			return;
		}
		
		data.increaseFavor(god, amount);
		data.markDirty();
	}
	
	/**
	 * Decrease the Favor of the specified religion, for the god, by an amount
	 * @param ownerName Religion it should decrease
	 * @param god 		Which god is getting Favor
	 * @param amount 	How much it decreases by
	 */
	public static void decreaseFavor(String ownerName, int god, int amount)
	{
		Favor data = getFavor(ownerName);
		
		if(data == null)
		{
			System.out.println("Error: data was null");
			return;
		}
		
		data.increaseFavor(god, amount);
		data.markDirty();
	}
	
	/**
	 * Set the Favor of a god to a certain amount. No adding, no fancy stuff
	 * @param ownerName Which religion is being modified
	 * @param god 		Which god is being modified
	 * @param amount	What to modify it too
	 */
	public static void setFavor(String ownerName, int god, int amount)
	{
		Favor data = getFavor(ownerName);
		
		if(data == null)
		{
			System.out.println("Error: data was null");
			return;
		}
		
		data.setFavor(god, amount);
		data.markDirty();
	}
}
