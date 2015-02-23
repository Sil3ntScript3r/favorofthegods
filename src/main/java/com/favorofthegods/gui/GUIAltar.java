package com.favorofthegods.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
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
	private static final int bgWidth = 256;
	private static final int bgHeight = 170;
	private static final int favorYChange = 50;
	
	private int x, y, z;
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

		backgroundImage = new ResourceLocation(FavorOfTheGods.MODID + ":" + "textures/gui/GuiAltar.png");
	}
	
	public void initGui()
	{
		this.buttonList.clear();
	}
	
	public void drawScreen(int mouseX, int mouseY, float renderPartialTicks)
	{
        super.drawScreen(mouseX, mouseY, renderPartialTicks);
		int x = (width - bgWidth) / 2;
		int y = (height - bgHeight) / 2;
		int favorY = y + 30;
		int leftX = x + 8;
		int rightX = x + 141;
		int Y1 = y + 30;
		int Y2 = Y1 + 50;
		TileAltar altar = (TileAltar)world.getTileEntity(new BlockPos(this.x, this.y, this.z));

		Minecraft.getMinecraft().getTextureManager().bindTexture(backgroundImage);
		
		drawTexturedModalRect(x, y, 0, 0, bgWidth, bgHeight);

		// TODO: Add additional pages to show past 6 gods
		// Need more gods to set this up completely
		int godCountBox = 0;
		int godCountText = 0;
		double godsOnPage;

		if(FavorHandler.getFavorSize(player) < 6)
			godsOnPage = FavorHandler.getFavorSize(player);
		else
			godsOnPage = 6;
		
		// Draw all the boxes and the indicator
		for(int c = 0; c < Math.ceil(godsOnPage / 2); c++)
		{
			if(c == Math.ceil(godsOnPage / 2) - 1 && godsOnPage % 2 == 1)
			{
				drawTexturedModalRect(leftX + 2, (favorY + (favorYChange * c)) + 3, 0, 198, 102, 21); 													// Background
				drawTexturedModalRect((leftX + 2) + 50 + (int)(altar.getFavor(godCountBox) / 200), (favorY + (favorYChange * c)) + 3, 0, 235, 2, 21); 	// Indicator
				drawTexturedModalRect(leftX, (favorY + (favorYChange * c)), 0, 170, 107, 26);															// Outline
				godCountBox++;
				break;
			}
			
			for(int i = 0; i < 2; i++)
			{
				if(i % 2 == 0)
				{
					drawTexturedModalRect(leftX + 2, (favorY + (favorYChange * c)) + 3, 0, 198, 102, 21); 													// Background
					drawTexturedModalRect((leftX + 2) + 50 + (int)(altar.getFavor(godCountBox) / 200), (favorY + (favorYChange * c)) + 3, 0, 235, 2, 21); 	// Indicator
					drawTexturedModalRect(leftX, (favorY + (favorYChange * c)), 0, 170, 107, 26);															// Outline
					godCountBox++;
				}
				else
				{
					drawTexturedModalRect(rightX + 2, (favorY + (favorYChange * c)) + 3, 0, 198, 102, 21);
					drawTexturedModalRect((rightX + 2) + 50 + (int)(altar.getFavor(godCountBox) / 200), (favorY + (favorYChange * c)) + 3, 0, 235, 2, 21);
					drawTexturedModalRect(rightX, (favorY + (favorYChange * c)), 0, 170, 107, 26);
					godCountBox++;
				}
			}
		}
		
		// Draw all the text [FOR SOME REASON CAN'T DO THIS WITH THE BOXES?]
		for(int c = 0; c < Math.ceil(godsOnPage / 2); c++)
		{
			if(c == Math.ceil(godsOnPage / 2) - 1 && godsOnPage % 2 == 1)
			{
				drawCenteredString(this.fontRendererObj, Gods.godNames.get(godCountText), leftX + 52, (favorY + (favorYChange * c)) + 4, 0x00999999);
				drawCenteredString(this.fontRendererObj, "" + altar.getFavor(godCountText), leftX + 52, (favorY + (favorYChange * c)) + 16, 0x00999999);
				godCountText++;
				break;
			}
			
			for(int i = 0; i < 2; i++)
			{
				if(i % 2 == 0)
				{
					drawCenteredString(this.fontRendererObj, Gods.godNames.get(godCountText), leftX + 52, (favorY + (favorYChange * c)) + 4, 0x00999999);
					drawCenteredString(this.fontRendererObj, "" + altar.getFavor(godCountText), leftX + 52, (favorY + (favorYChange * c)) + 16, 0x00999999);
					godCountText++;
				}
				else
				{
					drawCenteredString(this.fontRendererObj, Gods.godNames.get(godCountText), rightX + 52, (favorY + (favorYChange * c)) + 4, 0x00999999);
					drawCenteredString(this.fontRendererObj, "" + altar.getFavor(godCountText), rightX + 52, (favorY + (favorYChange * c)) + 16, 0x00999999);
					godCountText++;
				}
			}
		}
	}
	
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}
