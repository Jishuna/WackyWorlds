package me.jishuna.wackyworlds.generators;

import java.util.Random;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

public abstract class WackyGenerator extends ChunkGenerator {
    private final String name;

    protected WackyGenerator(String name) {
        this.name = name;
    }

    public abstract void reloadSettings(YamlConfiguration config);

    public abstract boolean shouldGenerateTerrain(WorldInfo worldInfo, Random random, int chunkX, int chunkZ);

    public abstract void sendSettings(CommandSender sender, String worldName);
    public String getName() {
        return name;
    }

    @Override
    public boolean shouldGenerateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        return shouldGenerateTerrain(worldInfo, random, chunkX, chunkZ);
    }

    @Override
    public boolean shouldGenerateSurface(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        return shouldGenerateTerrain(worldInfo, random, chunkX, chunkZ);
    }

    @Override
    public boolean shouldGenerateCaves(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        return shouldGenerateTerrain(worldInfo, random, chunkX, chunkZ);
    }

    @Override
    public boolean shouldGenerateDecorations(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        return shouldGenerateTerrain(worldInfo, random, chunkX, chunkZ);
    }

    @Override
    public boolean shouldGenerateMobs(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        return shouldGenerateTerrain(worldInfo, random, chunkX, chunkZ);
    }

    @Override
    public boolean shouldGenerateStructures(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        return shouldGenerateTerrain(worldInfo, random, chunkX, chunkZ);
    }
}
