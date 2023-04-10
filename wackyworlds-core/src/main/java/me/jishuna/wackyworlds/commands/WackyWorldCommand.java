package me.jishuna.wackyworlds.commands;

import me.jishuna.jishlib.ComponentLocalization;
import me.jishuna.jishlib.commands.ArgumentCommandHandler;
import me.jishuna.wackyworlds.WackyWorlds;

public class WackyWorldCommand extends ArgumentCommandHandler {

    public WackyWorldCommand(WackyWorlds plugin) {
        super("wackyworlds.command", () -> ComponentLocalization.getInstance().localize("commands.no-permission"),
                () -> ComponentLocalization.getInstance().localize("commands.usage"));

        addArgumentExecutor("reload", new ReloadCommand(plugin));
        addArgumentExecutor("worldinfo", new InfoCommand());
    }

}
