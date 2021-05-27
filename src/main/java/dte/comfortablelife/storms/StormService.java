package dte.comfortablelife.storms;

import org.bukkit.Bukkit;
import org.bukkit.World;

public interface StormService
{
	void stopStormAt(World world);
	
	default void stopAllStorms() 
	{
		Bukkit.getWorlds().stream()
		.filter(World::hasStorm)
		.forEach(this::stopStormAt);
	}
}
