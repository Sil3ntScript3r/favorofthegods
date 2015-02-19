package com.favorofthegods.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.favorofthegods.FavorOfTheGods;
import com.favorofthegods.altar.TileAltar;
import com.favorofthegods.favornetwork.FavorHandler;
import com.favorofthegods.gods.Gods;

public class GUIAltar extends GuiScreen {
	private int x, y, z;
	private int xSize, ySize;
	private EntityPlayer player;
	private World world;
	private ResourceLocation backgroundImage;
	
	public GUIAltar(EntityPlayer player, World world, int x, int y, int z)
	{
		this.player = player;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		xSize = 256;
		ySize = 170;

		backgroundImage = new ResourceLocation(FavorOfTheGods.MODID + ":" + "textures/gui/GuiAltar.png");
	}
	
	public void drawScreen(int mouseX, int mouseY, float renderPartialTicks)
	{
        super.drawScreen(mouseX, mouseY, renderPartialTicks);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		int leftX = x + 8;
		int rightX = x + 141;
		int Y1 = y + 30;
		int Y2 = Y1 + 50;
		TileAltar altar = (TileAltar)world.getTileEntity(new BlockPos(this.x, this.y, this.z));

		Minecraft.getMinecraft().getTextureManager().bindTexture(backgroundImage);
		
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		// Draw all the boxes and the indicator
		for(int i = 0; i < FavorHandler.getFavorSize(player); i++)
		{
			if(i % 2 == 0)
			{
				drawTexturedModalRect(leftX + 2, Y1 + 3, 0, 198, 102, 21); 											// Background
				drawTexturedModalRect((leftX + 2) + 50 + (int)(altar.getFavor(i) / 200), Y1 + 3, 0, 235, 2, 21); 	// Cursor
				drawTexturedModalRect(leftX, Y1, 0, 170, 107, 26);													// Outline
			}
			else
			{
				drawTexturedModalRect(rightX + 2, Y1 + 3, 0, 198, 102, 21);
				drawTexturedModalRect((rightX + 2) + 50 + (int)(altar.getFavor(i) / 200), Y1 + 3, 0, 235, 2, 21);
				drawTexturedModalRect(rightX, Y1, 0, 170, 107, 26);
			}
		}
		
		// Draw all the text [FOR SOME REASON CAN'T DO THIS WITH THE BOXES?]
		for(int i = 0; i < FavorHandler.getFavorSize(player); i++)
		{
			if(i % 2 == 0)
			{
				drawCenteredString(this.fontRendererObj, Gods.godNames.get(i), leftX + 52, Y1 + 4, 0x00999999);
				drawCenteredString(this.fontRendererObj, "" + altar.getFavor(i), leftX + 52, Y1 + 16, 0x00999999);
			}
			else
			{
				drawCenteredString(this.fontRendererObj, Gods.godNames.get(i), rightX + 52, Y1 + 4, 0x00999999);
				drawCenteredString(this.fontRendererObj, "" + altar.getFavor(i), rightX + 52, Y1 + 16, 0x00999999);
			}
		}
	}
	
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}
