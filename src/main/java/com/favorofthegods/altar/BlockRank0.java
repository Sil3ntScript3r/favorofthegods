package com.favorofthegods.altar;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.favorofthegods.FavorOfTheGods;
import com.favorofthegods.gods.Gods.EnumGods;

public class BlockRank0 extends Block {
	private final String NAME = "blockRank0";
	public static final PropertyEnum PROPERTY_GOD = PropertyEnum.create("god", EnumGods.class);
	
	public BlockRank0(Material material)
	{
		super(material);
		GameRegistry.registerBlock(this, ItemBlockRank0.class, NAME);
		setUnlocalizedName(FavorOfTheGods.prependModID(NAME, '_'));
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public String getName()
	{
		return NAME;
	}
	
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		EnumGods god = EnumGods.byMetadata(meta);
		
		return this.getDefaultState().withProperty(PROPERTY_GOD, god);
	}
	
	public IBlockState getStateFromMeta(int meta)
	{
		EnumGods god = EnumGods.byMetadata(meta);
		return this.getDefaultState().withProperty(PROPERTY_GOD, god);
	}
	
	public int getMetaFromState(IBlockState state)
	{
		EnumGods god = (EnumGods)state.getValue(PROPERTY_GOD);
		return god.getMetadata();
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		EnumGods[] allGods = EnumGods.values();
		for(EnumGods god : allGods)
		{
			list.add(new ItemStack(item, 1, god.getMetadata()));
		}
	}
	
	public int damageDropped(IBlockState state)
	{
		EnumGods god = (EnumGods)state.getValue(PROPERTY_GOD);
		return god.getMetadata();
	}
	
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[]{PROPERTY_GOD});
	}
}
