package com.github.triarry.Atalk;

import java.util.List;

import com.github.triarry.Atalk.utilities.Utilities;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AtalkCommandExecutor implements CommandExecutor {

	private Atalk plugin;
	  
	public AtalkCommandExecutor(Atalk plugin){ 
		this.plugin = plugin; 
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("Atalk") || label.equalsIgnoreCase("At") || label.equalsIgnoreCase("A")) {
			if (args.length < 1) {
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "#####");
				sender.sendMessage(ChatColor.GREEN + "Currently running Atalk version 1.3	");
				sender.sendMessage(ChatColor.GREEN + "Plugin made by triarry");
				sender.sendMessage(ChatColor.GREEN + "To send a message to all online staff, type /atalk <question>");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "#####");
				return true;
			}
			else if (sender.hasPermission("atalk.staff") && args.length >= 1 && args[0].equalsIgnoreCase("mute")) {
				if(args.length == 1) {
					sender.sendMessage(ChatColor.RED + "Please input the name of the player you would like to mute.");
					return true;
				}
				else if (plugin.getServer().getPlayer(args[1]) == null)
				{
					sender.sendMessage(ChatColor.GREEN + plugin.getServer().getPlayer(args[1]).getName() + ChatColor.RED + " is not online.");
					return true;
				}
				for (String staff : plugin.getConfig().getStringList("staff.members")) {
					if (!plugin.getServer().getPlayer(args[1]).getName().equals(staff)) {
						continue;
					}
					else {
						sender.sendMessage(ChatColor.RED + "You can't mute other staff members!");
						return true;
					}
				}
				for (String mutedPlayer : plugin.getConfig().getStringList("players.mute")) {
					if (!plugin.getServer().getPlayer(args[1]).getName().equals(mutedPlayer)) 
						continue;
					else {
						sender.sendMessage(ChatColor.GREEN + mutedPlayer + ChatColor.RED + " is already muted!");
						return true;
					}
				}
				List<String> mutedPlayers = plugin.getConfig().getStringList("players.mute");
				mutedPlayers.add(plugin.getServer().getPlayer(args[1]).getName());
				plugin.getConfig().set("players.mute", mutedPlayers);
				plugin.saveConfig();
				sender.sendMessage(ChatColor.RED + plugin.getServer().getPlayer(args[1]).getName() + " is now muted.");
				return true;
			}
			else if (sender.hasPermission("atalk.staff") && args.length >= 1 && args[0].equalsIgnoreCase("unmute")) {
				if(args.length == 1) {
					sender.sendMessage("Please input the name of the player you would like to unmute.");
					return true;
				}
				else if (plugin.getServer().getPlayer(args[1]) == null)
				{
					sender.sendMessage(ChatColor.GREEN + plugin.getServer().getPlayer(args[1]).getName() + ChatColor.RED + " is not online.");
					return true;
				}
				else {
					int i = 0;
					for (String mutedPlayer : plugin.getConfig().getStringList("players.mute")) {
						if (!plugin.getServer().getPlayer(args[1]).getName().equals(mutedPlayer)) {
							i++;
						}
						else {
							List<String> mutedPlayers = plugin.getConfig().getStringList("players.mute");
							mutedPlayers.remove(i);
							plugin.getConfig().set("players.mute", mutedPlayers);
							plugin.saveConfig();
							sender.sendMessage(ChatColor.GREEN + plugin.getServer().getPlayer(args[1]).getName() + " is no longer muted.");
							return true;
						}
					}
					sender.sendMessage(ChatColor.GREEN + plugin.getServer().getPlayer(args[1]).getName() + ChatColor.RED + " is not muted!");
					return true;
				}
			}
			else if (sender.hasPermission("atalk.staff") && args.length >= 1 && plugin.getServer().getPlayer(args[0]) != null) {
				if (Utilities.getUtilities().isMuted(sender)) {
					sender.sendMessage(ChatColor.RED + "You are muted!");
					return true;
				}
				if (args.length == 1) {
					sender.sendMessage(ChatColor.RED + "Please input a message to send to " + plugin.getServer().getPlayer(args[0]).getName() + " and the staff.");
					return true;
				}
                String msg = "";
                for (int i = 1; i < args.length; i++) {
                    msg += args[i] + " ";
                }
				for (String staff : plugin.getConfig().getStringList("staff.members")) {
					if (plugin.getServer().getPlayer(staff) != null && plugin.getServer().getPlayer(staff) != sender && plugin.getServer().getPlayer(staff) != plugin.getServer().getPlayer(args[0])) {
						Player p = plugin.getServer().getPlayer(staff);
					    Utilities.getUtilities().sendMsgStaff(sender, p, msg,plugin.getServer().getPlayer(args[0]).getName());
					}
				}
				if(plugin.getServer().getPlayer(args[0]) != sender) {
					Player p = plugin.getServer().getPlayer(args[0]);
					Utilities.getUtilities().sendMsgStaff(sender, p, msg, plugin.getServer().getPlayer(args[0]).getName());
					Utilities.getUtilities().sendMsgStaff(sender, (Player)sender, msg, plugin.getServer().getPlayer(args[0]).getName());
					return true;
				}
				else {
					Player p = plugin.getServer().getPlayer(args[0]);
					Utilities.getUtilities().sendMsgStaff(sender, p, msg, plugin.getServer().getPlayer(args[0]).getName());
					return true;
				}
			}
			else if (sender.hasPermission("atalk.send") && args.length >= 1) {
				if (Utilities.getUtilities().isMuted(sender)) {
					sender.sendMessage(ChatColor.RED + "You are muted!");
					return true;
				}
                String msg = "";
                for (int i = 0; i < args.length; i++) {
                    msg += args[i] + " ";
                }
				for (String staff : plugin.getConfig().getStringList("staff.members")) {
					if (plugin.getServer().getPlayer(staff) != null && plugin.getServer().getPlayer(staff) != sender) {
						Player p = plugin.getServer().getPlayer(staff);
						if (sender.hasPermission("atalk.staff"))
							Utilities.getUtilities().sendMsgStaff(sender, p, msg);
						else
							Utilities.getUtilities().sendMsgPlayer(sender, p, msg);
					}
				}
				Player p = plugin.getServer().getPlayer(sender.getName());
				if (sender.hasPermission("atalk.staff"))
					Utilities.getUtilities().sendMsgStaff(sender, p, msg);
				else
					Utilities.getUtilities().sendMsgPlayer(sender, p, msg);
				return true;
			}
			else {
				Utilities.getUtilities().denyInfo(sender);
				return true;
			}
    	}
		else {
			Utilities.getUtilities().denyInfo(sender);
			return true;
		}
	}
}	