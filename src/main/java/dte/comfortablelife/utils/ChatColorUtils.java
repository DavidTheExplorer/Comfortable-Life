package dte.comfortablelife.utils;

import org.bukkit.ChatColor;

public class ChatColorUtils 
{
	public static String colorize(String text) 
	{
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}