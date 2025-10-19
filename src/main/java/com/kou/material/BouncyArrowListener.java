package com.kou.material;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.WeakHashMap;

public class BouncyArrowListener implements Listener {

    private final WeakHashMap<Arrow, Integer> bounceCounts = new WeakHashMap<>();

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow) || !arrow.isCritical()) {
            return;
        }
        int count = bounceCounts.getOrDefault(arrow, 0);
        if (count < 100) {
            var face = event.getHitBlockFace();
            if (face == null) return;
            var velocity = arrow.getVelocity();
            if (face.getModX() != 0) {
                velocity.setX(-velocity.getX());
            }
            if (face.getModY() != 0) {
                velocity.setY(-velocity.getY());
                }
            if (face.getModZ() != 0) {
                velocity.setZ(-velocity.getZ());
            }
            var magnitude = velocity.length();
            if (magnitude < 3.0) {
                velocity.normalize().multiply(Math.min(3.0, magnitude * 1.2));
            }
            var vx = velocity.getX();
            var vy = velocity.getY();
            var vz = velocity.getZ();
            var yaw = (float)(Math.atan2(vx, vz)*180/Math.PI);
            var pitch = (float)(Math.atan2(vy, Math.sqrt(vx*vx + vz*vz))*180/Math.PI);
            Bukkit.getScheduler().runTask(MaterialPlugin.getInstance(), () -> {
                arrow.getWorld().spawn(arrow.getLocation(), Arrow.class, newArrow -> {
                    newArrow.setRotation(yaw, pitch);
                    newArrow.setVelocity(velocity);
                    newArrow.setFireTicks(arrow.getFireTicks());
                    newArrow.setShooter(arrow.getShooter());
                    newArrow.setDamage(arrow.getDamage());
                    newArrow.setPierceLevel(arrow.getPierceLevel());
                    newArrow.setCritical(true);
                    newArrow.setPickupStatus(arrow.getPickupStatus());
                    var weapon = arrow.getWeapon();
                    if (weapon != null) {
                        newArrow.setWeapon(weapon);
                    }
                    newArrow.setItemStack(newArrow.getItemStack());
                    newArrow.setHitSound(newArrow.getHitSound());
                    newArrow.setBasePotionType(newArrow.getBasePotionType());
                    newArrow.setColor(newArrow.getColor());
                    for (var effect : arrow.getCustomEffects()) {
                        newArrow.addCustomEffect(effect, false);
                    }
                    bounceCounts.put(newArrow, count + 1);
                });
                arrow.remove();
            });
        }
    }
}
