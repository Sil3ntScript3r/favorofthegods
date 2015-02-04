package com.favor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.favor.altar.TileAltar;

public class PlayerProps implements IExtendedEntityProperties {
	private static final String TAG = "FavorOfTheGods";
	private final EntityPlayer player;
	
	private BlockPos altarPos;
	private World world;

	public PlayerProps(EntityPlayer player)
	{
		this.player = player;
		altarPos = null;
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
		
		if(altarPos != null)
			props.setIntArray("altarPos", new int[]{altarPos.getX(), altarPos.getY(), altarPos.getZ()});
		
		compound.setTag(TAG, props);
	}

	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound props = (NBTTagCompound)compound.getTag(TAG);
		
		if(props.hasKey("altarPos"))
		{
			int[] pos = props.getIntArray("altarPos");
			altarPos = new BlockPos(pos[0], pos[1], pos[2]);
		}
	}
	
	public boolean checkAltar()
	{
		if(altarPos == null)
		{
			return false;
		}
		else if(world.getTileEntity(altarPos) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void setAltarPos(World world, BlockPos pos)
	{
		this.world = world;
		altarPos = pos;
	}
	
	public BlockPos getAltarPos()
	{
		return altarPos;
	}
	
	public TileAltar getAltar()
	{
		if(altarPos != null)
			return (TileAltar)world.getTileEntity(altarPos);
		else
			return null;
	}

	public void init(Entity entity, World world)
	{

	}
}
