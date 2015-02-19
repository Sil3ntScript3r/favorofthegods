package com.favorofthegods.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.favorofthegods.FavorOfTheGods;
import com.favorofthegods.PacketFavors;

public class GUIHandler implements IGuiHandler {

	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == 0)
		{
			System.out.println("GuiAltar Server Side");
			FavorOfTheGods.network.sendTo(new PacketFavors(player, new BlockPos(x, y, z)), (EntityPlayerMP)player);
		}
		return null;
	}

	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == 0)
		{
			return new GUIAltar(player, world, x, y, z);
		}
		return null;
	}

}
