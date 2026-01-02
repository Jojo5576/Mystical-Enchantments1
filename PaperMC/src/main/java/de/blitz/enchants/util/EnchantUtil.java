package de.blitz.enchants.util;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantUtil {

    public static boolean hasEnchant(ItemStack item, String name) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) return false;

        return meta.getLore().stream()
                .anyMatch(l -> ChatColor.stripColor(l).contains(name));
    }
}
