package me.jishuna.wackyworlds.generators;

import java.util.Random;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.WorldInfo;

public class RandomChunkGenerator extends WackyGenerator {
    private double emptyChance;

    public RandomChunkGenerator() {
        super("random");
    }

    @Override
    public void reloadSettings(YamlConfiguration config) {
        double chance = config.getDouble("empty-chance");
        this.emptyChance = Math.max(chance, 0d);
    }

    @Override
    public boolean shouldGenerateTerrain(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        return random.nextDouble() < (1 - emptyChance);
    }

    public double getEmptyChance() {
        return emptyChance;
    }
}
