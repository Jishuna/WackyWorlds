package me.jishuna.wackyworlds.generators;

import java.util.Random;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.WorldInfo;

public class StripeChunkGenerator extends WackyGenerator {
    private int stripeSize;
    private int gapSize;
    private int gridSize;

    public StripeChunkGenerator() {
        super("stripe");
    }

    @Override
    public void reloadSettings(YamlConfiguration config) {
        int stripeSize = config.getInt("stripe-size");
        this.stripeSize = Math.max(stripeSize, 1);

        int gapSize = config.getInt("gap-size");
        this.gapSize = Math.max(gapSize, 1);

        this.gridSize = this.stripeSize + this.gapSize;
    }

    @Override
    public boolean shouldGenerateTerrain(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        chunkX = (chunkX % gridSize + gridSize) % gridSize;

        int xMod = (chunkX + gridSize) % gridSize;

        return xMod < stripeSize;
    }

    public int getStripeSize() {
        return stripeSize;
    }

    public int getGapSize() {
        return gapSize;
    }
}
