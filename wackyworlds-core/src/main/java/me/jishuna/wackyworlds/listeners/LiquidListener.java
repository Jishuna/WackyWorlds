package me.jishuna.wackyworlds.listeners;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class LiquidListener implements Listener {

    @EventHandler
    public void onFlow(BlockFromToEvent event) {
        if (!event.getBlock().isLiquid()) {
            return;
        }

        Chunk from = event.getBlock().getChunk();
        Chunk to = event.getToBlock().getChunk();

        // Stop flow if the target chunk is different from the source chunk and is recently generated.
        if (to.getInhabitedTime() > 20 || to == from) {
            return;
        }
        event.setCancelled(true);
    }
}
