package com.favor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerProps implements IExtendedEntityProperties {
	private static final String TAG = "FavorOfTheGods";
	private final EntityPlayer player;
	
	private BlockPos altarPos;
	
	public PlayerProps(EntityPlayer player, BlockPos pos)
	{
		this.player = player;
		altarPos = pos;
	}
	
	public static void register(EntityPlayer player)
	{
		player.registerExtendedProperties(TAG, new Favor(player));
	}
	
	public static PlayerProps get(EntityPlayer player)
	{
		return (PlayerProps)player.getExtendedProperties(TAG);
	}
	
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound props = new NBTTagCompound();
		
		props.setIntArray("altarPos", new int[]{altarPos.getX(), altarPos.getY(), altarPos.getZ()});
		
		compound.setTag(TAG, props);
	}

	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound props = (NBTTagCompound)compound.getTag(TAG);
		
		int[] pos = props.getIntArray("altarPos");
		altarPos = new BlockPos(pos[0], pos[1], pos[2]);
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

	public void init(Entity entity, World world)
	{

	}
	
	public static String getSaveKey(EntityPlayer player)
	{
		return player.getName() + ":" + TAG;
	}

}
