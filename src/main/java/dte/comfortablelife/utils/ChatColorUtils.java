package dte.comfortablelife.utils;

import org.bukkit.ChatColor;

public class ChatColorUtils 
{
	//Container of static methods
	private ChatColorUtils(){}
	
	public static String colorize(String text) 
	{
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}