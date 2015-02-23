package com.favorofthegods.gods;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import com.favorofthegods.PlayerProps;
import com.favorofthegods.favornetwork.FavorHandler;

public class Gods {
	// Ints used to easily talk about a certain God
	public static final int GOD_STEFAN = 0;
	public static final int GOD_DESERTPIG = 1;
	public static final int GOD_BLOOD = 2;
	
	// List of all the blocks that can be used for the Altar
	// First list = God to choose, based on it's number
	// Array index = Rank of the block
	// Second list = All the blocks the God has added for that rank
	// All gods set this themself in their constructor
	public static List<List[]> godBlocks = new ArrayList<List[]>();
	
	// List of all the God's names, sorted by their number
	public static List<String> godNames = new ArrayList<String>();
	
	public static final int NUM_RANKS = 5;
	
	String name;
	
	Random rand = new Random();
	
	@SubscribeEvent
	public void playerUpdate(PlayerTickEvent event)
	{
		GodStefan.tick(event);
		GodDesertPig.tick(event);
		GodBlood.tick(event);
	}
	
	public static void tick(PlayerTickEvent event) {}

	// Return the list of blocks for a certain rank
	public static ArrayList<Block> getAltarBlocks(int god, int rank)
	{
		return (ArrayList<Block>)godBlocks.get(god)[rank];
	}

	public static void initEvents()
	{
		MinecraftForge.EVENT_BUS.register(new GodStefan());
		MinecraftForge.EVENT_BUS.register(new GodDesertPig());
		MinecraftForge.EVENT_BUS.register(new GodBlood());
	}
	
	public static int getRivalGod(int god)
	{
		if(god % 2 == 0)
		{
			return god + 1;
		}
		else
		{
			return god - 1;
		}
	}
	
	// Used by the gods to construct their ArrayList
	static List[] initAltarBlocks()
	{
		List[] list = new List[NUM_RANKS + 1];
		
		for(int i = 0; i <= NUM_RANKS; i++)
		{
			list[i] = new ArrayList<Block>();
		}
		
		return list;
	}
	
	void increaseFavor(EntityPlayer player, int god, int num, boolean rival)
	{
		if(PlayerProps.get(player) != null)
		{
			PlayerProps props = PlayerProps.get(player);
			if(props.hasReligion())
			{
				FavorHandler.increaseFavor(props.getReligionName(), god, num);
				
				if(rival)
					FavorHandler.decreaseFavor(props.getReligionName(), getRivalGod(god), (int)num / 2);
			}
		}
	}
	
	void decreaseFavor(EntityPlayer player, int god, int num)
	{
		if(PlayerProps.get(player) != null)
		{
			PlayerProps props = PlayerProps.get(player);
			if(props.hasReligion())
			{
				FavorHandler.decreaseFavor(props.getReligionName(), god, num);
			}
		}
	}
	
	public static enum EnumGods implements IStringSerializable
	{
		STEFAN(GOD_STEFAN, "Stefan"),
		DESERT_PIG(GOD_DESERTPIG, "DesertPig"),
		BLOOD(GOD_BLOOD, "Blood");
		
		private final int meta;
		private final String name;
		private static final EnumGods[] META_LOOKUP = new EnumGods[values().length];
				
		public int getMetadata()
		{
			return meta;
		}
		
		public String toString()
		{
			return name;
		}
		
		public static EnumGods byMetadata(int meta)
		{
			if(meta < 0 || meta >= META_LOOKUP.length)
			{
				meta = 0;
			}
			
			return META_LOOKUP[meta];
		}
		
		public String getName()
		{
			return this.name;
		}
		
		private EnumGods(int i_meta, String i_name)
		{
			this.meta = i_meta;
			this.name = i_name;
		}
		
		static
		{
			for(EnumGods gods : values())
			{
				META_LOOKUP[gods.getMetadata()] = gods;
			}
		}
	}
}
