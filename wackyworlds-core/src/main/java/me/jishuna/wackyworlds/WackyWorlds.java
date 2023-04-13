package me.jishuna.wackyworlds;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.jishlib.FileUtils;
import me.jishuna.jishlib.Localization;
import me.jishuna.wackyworlds.commands.WackyWorldCommand;
import me.jishuna.wackyworlds.generators.CheckerboardChunkGenerator;
import me.jishuna.wackyworlds.generators.GridChunkGenerator;
import me.jishuna.wackyworlds.generators.InverseGridGenerator;
import me.jishuna.wackyworlds.generators.RandomChunkGenerator;
import me.jishuna.wackyworlds.generators.RingChunkGenerator;
import me.jishuna.wackyworlds.generators.StripeChunkGenerator;
import me.jishuna.wackyworlds.generators.WackyGenerator;
import me.jishuna.wackyworlds.listeners.LiquidListener;

public class WackyWorlds extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new LiquidListener(), this);

        getCommand("wackyworlds").setExecutor(new WackyWorldCommand(this));

        reload();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        if (id == null) {
            return null;
        }

        switch (id.toUpperCase()) {
        case "CHECKERBOARD":
            return setupGenerator(worldName, new CheckerboardChunkGenerator());
        case "GRID":
            return setupGenerator(worldName, new GridChunkGenerator());
        case "INVERSEGRID":
            return setupGenerator(worldName, new InverseGridGenerator());
        case "RANDOM":
            return setupGenerator(worldName, new RandomChunkGenerator());
        case "STRIPE":
            return setupGenerator(worldName, new StripeChunkGenerator());
        case "RING":
            return setupGenerator(worldName, new RingChunkGenerator());
        default:
            return null;
        }
    }

    public void reload() {
        FileUtils.loadResource(this, "messages.yml").ifPresent(config -> Localization.getInstance().setConfig(config));
    }

    private ChunkGenerator setupGenerator(String worldName, WackyGenerator generator) {
        YamlConfiguration config = getOrCreateConfig(this, worldName, generator);
        generator.reloadSettings(config);

        return generator;
    }

    public static YamlConfiguration getOrCreateConfig(Plugin plugin, String worldName, WackyGenerator generator) {
        File folder = new File(plugin.getDataFolder(), "worlds");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File target = new File(folder, worldName + ".yml");
        if (!target.exists()) {
            try {
                target.createNewFile();
                Utils.copyFile(plugin.getResource("templates/" + generator.getName() + ".yml"), target);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return YamlConfiguration.loadConfiguration(target);
    }
}
