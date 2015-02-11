package com.favorofthegods.altar;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import com.favorofthegods.gods.Gods.EnumGods;

public class ItemBlockRank0 extends ItemBlock {
	public ItemBlockRank0(Block block)
	{
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	public int getMetadata(int metadata)
	{
		return metadata;
	}
	
	public String getUnlocalizedName(ItemStack stack)
	{
		EnumGods god = EnumGods.byMetadata(stack.getMetadata());
		return super.getUnlocalizedName() + "." + god.toString();
	}
}
