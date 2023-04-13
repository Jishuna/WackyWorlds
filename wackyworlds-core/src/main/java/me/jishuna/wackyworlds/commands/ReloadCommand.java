package me.jishuna.wackyworlds.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.ChunkGenerator;

import me.jishuna.jishlib.Localization;
import me.jishuna.jishlib.commands.SimpleCommandHandler;
import me.jishuna.wackyworlds.WackyWorlds;
import me.jishuna.wackyworlds.generators.WackyGenerator;

public class ReloadCommand extends SimpleCommandHandler {
    private final WackyWorlds plugin;

    protected ReloadCommand(WackyWorlds plugin) {
        super("wackyworlds.command.reload");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        plugin.reload();
        
        for (World world : Bukkit.getWorlds()) {
            ChunkGenerator generator = world.getGenerator();

            if (!(generator instanceof WackyGenerator wackyGenerator)) {
                continue;
            }

            wackyGenerator.reloadSettings(WackyWorlds.getOrCreateConfig(plugin, world.getName(), wackyGenerator));
            wackyGenerator.sendSettings(sender, alias);
        }

        sender.sendMessage(Localization.getInstance().localize("commands.reload.success"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
