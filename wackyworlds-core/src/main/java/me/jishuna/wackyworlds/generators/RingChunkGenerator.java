package me.jishuna.wackyworlds.generators;

import java.util.Random;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.WorldInfo;
import org.joml.Math;

import me.jishuna.jishlib.Localization;

public class RingChunkGenerator extends WackyGenerator {
    private int filledSize;
    private int gapSize;
    private int ringSize;

    public RingChunkGenerator() {
        super("ring");
    }

    @Override
    public void reloadSettings(YamlConfiguration config) {
        int filledSize = config.getInt("ring-size");
        this.filledSize = Math.max(filledSize, 1);

        int gapSize = config.getInt("gap-size");
        this.gapSize = Math.max(gapSize, 1);

        this.ringSize = this.filledSize + this.gapSize;
    }

    @Override
    public boolean shouldGenerateTerrain(WorldInfo worldInfo, Random random, int x, int y) {
        // Determine the ring number based on x and y
        int ringNumber = Math.max(Math.abs(x) / (ringSize), Math.abs(y) / (ringSize));

        // Calculate the coordinates of the corners of the ring
        int left = -ringNumber * (ringSize) - filledSize / 2;
        int right = ringNumber * (ringSize) + filledSize / 2;

        if (filledSize > gapSize) {
            right += 1;
            left -= 1;
        }

        // Check if the given position falls within the ring
        return x >= left && x <= right && y >= left && y <= right;
    }

    @Override
    public void sendSettings(CommandSender sender, String worldName) {
        sender.sendMessage(Localization.getInstance().localize("settings.ring-size", filledSize));
        sender.sendMessage(Localization.getInstance().localize("settings.gap-size", gapSize));
    }
}
