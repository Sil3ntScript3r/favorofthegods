package com.favor.altar;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public class TileAltar extends TileEntity {
	private boolean isMaster, hasMaster;
	private int masterX, masterY, masterZ;

	public void writeToNBT(NBTTagCompound data)
	{
		super.writeToNBT(data);
		data.setInteger("masterX", masterX);
		data.setInteger("masterY", masterY);
		data.setInteger("masterZ", masterZ);
		data.setBoolean("hasMaster", hasMaster);
		data.setBoolean("isMaster", isMaster);
		
		if(hasMaster && isMaster)
		{

		}
	}
	
	public void readFromNBT(NBTTagCompound data)
	{
		super.readFromNBT(data);
		masterX = data.getInteger("masterX");
		masterY = data.getInteger("masterY");
		masterZ = data.getInteger("masterZ");
		hasMaster = data.getBoolean("hasMaster");
		isMaster = data.getBoolean("isMaster");
		
		if(hasMaster && isMaster)
		{
			
		}
	}
	
	public int checkRank()
	{
		return 0;
	}

	public boolean hasMaster()
	{
		return hasMaster;
	}
	
	public boolean isMaster()
	{
		return isMaster;
	}
	
	public BlockPos masterPos()
	{
		return new BlockPos(masterX, masterY, masterZ);
	}
}
