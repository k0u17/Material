package com.kou.material;

import org.bukkit.Bukkit;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class TridentListener implements Listener {

    private final Set<Trident> HIT_TRIDENTS = Collections.newSetFromMap(new WeakHashMap<>());

    @EventHandler
    public void onTridentHit(ProjectileHitEvent event) {
        var entity = event.getEntity();
        if (!(entity instanceof Trident) || !HIT_TRIDENTS.add((Trident) entity)) {
            return;
        }
        Bukkit.getScheduler().runTask(MaterialPlugin.getInstance(), () -> {
            var world = entity.getWorld();
            world.strikeLightning(entity.getLocation());
            world.createExplosion(entity, 5.0F, true);
        });
    }
}
