package com.favor;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class Favor implements IExtendedEntityProperties {
	// Set the most and least favor you're allowed to acquire for any one god
	public static final int MAX_FAVOR = 1000;
	public static final int NEUTRAL_FAVOR = 0;
	public static final int MIN_FAVOR = -1000;
	
	// Set the name of the property
	public static final String FAVOR_TAG = "Favor";
	public static Set<EntityPlayer> players = new HashSet<EntityPlayer>();
	
	private final EntityPlayer player;
	
	// TODO: Stop being so bad and change godFavors to a List(assuming that works)
	// Store the favors of the gods
	private int[] godFavors = new int[2];
	
	public static int numGods = 2;
	
	// When Favor is created, set player to a player passed in
	public Favor(EntityPlayer player)
	{
		this.player = player;
		godFavors[0] = NEUTRAL_FAVOR;
		godFavors[1] = NEUTRAL_FAVOR;
	}

	// Save Favor data to a Tag
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();

		properties.setIntArray("godFavors", godFavors);

		compound.setTag(FAVOR_TAG, properties);
		FavorOfTheGods.network.sendTo(new PacketHandler(godFavors, player), (EntityPlayerMP) player);
	}

	// Load Favor data from a tag
	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound)compound.getTag(FAVOR_TAG);
		
		godFavors = properties.getIntArray("godFavors");
		
		System.out.println("--Favor--");
		System.out.println("Stefan: " + godFavors[0]);
		System.out.println("Desert Pig: " + godFavors[1]);
	}

	// Required implemented method
	@Override
	public void init(Entity entity, World world)
	{
		
	}
	
	// Sets the favor to the specified amount for the specified god
	public void setFavor(int num, int god)
	{
		godFavors[god] = num;
	}
	
	// Increase the favor of a god given
	public void increaseFavor(int num, int god)
	{
		godFavors[god] += num;
		
		if(godFavors[god] > MAX_FAVOR)
			godFavors[god] = MAX_FAVOR;
		
		syncProperties();
	}
	
	// Decrease the favor of a god given
	public void decreaseFavor(int num, int god)
	{
		godFavors[god] -= num;
		
		if(godFavors[god] < MIN_FAVOR)
			godFavors[god] = MIN_FAVOR;
		
		syncProperties();
	}
	
	// Return the favor of a god given
	public int getFavor(int god)
	{
		return godFavors[god];
	}
	
	// Call to assign Favor to a player
	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(Favor.FAVOR_TAG, new Favor(player));
		players.add(player);
	}
	
	// Retrieve the Favor from a player
	public static final Favor get(EntityPlayer player)
	{
		return (Favor)player.getExtendedProperties(FAVOR_TAG);
	}
	
	// Save the Favor data to a safe location so it doesn't reset on death
	public static void saveProxyData(EntityPlayer player)
	{
		Favor playerData = Favor.get(player);
		NBTTagCompound savedData = new NBTTagCompound();
		
		playerData.saveNBTData(savedData);
		CommonProxy.storeEntityData(getSaveKey(player), savedData);
	}
	
	// Load the Favor data from the safe location so we may use it again
	public static void loadProxyData(EntityPlayer player)
	{
		Favor playerData = Favor.get(player);
		NBTTagCompound savedData = CommonProxy.getEntityData(getSaveKey(player));
		
		if(savedData != null)
		{
			playerData.loadNBTData(savedData);
		}
		
		playerData.syncProperties();
	}

	// Sync up the client with the server
	public final void syncProperties()
	{
		// TODO: Make the property syncing work better
		if(!player.worldObj.isRemote)
		{
			FavorOfTheGods.network.sendTo(new PacketHandler(godFavors, player), (EntityPlayerMP) player);
		}
	}
	
	// Get the name we save our data under for the proxy data
	private static String getSaveKey(EntityPlayer player)
	{
		return player.getName() + ":" + FAVOR_TAG;
	}
}
