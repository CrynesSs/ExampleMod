package com.example.examplemod.Init;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Structures.ExampleStructure;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FeatureInit {
    //Init the DeferredRegister
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ExampleMod.MODID);
    //Register the Feature
    public static final RegistryObject<Structure<NoFeatureConfig>> EXAMPLE = FEATURES.register("example",()->new ExampleStructure(NoFeatureConfig::deserialize));
    //Add the Feature to all Biomes (If you want to only add it to specific biomes, here is the place to do so.
    public static void addToBiomes(){
        System.out.println("Feature added to Biomes");
        for(Biome biome : ForgeRegistries.BIOMES){
            biome.addStructure(FeatureInit.EXAMPLE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
            biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES,
                    FeatureInit.EXAMPLE.get()
                            .withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                            .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

        }
    }
}
