package com.kou.material;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class FireballListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Player player = event.getPlayer();
        if (player.isSneaking() || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        ItemStack item = event.getItem();
        if (item != null && item.getType() == Material.FIRE_CHARGE) {
            player.launchProjectile(Fireball.class, null, fireball -> fireball.setYield(4.0F));
            EquipmentSlot hand = event.getHand();
            if (hand != null) {
                player.swingHand(hand);
            }
            if (player.getGameMode() != GameMode.CREATIVE) {
                item.setAmount(item.getAmount() - 1);
            }
            event.setCancelled(true);
        }
    }
}
