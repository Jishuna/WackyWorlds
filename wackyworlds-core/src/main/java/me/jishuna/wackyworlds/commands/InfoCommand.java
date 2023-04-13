package me.jishuna.wackyworlds.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jishuna.jishlib.Localization;
import me.jishuna.jishlib.commands.SimpleCommandHandler;
import me.jishuna.wackyworlds.generators.WackyGenerator;

public class InfoCommand extends SimpleCommandHandler {

    protected InfoCommand() {
        super("wackyworlds.command.worldinfo");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Localization.getInstance().localize("commands.player-only"));
            return true;
        }
        
        World world = player.getWorld();
        if (world.getGenerator() == null) {
            sender.sendMessage(Localization.getInstance().localize("commands.invalid-world"));
            return true;
        }
        
        if (world.getGenerator() instanceof WackyGenerator generator) {
            generator.sendSettings(sender, world.getName());
            return true;
        }
        sender.sendMessage(Localization.getInstance().localize("commands.invalid-world"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
