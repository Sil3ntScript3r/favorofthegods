package com.favor.altar;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.favor.Favor;

public class BlockAltar extends Block implements ITileEntityProvider {
	public BlockAltar(Material material)
	{
		super(material);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	// When the block is placed by a player, associate the Altar with the player
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitx, float hity, float hitz, int meta, EntityLivingBase placer)
	{
		if(placer instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)placer;
			System.out.println("Altar placed");
			
			Favor favor = Favor.get(player);
			if(favor != null)
			{
				((TileAltar)world.getTileEntity(pos)).setOwner(player);
				favor.setAltarPos(pos);
				System.out.println(pos);
			}
		}
		
		return this.getDefaultState();
	}
	
	// When the Altar is destroyed by a player, call lightning down and make it explode
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
	{
		if(!world.isRemote)
		{
			world.spawnEntityInWorld(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ()));
			world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 6f, true);
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("The Gods are not pleased with your defiance!"));
		}
	}
	
	// When the block is spawned, check to see what rank the Altar is
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		TileEntity altar = world.getTileEntity(pos);
		if(altar != null && altar instanceof TileAltar)
		{
			TileAltar tilealtar = (TileAltar)altar;
			tilealtar.checkRank(world);
		}
	}

	public TileEntity createNewTileEntity(World world, int meta) {
		System.out.println("Altar tile entity created");
		return new TileAltar();
	}

}
