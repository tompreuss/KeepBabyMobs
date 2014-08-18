package io.github.tompreuss.keepbabymobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class KeepBabyMobs extends JavaPlugin implements Listener {
    
    // register
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }
    
    // if player names a baby mob with a name tag, age lock the baby
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();
            if (player != null && entity != null) { // player clicking isn't null and entity clicked on isn't null
                ItemStack item = player.getItemInHand(); 
                    if (item != null) { // has item in hand
                        if (item.getType() == Material.NAME_TAG) { // item is name tag
                            ItemMeta meta = item.getItemMeta();
                                if (meta.hasDisplayName()) { // name tag has a name on it
                                    if (entity instanceof Ageable) { // entity is one that has babies
                                        if (((Ageable) entity).isAdult() == false) { // entity is a baby
                                            ((Ageable) entity).setAgeLock(true); // age lock the baby
                                            
                                            player.sendMessage(ChatColor.GOLD + "That mob has now been age locked. How adorable!"); // tell the player
                                            
                                            // make sure it's logged
                                            
                                            EntityType entitytype = entity.getType();
                                            Location location = entity.getLocation();
                                            String playername = player.getName();
                                            String name = meta.getDisplayName();
                                                                                                                    
                                            String locationlog = location.getWorld().getName() + " " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ();
                                            
                                            getLogger().info(playername + " age locked " + entitytype + " named " + name + " at "  + locationlog );
                                        }
                                    }
                                }
                        }
                    }
            }
        
    } // onPlayerInteractEntityEvent
    
} // class KeepBabyMobs