package com.favorofthegods;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import com.favorofthegods.gods.Gods;
import com.favorofthegods.gui.GUIHandler;

@Mod(modid = FavorOfTheGods.MODID, name = "Favor of the Gods", version = FavorOfTheGods.VERSION)
public class FavorOfTheGods {
	public static final String MODID = "favorofthegods";
	public static final String VERSION = "0.0.0";

	@Mod.Instance(FavorOfTheGods.MODID)
	public static FavorOfTheGods instance;
	
	@SidedProxy(clientSide = "com.favorofthegods.ClientOnlyProxy", serverSide = "com.favorofthegods.ServerOnlyProxy")
	public static CommonProxy proxy;
	
	public static SimpleNetworkWrapper network;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit();
		network = NetworkRegistry.INSTANCE.newSimpleChannel("Favor");
		network.registerMessage(PacketFavors.Handler.class, PacketFavors.class, 0, Side.CLIENT);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();
		MinecraftForge.EVENT_BUS.register(new EventList());
		Gods.initEvents();
	}
	
	public static String prependModID(String name, char letter)
	{
		return MODID + letter + name;
	}
}
