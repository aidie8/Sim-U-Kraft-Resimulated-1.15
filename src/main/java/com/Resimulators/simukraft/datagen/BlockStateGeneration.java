package com.resimulators.simukraft.datagen;


import com.resimulators.simukraft.Reference;
import com.resimulators.simukraft.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;


public class BlockStateGeneration extends BlockStateProvider {


    public BlockStateGeneration(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Reference.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile rainbowLight = cubeAll(ModBlocks.RAINBOW_LIGHT);
        simpleBlock(ModBlocks.RAINBOW_LIGHT,rainbowLight);

        ModelFile compositeBrick = cubeAll(ModBlocks.COMPOSITE_BRICK);
        simpleBlock(ModBlocks.COMPOSITE_BRICK,compositeBrick);

        ModelFile cheeseBlock = cubeAll(ModBlocks.CHEESE_BLOCK);
        simpleBlock(ModBlocks.CHEESE_BLOCK,cheeseBlock);
        for (DyeColor color : DyeColor.values()) {
            if (color.equals(DyeColor.LIGHT_GRAY))continue;

        }
    }
}
