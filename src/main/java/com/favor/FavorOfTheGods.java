package com.favor;

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

import com.favor.gods.GodDesertPig;
import com.favor.gods.GodStefan;

@Mod(modid = FavorOfTheGods.MODID, name = "Favor of the Gods", version = FavorOfTheGods.VERSION)
public class FavorOfTheGods {
	public static final String MODID = "favorofthegods";
	public static final String VERSION = "0.0.0";
	
	@Mod.Instance(FavorOfTheGods.MODID)
	public static FavorOfTheGods instance;
	public static SimpleNetworkWrapper network;
	
	@SidedProxy(clientSide = "com.favor.ClientOnlyProxy", serverSide = "com.favor.ServerOnlyProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		network = NetworkRegistry.INSTANCE.newSimpleChannel("FavorOfTheGods");
		network.registerMessage(PacketHandler.Handler.class, PacketHandler.class, 0, Side.CLIENT);
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
		MinecraftForge.EVENT_BUS.register(new EventList());
		MinecraftForge.EVENT_BUS.register(new GodStefan());
		MinecraftForge.EVENT_BUS.register(new GodDesertPig());
	}
	
	public static String prependModID(String name, char letter)
	{
		return MODID + letter + name;
	}
}
