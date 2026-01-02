package de.blitz.enchants.listener;

import de.blitz.enchants.util.EnchantUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class MovementListener implements Listener {

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();

        if (!EnchantUtil.hasEnchant(p.getInventory().getBoots(), "Double Jump")) return;

        e.setCancelled(true);
        p.setVelocity(p.getLocation().getDirection().multiply(0.6).setY(0.9));
        p.setAllowFlight(false);
    }

    @EventHandler
    public void onWater(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (p.isInWater() &&
                EnchantUtil.hasEnchant(p.getInventory().getChestplate(), "Healing Waters")) {

            if (p.getHealth() < p.getMaxHealth())
                p.setHealth(Math.min(p.getMaxHealth(), p.getHealth() + 0.5));
        }
    }
}
