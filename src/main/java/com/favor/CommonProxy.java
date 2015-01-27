package com.favor;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.favor.altar.BlockAltar;
import com.favor.altar.TileAltar;
import com.favor.items.ItemDecreaseFavor;
import com.favor.items.ItemFavorCheck;
import com.favor.items.ItemIncreaseFavor;

public abstract class CommonProxy implements IGuiHandler {
	public static ItemFavorCheck favorCheck;
	public static ItemIncreaseFavor increaseFavor;
	public static ItemDecreaseFavor decreaseFavor;
	public static BlockAltar altar;
	private static final Map<String, NBTTagCompound> favorData = new HashMap<String, NBTTagCompound>();
	
	public void preInit()
	{
		favorCheck = (ItemFavorCheck)new ItemFavorCheck().setUnlocalizedName("favorCheck");
		GameRegistry.registerItem(favorCheck, "favorCheck");
		
		increaseFavor = (ItemIncreaseFavor)new ItemIncreaseFavor().setUnlocalizedName("increaseFavor");
		GameRegistry.registerItem(increaseFavor, "increaseFavor");
		
		decreaseFavor = (ItemDecreaseFavor)new ItemDecreaseFavor().setUnlocalizedName("decreaseFavor");
		GameRegistry.registerItem(decreaseFavor, "decreaseFavor");
		
		altar = (BlockAltar)new BlockAltar(Material.wood).setUnlocalizedName("blockAltar");
		GameRegistry.registerBlock(altar, "blockAltar");
		GameRegistry.registerTileEntity(TileAltar.class, "tileAltar");
	}

	public void init()
	{

	}

	public void postInit()
	{

	}
	
	public void registerRenderers(){}
	
	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
	
	public static void storeEntityData(String name, NBTTagCompound compound)
	{
		favorData.put(name, compound);
	}
	
	public static NBTTagCompound getEntityData(String name)
	{
		return favorData.remove(name);
	}
	
	abstract public boolean playerIsInCreativeMode(EntityPlayer player);
}
