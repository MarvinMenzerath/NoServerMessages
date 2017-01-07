package pro.marvin.minecraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NoServerMessages extends JavaPlugin implements Listener {
	private boolean active = true;

	/**
	 * Register the 'onCommand'-Event and load the config (--> if enabled or not)
	 */
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		loadConfig();
	}

	/**
	 * Enables or disables the plugin's functionality if the issuer is an operator or has the correct permission
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("togglemessages")) {
			if (sender.hasPermission("servermessages.toggle")) {
				if (active) {
					active = false;
					getConfig().set("active", false);
					sender.sendMessage("§7[NoServerMessages] §6You will now see every Server-Message...");
				} else {
					active = true;
					getConfig().set("active", true);
					sender.sendMessage("§7[NoServerMessages] §6Every Server-Message will be hidden from now on.");
				}
				saveConfig();
			} else {
				sender.sendMessage("§7[NoServerMessages] §4You do not have Permissions to use this command.");
			}
			return true;
		}
		return false;
	}

	/**
	 * If plugin is active: Clear the PlayerJoin-Message
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (active) {
			e.setJoinMessage("");
		}
	}

	/**
	 * If plugin is active: Clear the PlayerQuit-Message
	 */
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (active) {
			e.setQuitMessage("");
		}
	}

	/**
	 * If plugin is active: Clear the PlayerDeath-Message
	 */
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (active) {
			e.setDeathMessage("");
		}
	}

	/**
	 * Loads the setting from the config.yml or creates this file if it is not present
	 */
	private void loadConfig() {
		this.saveDefaultConfig();
		this.reloadConfig();
		active = this.getConfig().getBoolean("active");
	}
}