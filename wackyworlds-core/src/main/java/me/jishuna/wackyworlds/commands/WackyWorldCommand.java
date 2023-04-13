package me.jishuna.wackyworlds.commands;

import me.jishuna.jishlib.Localization;
import me.jishuna.jishlib.commands.ArgumentCommandHandler;
import me.jishuna.wackyworlds.WackyWorlds;

public class WackyWorldCommand extends ArgumentCommandHandler {

    public WackyWorldCommand(WackyWorlds plugin) {
        super("wackyworlds.command", () -> Localization.getInstance().localize("commands.no-permission"),
                () -> Localization.getInstance().localize("commands.usage"));

        addArgumentExecutor("reload", new ReloadCommand(plugin));
        addArgumentExecutor("worldinfo", new InfoCommand());
    }

}
