package com.favorofthegods;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.favorofthegods.altar.TileAltar;
import com.favorofthegods.favornetwork.FavorHandler;

public class PlayerProps implements IExtendedEntityProperties {
	private static final String TAG = "FavorOfTheGods";
	private final EntityPlayer player;
	
	private String religionName;

	public PlayerProps(EntityPlayer player)
	{
		this.player = player;
	}
	
	public static void register(EntityPlayer player)
	{
		player.registerExtendedProperties(TAG, new PlayerProps(player));
	}
	
	public static PlayerProps get(EntityPlayer player)
	{
		return (PlayerProps)player.getExtendedProperties(TAG);
	}
	
	public static void saveProxy(EntityPlayer player)
	{
		PlayerProps props = PlayerProps.get(player);
		
		NBTTagCompound savedData = new NBTTagCompound();
		props.saveNBTData(savedData);
		CommonProxy.storeEntityData(getSaveKey(player), savedData);
	}
	
	public static void loadProxy(EntityPlayer player)
	{
		PlayerProps props = PlayerProps.get(player);
		NBTTagCompound savedData = CommonProxy.getEntityData(getSaveKey(player));
		
		if(savedData != null)
		{
			props.loadNBTData(savedData);
		}
	}
	
	public static String getSaveKey(EntityPlayer player)
	{
		return player.getName() + ":" + TAG;
	}
	
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound props = new NBTTagCompound();

		if(religionName != null)
			props.setString("religionName", religionName);

		compound.setTag(TAG, props);
	}

	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound props = (NBTTagCompound)compound.getTag(TAG);
		
		if(props.hasKey("religionName"))
			religionName = props.getString("religionName");
		
		System.out.println(religionName);
		
		FavorHandler.addFollower(religionName, player);
	}
	
	public boolean hasReligion()
	{
		if(religionName == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void setReligionName(String name)
	{
		religionName = name;
	}
	
	public String getReligionName()
	{
		return religionName;
	}

	public void init(Entity entity, World world)
	{

	}
}
