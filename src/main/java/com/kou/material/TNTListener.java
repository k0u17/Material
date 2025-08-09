package com.kou.material;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class TNTListener implements Listener {

    @EventHandler
    public void onPlaceTNT(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() != Material.TNT) {
            return;
        }
        event.getBlockPlaced().setType(Material.AIR);
        Location location = event.getBlock().getLocation();
        location = location.add(0.5, 0.5, 0.5);
        location.getWorld().spawn(location, TNTPrimed.class, tnt -> {
            tnt.setSource(event.getPlayer());
            tnt.setFuseTicks(80);
            tnt.setYield(10.0F);
            tnt.setIsIncendiary(true);
        });
    }
}
