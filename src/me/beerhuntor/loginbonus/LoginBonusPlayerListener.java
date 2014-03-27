package me.BeerHuntor.LoginBonus;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.configuration.Configuration;

public class LoginBonusPlayerListener implements Listener
{
  protected static Configuration config;
  public LoginBonus plugin;

  public LoginBonusPlayerListener(LoginBonus instance)
  {
    this.plugin = instance;
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event)
  {
    LoginBonus.loadConfiguration(config);

    Player p = event.getPlayer();
    int id = LoginBonus.config.getInt("Prize", 3);
    int x = LoginBonus.config.getInt("Reward Time", 1);
    x *= 3600000;
    ItemStack i = new ItemStack(id, 1);
    String itemid = Material.getMaterial(id).name();
    if (p.hasPermission("LoginBonus.true"))
    {
      if (this.plugin.getPlayers().containsKey(p.getName())) {
        Long time = (Long)this.plugin.getPlayers().get(p.getName());
        if (System.currentTimeMillis() - time.longValue() > x) {
          p.getInventory().addItem(new ItemStack[] { i });

          p.sendMessage(ChatColor.AQUA + "You have recieved a Login Bonus of a " + itemid);

          this.plugin.addToPlayers(p.getName(), Long.valueOf(System.currentTimeMillis()));
          return;
        }
        p.sendMessage(ChatColor.AQUA + "You have already recieved your daily login bonus for today!");
      }
      else
      {
        p.getInventory().addItem(new ItemStack[] { i });
        p.sendMessage(ChatColor.AQUA + "You have recieved a Login Bonus of a " + itemid);

        this.plugin.addToPlayers(p.getName(), Long.valueOf(System.currentTimeMillis()));
        return;
      }
    }
  }
}