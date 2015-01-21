package com.favor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class Favor implements IExtendedEntityProperties {
	// Set the most and least favor you're allowed to acquire for any one god
	private static final int MAX_FAVOR = 1000;
	private static final int MIN_FAVOR = 0;
	
	// Set the name of the property
	public static final String FAVOR_TAG = "Favor";
	
	private final EntityPlayer player;
	
	// Store the favors of the gods
	private int[] godFavors = new int[2];
	
	// When Favor is created, set player to a player passed in
	public Favor(EntityPlayer player)
	{
		this.player = player;
		godFavors[0] = 257;
		godFavors[1] = -59;
	}
	
	// Call to assign Favor to a player
	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(Favor.FAVOR_TAG, new Favor(player));
	}
	
	// Retrieve the Favor from a player
	public static final Favor get(EntityPlayer player)
	{
		return (Favor)player.getExtendedProperties(FAVOR_TAG);
	}
	
	// Save Favor data to a Tag
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();
		// TODO: Why NBT tag no save
		properties.setIntArray("godFavors", godFavors);
		
		properties.setTag(FAVOR_TAG, properties);
		
		System.out.println("FAVOR DATA SAVED");
	}

	// Load Favor data from a tag
	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound)compound.getTag(FAVOR_TAG);
		
		godFavors = properties.getIntArray("godFavors");
		
		System.out.println("--Favor--");
		System.out.println("Length: " + godFavors.length);
		System.out.println("Stefan: " + godFavors[0]);
		System.out.println("Desert Pig: " + godFavors[1]);
	}

	@Override
	public void init(Entity entity, World world)
	{
		
	}
	
	public void increaseFavor(int num, int god)
	{
		
	}
}