package me.jishuna.wackyworlds;

import org.bukkit.command.CommandSender;

import me.jishuna.jishlib.Localization;
import me.jishuna.wackyworlds.generators.CheckerboardChunkGenerator;
import me.jishuna.wackyworlds.generators.GridChunkGenerator;
import me.jishuna.wackyworlds.generators.RandomChunkGenerator;
import me.jishuna.wackyworlds.generators.StripeChunkGenerator;
import me.jishuna.wackyworlds.generators.WackyGenerator;

public class Utils {
    public static void showSettings(CommandSender sender, WackyGenerator generator, String worldName) {
        sender.sendMessage(Localization.getInstance().localize("settings.world-settings", worldName));
        sender.sendMessage(Localization.getInstance().localize("settings.generator-type", generator.getName()));

        if (generator instanceof RandomChunkGenerator randomGenerator) {
            sender.sendMessage(Localization.getInstance().localize("settings.empty-chance",
                    randomGenerator.getEmptyChance(), (randomGenerator.getEmptyChance() * 100d)));

        } else if (generator instanceof CheckerboardChunkGenerator checkerGenerator) {
            sender.sendMessage(
                    Localization.getInstance().localize("settings.square-size", checkerGenerator.getSquareSize()));

        } else if (generator instanceof GridChunkGenerator gridGenerator) {
            sender.sendMessage(
                    Localization.getInstance().localize("settings.square-size", gridGenerator.getSquareSize()));
            sender.sendMessage(Localization.getInstance().localize("settings.gap-size", gridGenerator.getGapSize()));

        } else if (generator instanceof StripeChunkGenerator stripeGenerator) {
            sender.sendMessage(
                    Localization.getInstance().localize("settings.stripe-size", stripeGenerator.getStripeSize()));
            sender.sendMessage(Localization.getInstance().localize("settings.gap-size", stripeGenerator.getGapSize()));
        }
    }

}
