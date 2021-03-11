package com.resimulators.simukraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.resimulators.simukraft.SimuKraft;
import com.resimulators.simukraft.common.entity.sim.SimEntity;
import com.resimulators.simukraft.common.tileentity.TileResidential;
import com.resimulators.simukraft.common.world.Faction;
import com.resimulators.simukraft.common.world.SavedWorldData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;


public class GuiResidential extends Screen {

    private Faction faction;
    private TileResidential tile;
    private BlockPos pos;
    private String name;
    private Button Done;
    private ArrayList<Integer> occupants;
    protected GuiResidential(ITextComponent titleIn, BlockPos pos) {
        super(titleIn);
        faction = SavedWorldData.get(SimuKraft.proxy.getClientWorld()).getFactionWithPlayer(SimuKraft.proxy.getClientPlayer().getUniqueID());
        tile =(TileResidential) SimuKraft.proxy.getClientWorld().getTileEntity(pos);
        this.pos = pos;
        name = faction.getHouseByID(tile.getHouseID()).getName();
    }


    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        font.drawString(stack,getTitle().getString(),(width/2)- font.getStringWidth(getTitle().getString())/2,height/6, Color.WHITE.getRGB());
        font.drawString(stack,"Name: " + name.replace("_"," "),60 - font.getStringWidth("Name: " + name)/2,height/2-50, Color.WHITE.getRGB());
        font.drawString(stack,"Occupants: ",60,height/2-30,Color.WHITE.getRGB());
        if (occupants != null){
            int i = 0;
            for (int id: occupants){
                SimEntity sim = (SimEntity) SimuKraft.proxy.getClientWorld().getEntityByID(id);
                font.drawString(stack,sim.getCustomName().getString(),60,height/2 + i*20,Color.WHITE.getRGB());
            }
        }
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);

        addButton(Done = new Button(width-120,height-30,100,20,new StringTextComponent("Done"), Done ->{
            minecraft.displayGuiScreen(null);
        }));
    }


    public void setOccupants(ArrayList<Integer> ids){
        this.occupants = ids;

    }
}
