package com.example.examplemod.Structures;

import com.example.examplemod.ExampleMod;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.*;

import java.util.List;
import java.util.Locale;

public class ExamplePieces {
    //add the Startpiece to the JigsawManager and start generating the Structure
    public static void addPieces(ChunkGenerator<?> chunkGenerator, TemplateManager templateManager, BlockPos structurePos, List<StructurePiece> structurePieces, SharedSeedRandom random){
        JigsawManager.addPieces(new ResourceLocation(ExampleMod.MODID,"example/base"),4,ExamplePiece::new,chunkGenerator,templateManager,structurePos,structurePieces,random);
    }
    //Examplepiece that extends Abstractvillagepiece, so our Structure will generate properly
    public static class ExamplePiece extends AbstractVillagePiece{

        public ExamplePiece(TemplateManager templateManagerIn, JigsawPiece jigsawPieceIn, BlockPos posIn, int groundLevelDelta, Rotation rotation, MutableBoundingBox p_i51346_7_) {
            super(EXAMPLETYPE, templateManagerIn, jigsawPieceIn, posIn, groundLevelDelta, rotation, p_i51346_7_);
        }

        public ExamplePiece(TemplateManager templateManagerIn, CompoundNBT p_i51347_2_) {
            super(templateManagerIn, p_i51347_2_, EXAMPLETYPE);
        }
    }
    //New StructureType for our Pieces
    public static final IStructurePieceType EXAMPLETYPE = register(ExamplePiece::new,"example");

    //Register the Structuretype in the Registry
    static IStructurePieceType register(IStructurePieceType type,String key){
        return Registry.register(Registry.STRUCTURE_PIECE,key.toLowerCase(Locale.ROOT),type);
    }
    //this is gonna be wild i promise
    static {
        //RuleStructureProcessor for Transforming around 10% of the Stone Bricks to Cracked Stone Bricks
        ImmutableList<StructureProcessor> transformStone = ImmutableList.of(
                new RuleStructureProcessor(ImmutableList.of(
                        new RuleEntry(new RandomBlockMatchRuleTest(Blocks.STONE_BRICKS,0.1f),AlwaysTrueRuleTest.INSTANCE,Blocks.CRACKED_STONE_BRICKS.getDefaultState())
                ))
        );
        //Register the JigsawPattern inside the Manager, so our JigsawBlocks have access to them
        JigsawManager.REGISTRY.register(new JigsawPattern(
                //The Primary Pool to pull from
                new ResourceLocation(ExampleMod.MODID,"example/base"),
                //The Fallback in case the first one is empty
                new ResourceLocation(ExampleMod.MODID,"empty"),
                //If you want more Jigsawpieces in the Pool add them in this list, the Integer at the End is the Weight of each Piece
                ImmutableList.of(
                        new Pair<>(new SingleJigsawPiece(new ResourceLocation(ExampleMod.MODID,"example/base/examplebase").toString(),transformStone),5)
                ),
                //Placementbehaviour, if you want it to match the Terrain, select JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING instead of Rigid
                JigsawPattern.PlacementBehaviour.RIGID));
        JigsawManager.REGISTRY.register(new JigsawPattern(
                new ResourceLocation(ExampleMod.MODID,"example/streets"),
                new ResourceLocation(ExampleMod.MODID,"empty"),
                ImmutableList.of(
                        new Pair<>(new SingleJigsawPiece(new ResourceLocation(ExampleMod.MODID,"example/streets/examplecorner").toString(),transformStone),5),
                        new Pair<>(new SingleJigsawPiece(new ResourceLocation(ExampleMod.MODID,"example/streets/examplethreeway").toString(),transformStone),5),
                        new Pair<>(new SingleJigsawPiece(new ResourceLocation(ExampleMod.MODID,"example/streets/examplestraight").toString(),transformStone),3)
                ),
                JigsawPattern.PlacementBehaviour.RIGID));
        JigsawManager.REGISTRY.register(new JigsawPattern(
                new ResourceLocation(ExampleMod.MODID,"example/decors"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(new SingleJigsawPiece(new ResourceLocation(ExampleMod.MODID,"example/decor/exampledecor_01").toString(),transformStone),5),
                        new Pair<>(new SingleJigsawPiece(new ResourceLocation(ExampleMod.MODID,"example/streets/exampledecor_02").toString(),transformStone),5)
                ),
                JigsawPattern.PlacementBehaviour.RIGID));
        JigsawManager.REGISTRY.register(new JigsawPattern(
                new ResourceLocation(ExampleMod.MODID,"example/houses"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(new SingleJigsawPiece(new ResourceLocation(ExampleMod.MODID,"example/houses/examplehouse").toString(),transformStone),5)
                ),
                JigsawPattern.PlacementBehaviour.RIGID));
    }
}
