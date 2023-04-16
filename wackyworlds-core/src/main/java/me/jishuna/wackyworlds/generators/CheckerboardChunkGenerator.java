package me.jishuna.wackyworlds.generators;

import java.io.File;
import java.util.Random;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.WorldInfo;

import me.jishuna.jishlib.Localization;

public class CheckerboardChunkGenerator extends WackyGenerator {
    private int squareSize;
    private int doubleSquareSize;

    public CheckerboardChunkGenerator() {
        super("checkerboard");
    }

    @Override
    public void reloadSettings(File pluginFolder, YamlConfiguration config) {
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

    @Override
    public void sendSettings(CommandSender sender, String worldName) {
        sender.sendMessage(Localization.getInstance().localize("settings.square-size", squareSize));
    }
}
