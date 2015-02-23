package com.favorofthegods.gods;

import java.util.List;

public class GodBlood extends Gods {
	private static List[] altarBlocks;
	
	public GodBlood()
	{
		godNames.add(GOD_BLOOD, "Blood");
		
		altarBlocks = initAltarBlocks();
		godBlocks.add(GOD_BLOOD, altarBlocks);
	}
}
