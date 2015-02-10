package com.favorofthegods.favornetwork;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
/**
 * Main class that handles the entire Favor system
 * @author Sil3ntScript3r
 */
public class FavorHandler {
	// Init some values for use other places
	public static final int MAX_FAVOR = 10000;
	public static final int MIN_FAVOR = -10000;
	public static final int[] RANKS = new int[]{0, 500, 2500, 5000, 7500, MAX_FAVOR};
	
	/**
	 * Returns the Favor with the name entered
	 * @param ownerName Favor to retrieve
	 * @return The Favor with the name specified
	 */
	public static Favor getFavor(String ownerName)
	{
		// Make sure there is a server
		if(MinecraftServer.getServer() == null)
			return null;
		
		// Get the World object, and the Favor data
		World world = MinecraftServer.getServer().worldServers[0];
		Favor data = (Favor)world.loadItemData(Favor.class, ownerName);
		
		// If the Favor data doesn't exist, return null
		if(data == null)
		{
			return null;
		}
		
		return data;
	}
	
	/**
	 * Create a Favor object with the name entered.
	 * If it already exists, don't create another
	 * @param ownerName Name of the Favor to create
	 */
	public static void createFavor(String ownerName)
	{
		if(MinecraftServer.getServer() == null)
			return;
		
		World world = MinecraftServer.getServer().worldServers[0];
		if(world.loadItemData(Favor.class, ownerName) == null)
		{
			Favor data = new Favor(ownerName);
			world.setItemData(ownerName, data);
		}
		else
		{
			System.out.println("Favor with that name already existed");
		}
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
		
		data.decreaseFavor(god, amount);
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
	
	/**
	 * Adds a player to the list of followers of a religion
	 * @param name 		Religion to add them to
	 * @param player 	Player to add
	 */
	public static void addFollower(String name, EntityPlayer player)
	{
		Favor favor = FavorHandler.getFavor(name);
		
		if(!favor.followers.contains(player))
		{
			favor.followers.add(player);
			System.out.println("Added " + player + " to " + favor.followers);
			favor.markDirty();
		}
	}
	
	public static boolean isFollowerOf(String name, EntityPlayer player)
	{
		Favor favor = getFavor(name);
		
		if(favor != null)
		{
			if(favor.followers.contains(player))
			{
				System.out.println(name + " contains " + player);
				return true;
			}
		}
		
		return false;
	}
}
