package de.blitz.enchants.listener;

import de.blitz.enchants.util.EnchantUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.UUID;

public class CombatListener implements Listener {

    private final HashMap<UUID, Integer> hitCounter = new HashMap<>();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player p)) return;
        if (!(e.getEntity() instanceof LivingEntity)) return;

        if (EnchantUtil.hasEnchant(p.getInventory().getItemInMainHand(), "Lifesteal")) {
            hitCounter.put(p.getUniqueId(),
                    hitCounter.getOrDefault(p.getUniqueId(), 0) + 1);

            if (hitCounter.get(p.getUniqueId()) >= 5) {
                p.setHealth(Math.min(p.getMaxHealth(), p.getHealth() + 1));
                hitCounter.put(p.getUniqueId(), 0);
            }
        }
    }
}
