package com.favorofthegods.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.favorofthegods.FavorOfTheGods;
import com.favorofthegods.gods.Gods.EnumGods;

public class BlockListClient {
	public static void preInit()
	{

	}
	
	public static void init()
	{
		ItemModelMesher render = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		Item itemBlockAltar = GameRegistry.findItem(FavorOfTheGods.MODID, BlockList.altar.NAME);
		render.register(itemBlockAltar, 0, new ModelResourceLocation(FavorOfTheGods.prependModID(BlockList.altar.NAME, ':'), "inventory"));
		
		Item itemBlockDesertPig0 = GameRegistry.findItem(FavorOfTheGods.MODID, BlockList.desertPig0.NAME);
		render.register(itemBlockDesertPig0, 0, new ModelResourceLocation(FavorOfTheGods.prependModID(BlockList.desertPig0.NAME, ':'), "inventory"));
		Item itemBlockStefan0 = GameRegistry.findItem(FavorOfTheGods.MODID, BlockList.stefan0.NAME);
		render.register(itemBlockStefan0, 0, new ModelResourceLocation(FavorOfTheGods.prependModID(BlockList.stefan0.NAME, ':'), "inventory"));
	}
}
