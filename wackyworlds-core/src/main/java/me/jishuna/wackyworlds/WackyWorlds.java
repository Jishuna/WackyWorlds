package me.jishuna.wackyworlds;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import me.jishuna.jishlib.FileUtils;
import me.jishuna.jishlib.Localization;
import me.jishuna.jishlib.ServerUtils;
import me.jishuna.jishlib.SimpleSemVersion;
import me.jishuna.jishlib.UpdateChecker;
import me.jishuna.wackyworlds.commands.WackyWorldCommand;
import me.jishuna.wackyworlds.generators.CheckerboardChunkGenerator;
import me.jishuna.wackyworlds.generators.GridChunkGenerator;
import me.jishuna.wackyworlds.generators.ImageChunkGenerator;
import me.jishuna.wackyworlds.generators.InverseGridGenerator;
import me.jishuna.wackyworlds.generators.RandomChunkGenerator;
import me.jishuna.wackyworlds.generators.RingChunkGenerator;
import me.jishuna.wackyworlds.generators.StripeChunkGenerator;
import me.jishuna.wackyworlds.generators.WackyGenerator;
import me.jishuna.wackyworlds.listeners.LiquidListener;
import net.md_5.bungee.api.ChatColor;

public class WackyWorlds extends JavaPlugin {
    private static final int BSTATS_ID = 18225;
    private static final int PLUGIN_ID = 109273;
    private static final String IMAGE_PATH = "images";

    private BukkitTask updateTask;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new LiquidListener(), this);

        getCommand("wackyworlds").setExecutor(new WackyWorldCommand(this));

        reload();

        initializeMetrics();
        initializeUpdateChecker();
    }

    @Override
    public void onDisable() {
        if (updateTask != null && !updateTask.isCancelled()) {
            updateTask.cancel();
        }
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
        case "IMAGE":
            return setupGenerator(worldName, new ImageChunkGenerator());
        default:
            return null;
        }
    }

    public void reload() {
        File folder = new File(this.getDataFolder(), "worlds");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File imageFolder = new File(this.getDataFolder(), IMAGE_PATH);
        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
            processDefaults(this, IMAGE_PATH, name -> FileUtils.saveResource(this, name));
        }

        FileUtils.loadResource(this, "messages.yml").ifPresent(config -> Localization.getInstance().setConfig(config));
    }

    private ChunkGenerator setupGenerator(String worldName, WackyGenerator generator) {
        YamlConfiguration config = getOrCreateConfig(this, worldName, generator);
        generator.reloadSettings(this.getDataFolder(), config);

        return generator;
    }

    private void initializeMetrics() {
        Metrics metrics = new Metrics(this, BSTATS_ID);
        metrics.addCustomChart(new SimplePie("online_status", ServerUtils::getOnlineMode));
    }

    private void initializeUpdateChecker() {
        UpdateChecker checker = new UpdateChecker(this, PLUGIN_ID);
        SimpleSemVersion current = SimpleSemVersion.fromString(this.getDescription().getVersion());

        updateTask = Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> checker.getVersion(version -> {
            if (SimpleSemVersion.fromString(version).isNewerThan(current)) {
                ConsoleCommandSender sender = Bukkit.getConsoleSender();
                sender.sendMessage(ChatColor.GOLD + "=".repeat(70));
                sender.sendMessage(
                        ChatColor.GOLD + "A new version of WackyWorlds is available: " + ChatColor.DARK_AQUA + version);
                sender.sendMessage(ChatColor.GOLD
                        + "Download it at https://www.spigotmc.org/resources/wackyworlds-terrain-generator.109273");
                sender.sendMessage(ChatColor.GOLD + "=".repeat(70));
            }
        }), 0, 20l * 60 * 60);
    }

    public static YamlConfiguration getOrCreateConfig(Plugin plugin, String worldName, WackyGenerator generator) {
        File folder = new File(plugin.getDataFolder(), "worlds");

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

    public static void processDefaults(JavaPlugin plugin, String path, Consumer<String> consumer) {
        final File jarFile = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

        if (jarFile.isFile()) {
            try (final JarFile jar = new JarFile(jarFile);) {
                final Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    final JarEntry entry = entries.nextElement();
                    if (entry.isDirectory())
                        continue;

                    final String name = entry.getName();
                    if (name.startsWith(path + "/")) {
                        consumer.accept(name);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
