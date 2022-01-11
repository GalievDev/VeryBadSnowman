package com.valion.very_bad_snowman.world.gen;

import com.valion.very_bad_snowman.entity.RegEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Arrays;
import java.util.List;

public class VBSEntityGeneration {
    public static void onEntitySpawn(final BiomeLoadingEvent event) {
        addEntityToAllBiomesExceptThese(event, RegEntity.BAD_SNOWMAN.get(), 50, 1, 3,
                Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.ICE_SPIKES, Biomes.SNOWY_TUNDRA);
    }

    @SafeVarargs
    private static void addEntityToAllBiomesExceptThese(BiomeLoadingEvent event, EntityType<?> type,
                                                        int weight, int minCount, int maxCount, RegistryKey<Biome>... biomes) {
        boolean isBiomeSelected = Arrays.stream(biomes).map(RegistryKey::getLocation)
                .map(Object::toString).anyMatch(s -> s.equals(event.getName().toString()));

        if (!isBiomeSelected) {
            addEntityToAllBiomes(event.getSpawns(), type, weight, minCount, maxCount);
        }
    }

    private static void addEntityToSpecificBiomes(BiomeLoadingEvent event, EntityType<?> type,
                                                  int weight, int minCount, int maxCount, RegistryKey<Biome>... biomes) {
        boolean isBiomeSelected = Arrays.stream(biomes).map(RegistryKey::getLocation)
                .map(Object::toString).anyMatch(s -> s.equals(event.getName().toString()));

        if (isBiomeSelected) {
            addEntityToAllBiomes(event.getSpawns(), type, weight, minCount, maxCount);
        }
    }

    private static void addEntityToAllBiomes(MobSpawnInfoBuilder spawns, EntityType<?> type,
                                             int weight, int minCount, int maxCount) {
        List<MobSpawnInfo.Spawners> base = spawns.getSpawner(type.getClassification());
        base.add(new MobSpawnInfo.Spawners(type, weight, minCount, maxCount));
    }
}