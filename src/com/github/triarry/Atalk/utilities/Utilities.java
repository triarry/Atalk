package com.github.triarry.Atalk.utilities;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.triarry.Atalk.Atalk;

public class Utilities {
	
	private Atalk plugin;
	
	static Utilities instance = new Utilities();
	
    public static Utilities getUtilities() {
        return instance;
    }
  
    public void startUp(Atalk plug) {
        plugin = plug;
    }
    
    public void denyInfo(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Sorry, you do not have permission to view this resource.");
        plugin.getLogger().info(sender.getName() + " requested information about Atalk and was denied.");
    }
    
    public void sendMsgStaff(CommandSender sender, Player p, String msg)  {
    	p.sendMessage(ChatColor.YELLOW + "[Atalk] " + ChatColor.RED + "Staff " + sender.getName() + ": " + ChatColor.GREEN + msg);
    }
    
    public void sendMsgStaff(CommandSender sender, Player p, String msg, String receiver)  {
    	p.sendMessage(ChatColor.YELLOW + "[Atalk] " + ChatColor.GREEN + "(@" + receiver + ") " + ChatColor.RED + "Staff " + sender.getName() + ": " + ChatColor.GREEN + msg);
    }
    
    public void sendMsgPlayer(CommandSender sender, Player p, String msg)  {
    	p.sendMessage(ChatColor.YELLOW + "[Atalk] " + ChatColor.AQUA + "Player " + sender.getName() + ": " + ChatColor.GREEN + msg);
    }
    
    public boolean isMuted(CommandSender sender) {
		for (String mutedPlayer : plugin.getConfig().getStringList("players.mute")) {
			if (!sender.getName().equals(mutedPlayer))
				continue;
			else 
				return true;
		}
		return false;
    }
}