package com.favor.gods;

import java.util.List;

public class GodBlood extends Gods {
	private static List[] altarBlocks;
	
	public GodBlood()
	{
		godNames.add(2, "Blood");
		
		altarBlocks = initAltarBlocks();
		godBlocks.add(GOD_BLOOD, altarBlocks);
	}
}
