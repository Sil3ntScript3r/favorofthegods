package com.favor;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = FavorOfTheGods.MODID, name = "Favor of the Gods", version = FavorOfTheGods.VERSION)
public class FavorOfTheGods {
	public static final String MODID = "favorofthegods";
	public static final String VERSION = "0.0.0";
	
	@Mod.Instance(FavorOfTheGods.MODID)
	public static FavorOfTheGods instance;
	
	@SidedProxy(clientSide = "favorofthegods.ClientOnlyProxy", serverSide = "favorofthegods.ServerOnlyProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();
	}
	
	public static String prependModID(String name, char letter)
	{
		return MODID + letter + name;
	}
}
