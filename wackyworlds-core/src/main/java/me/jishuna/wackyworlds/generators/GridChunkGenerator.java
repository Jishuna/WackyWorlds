package me.jishuna.wackyworlds.generators;

import java.io.File;
import java.util.Random;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.WorldInfo;

import me.jishuna.jishlib.Localization;

public class GridChunkGenerator extends WackyGenerator {
    private int squareSize;
    private int gapSize;
    private int gridSize;

    public GridChunkGenerator() {
        super("grid");
    }

    @Override
    public void reloadSettings(File pluginFolder, YamlConfiguration config) {
        int squareSize = config.getInt("square-size");
        this.squareSize = Math.max(squareSize, 1);

        int gapSize = config.getInt("gap-size");
        this.gapSize = Math.max(gapSize, 1);

        this.gridSize = this.squareSize + this.gapSize;
    }

    @Override
    public boolean shouldGenerateTerrain(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        // Wrap around the grid if x or z is negative
        chunkX = (chunkX % gridSize + gridSize) % gridSize;
        chunkZ = (chunkZ % gridSize + gridSize) % gridSize;

        // Calculate the relative position of (x, y) within the effective grid
        int xMod = (chunkX + gridSize) % gridSize;
        int yMod = (chunkZ + gridSize) % gridSize;

        return xMod < squareSize && yMod < squareSize;
    }

    @Override
    public void sendSettings(CommandSender sender, String worldName) {
        sender.sendMessage(Localization.getInstance().localize("settings.square-size", squareSize));
        sender.sendMessage(Localization.getInstance().localize("settings.gap-size", gapSize));
    }
}
