package me.jishuna.wackyworlds.generators;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.WorldInfo;

import me.jishuna.jishlib.Localization;

public class ImageChunkGenerator extends WackyGenerator {
    private GeneratorData data;
    private String fileName;

    public ImageChunkGenerator() {
        super("image");
    }

    @Override
    public void reloadSettings(File pluginFolder, YamlConfiguration config) {
        String imagePath = config.getString("image");
        if (imagePath == null) {
            return;
        }

        File file = new File(pluginFolder, imagePath);
        this.fileName = file.getName();

        BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        this.data = new GeneratorData(image);
    }

    @Override
    public boolean shouldGenerateTerrain(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        chunkX = (chunkX % data.getWidth() + data.getWidth()) % data.getWidth();
        chunkZ = (chunkZ % data.getHeight() + data.getHeight()) % data.getHeight();

        return data.isFilled(chunkX, chunkZ);
    }

    @Override
    public void sendSettings(CommandSender sender, String worldName) {
        sender.sendMessage(Localization.getInstance().localize("settings.width", data.getWidth()));
        sender.sendMessage(Localization.getInstance().localize("settings.height", data.getHeight()));
        sender.sendMessage(Localization.getInstance().localize("settings.file-name", this.fileName));
    }
}
