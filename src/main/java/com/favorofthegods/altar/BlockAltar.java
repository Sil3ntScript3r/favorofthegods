package com.favorofthegods.altar;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.favorofthegods.FavorOfTheGods;
import com.favorofthegods.PlayerProps;
import com.favorofthegods.favornetwork.FavorHandler;

public class BlockAltar extends Block implements ITileEntityProvider {
	public static final String NAME = "blockAltar";
	
	public BlockAltar()
	{
		super(Material.wood);
		GameRegistry.registerBlock(this, NAME);
		GameRegistry.registerTileEntity(TileAltar.class, "tileAltar");
		setUnlocalizedName(FavorOfTheGods.prependModID(NAME, '_'));
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	// When block is right clicked, set the clicker to this religion
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileAltar altar = (TileAltar)world.getTileEntity(pos);
		
		if(!world.isRemote)
		{
			PlayerProps props = PlayerProps.get(player);
			if(props != null)
			{
				if(!props.hasReligion())
				{
					if(altar.getReligionName() == null)
					{
						FavorHandler.createFavor(player.getName());
						altar.setReligionName(player.getName());
					}
					
					props.setReligionName(player.getName());
					FavorHandler.addFollower(altar.getReligionName(), player);
					player.addChatComponentMessage(new ChatComponentText("You are now a follower of the new religion " + "§9" + props.getReligionName() + "§r" + "."));
				}
				else if(props.hasReligion() && altar.getReligionName() == null)
				{
					altar.setReligionName(props.getReligionName());
					player.addChatComponentMessage(new ChatComponentText("This Altar now follows " + "§9" + props.getReligionName() + "§r" + "."));
				}
				else if(FavorHandler.isFollowerOf(altar.getReligionName(), player))
				{
					player.addChatComponentMessage(new ChatComponentText("You are already a follower " + "§9" + altar.getReligionName() + "§r" + "."));
				}
				else
				{
					player.addChatComponentMessage(new ChatComponentText("You can not join " + "§9" + altar.getReligionName() + "§r" + ", as you already are a follower of " + "§9" + props.getReligionName() + "§r" + "."));
				}
			}
		}

		altar.onInteract(player);

		return true;
	}

	// When the Altar is destroyed by a player, call lightning down and make it explode
	// TODO: Make it not explode if it's not followed by a God
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
		}
	}

	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileAltar();
	}
}
