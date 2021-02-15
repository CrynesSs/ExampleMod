package com.example.examplemod.Structures;

import com.example.examplemod.ExampleMod;
import com.mojang.datafixers.Dynamic;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;
import java.util.function.Function;

//Yes that is everything we need
public class ExampleStructure extends Structure<NoFeatureConfig> {
    public ExampleStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }
    //done
    protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
        int i = chunkGenerator.getSettings().getVillageDistance();
        int j = chunkGenerator.getSettings().getVillageSeparation();
        int k = x + i * spacingOffsetsX;
        int l = z + i * spacingOffsetsZ;
        int i1 = k < 0 ? k - i + 1 : k;
        int j1 = l < 0 ? l - i + 1 : l;
        int k1 = i1 / i;
        int l1 = j1 / i;
        ((SharedSeedRandom)random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 420691337);
        k1 = k1 * i;
        l1 = l1 * i;
        k1 = k1 + random.nextInt(i - j);
        l1 = l1 + random.nextInt(i - j);
        return new ChunkPos(k1, l1);
    }
    //done
    @Override
    public boolean canBeGenerated(BiomeManager biomeManagerIn, ChunkGenerator<?> generatorIn, Random randIn, int chunkX, int chunkZ, Biome biomeIn) {
        ChunkPos chunkPos = this.getStartPositionForPosition(generatorIn,randIn,chunkX,chunkZ,0,0);

        return (chunkX == chunkPos.x && chunkZ == chunkPos.z) && generatorIn.hasStructure(biomeIn,this);
    }
    //done
    @Override
    public IStartFactory getStartFactory() {
        return ExampleStructure.Start::new;
    }
    //done
    @Override
    public String getStructureName() {
        return ExampleMod.MODID + ":example";
    }
    //done
    @Override
    public int getSize() {
        return 0;
    }
    //done
    public static class Start extends MarginedStructureStart{

        public Start(Structure<?> p_i225874_1_, int p_i225874_2_, int p_i225874_3_, MutableBoundingBox p_i225874_4_, int p_i225874_5_, long p_i225874_6_) {
            super(p_i225874_1_, p_i225874_2_, p_i225874_3_, p_i225874_4_, p_i225874_5_, p_i225874_6_);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            BlockPos pos = new BlockPos(chunkX << 4,0,chunkZ<<4);
            System.out.println("New ExampleStructure at " + pos.toString());
            ExamplePieces.addPieces(generator,templateManagerIn,pos,this.components,this.rand);
            this.recalculateStructureSize();
        }
    }
}
