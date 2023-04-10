package me.jishuna.wackyworlds.generators;

import java.util.Random;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.WorldInfo;

public class CheckerboardChunkGenerator extends WackyGenerator {
    private int squareSize;
    private int doubleSquareSize;

    public CheckerboardChunkGenerator() {
        super("checkerboard");
    }

    @Override
    public void reloadSettings(YamlConfiguration config) {
        int size = config.getInt("square-size");
        this.squareSize = Math.max(size, 1);

        this.doubleSquareSize = squareSize * 2;
    }

    @Override
    public boolean shouldGenerateTerrain(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        // Wrap around the checkerboard if x or z is negative
        chunkX = (chunkX % doubleSquareSize + doubleSquareSize) % doubleSquareSize;
        chunkZ = (chunkZ % doubleSquareSize + doubleSquareSize) % doubleSquareSize;

        // Calculate the row and column indices of the square on the checkerboard
        int row = chunkZ / squareSize;
        int col = chunkX / squareSize;

        // Determine if the square should be filled or empty based on row and column
        // indices
        return (row + col) % 2 == 0;
    }

    public int getSquareSize() {
        return squareSize;
    }
}
