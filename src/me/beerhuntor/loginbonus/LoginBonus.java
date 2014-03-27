/*
* author of this plugin is BeerHuntor.  All code is considered open source. 
*/

package me.BeerHuntor.LoginBonus;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;

public class LoginBonus extends JavaPlugin
{
  LoginBonusPlayerListener playerListener = new LoginBonusPlayerListener(this);
  private Logger log = Logger.getLogger("Minecraft");
  private Map<String, Long> players = new HashMap();
  protected static Configuration config;
  public iConomy iConomy = null;

  public void onEnable() {
    PluginManager pm = getServer().getPluginManager();

    pm.registerEvents(new LoginBonusPlayerListener, this);

    config = getConfig();
    File configs = new File(getDataFolder() + File.separator + "config.yml");
    if (!configs.exists()) {
      config.setProperty("Prize", Integer.valueOf(264));
      config.header("#Time must be in hours");
      config.setProperty("RewardTime", Integer.valueOf(24));
      config.saveConfig();
    }

    logMessage(" Has been enabled.");
  }

  public void onDisable()
  {
    logMessage(" Has been disabled.");
  }

  protected void logMessage(String msg) {
    PluginDescriptionFile pdFile = getDescription();
    this.log.info("[" + pdFile.getName() + "]" + " " + pdFile.getVersion() + ": " + msg);
  }

  public Map<String, Long> getPlayers() {
    return this.players;
  }

  public void addToPlayers(String player, Long time) {
    this.players.put(player, time);
  }
}