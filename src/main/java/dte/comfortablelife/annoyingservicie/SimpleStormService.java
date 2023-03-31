package dte.comfortablelife.annoyingservicie;

import org.bukkit.Bukkit;
import org.bukkit.World;

import dte.comfortablelife.ComfortableLife;

public class SimpleStormService implements StormService
{
	private final String stormStoppedMessage;
	private final int stormStopDelay;

	public SimpleStormService(String stormStoppedMessage, int stormStopDelay) 
	{
		this.stormStoppedMessage = stormStoppedMessage;
		this.stormStopDelay = stormStopDelay;
	}

	@Override
	public void stopStormAt(World world) 
	{
		world.setStorm(false);

		if(this.stormStoppedMessage != null)
			world.getPlayers().forEach(player -> player.sendMessage(this.stormStoppedMessage));
	}

	@Override
	public void preventNextSpawns() 
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(ComfortableLife.getInstance(), this::despawnAll, 0, this.stormStopDelay / 20);
	}
	
	@Override
	public void despawnAll() 
	{
		Bukkit.getWorlds().stream()
		.filter(World::hasStorm)
		.forEach(this::stopStormAt);
	}
}