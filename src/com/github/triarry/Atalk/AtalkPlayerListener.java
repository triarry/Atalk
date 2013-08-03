package com.github.triarry.Atalk;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AtalkPlayerListener implements Listener {
	
	@SuppressWarnings("unused")
	private Atalk plugin;

    public AtalkPlayerListener(Atalk plugin) {
        this.plugin = plugin;
    }
    
	@EventHandler
	public void informAdmins(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if (Atalk.update == true && p.hasPermission("talk.update")) {
			p.sendMessage(ChatColor.GREEN + "An update is available: " + ChatColor.GOLD + Atalk.ver);
			p.sendMessage(ChatColor.GREEN + "Download it here: " + ChatColor.GOLD + "http://dev.bukkit.org/server-mods/atalk/");
		}
	}	
}