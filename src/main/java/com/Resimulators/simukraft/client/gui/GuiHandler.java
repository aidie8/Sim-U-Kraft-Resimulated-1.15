package com.Resimulators.simukraft.client.gui;

import com.Resimulators.simukraft.Network;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.ArrayList;

public class GuiHandler {
    public static final int Builder = 1;
    public static final int Miner = 2;



    public static void openGui(ArrayList<Integer> ints, BlockPos pos, int id,int guiId){
        switch (guiId){
            case Builder:
                Minecraft.getInstance().displayGuiScreen(new BuilderGui(new StringTextComponent("Builder"),ints,pos,id));
                break;
            case Miner:
                Minecraft.getInstance().displayGuiScreen(new MinerGui(new StringTextComponent("Miner"),ints,pos,id));
        }
    }
}
