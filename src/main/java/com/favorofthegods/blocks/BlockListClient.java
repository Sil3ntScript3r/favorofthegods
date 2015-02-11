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
		Item itemBlockRank0 = GameRegistry.findItem(FavorOfTheGods.MODID, BlockList.blockRank0.getName());
		ModelBakery.addVariantName(itemBlockRank0,
				FavorOfTheGods.MODID + ":" + BlockList.blockRank0.getName() + "_DesertPig",
				FavorOfTheGods.MODID + ":" + BlockList.blockRank0.getName() + "_Stefan");
	}
	
	public static void init()
	{
		ItemModelMesher render = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		Item itemBlockAltar = GameRegistry.findItem(FavorOfTheGods.MODID, BlockList.altar.getName());
		render.register(itemBlockAltar, 0, new ModelResourceLocation(FavorOfTheGods.prependModID(BlockList.altar.getName(), ':'), "inventory"));
		
		Item itemBlockRank0 = GameRegistry.findItem("favorofthegods", "blockRank0");
		render.register(itemBlockRank0, EnumGods.DESERT_PIG.getMetadata(), new ModelResourceLocation("favorofthegods:blockRank0_DesertPig", "inventory"));
		render.register(itemBlockRank0, EnumGods.STEFAN.getMetadata(), new ModelResourceLocation("favorofthegods:blockRank0_Stefan", "inventory"));
	}
}
