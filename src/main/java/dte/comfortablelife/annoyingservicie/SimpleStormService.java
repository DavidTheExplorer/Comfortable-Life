package dte.comfortablelife.annoyingservicie;

import java.time.Duration;

import org.bukkit.Bukkit;
import org.bukkit.World;

import dte.comfortablelife.ComfortableLife;

public class SimpleStormService implements StormService
{
	private final String stormStoppedMessage;
	private final long delayInTicks;

	public SimpleStormService(String stormStoppedMessage, Duration stormStopDelay) 
	{
		this.stormStoppedMessage = stormStoppedMessage;
		this.delayInTicks = stormStopDelay.getSeconds() / 20;
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
		Bukkit.getScheduler().scheduleSyncRepeatingTask(ComfortableLife.getInstance(), this::despawnAll, 0, this.delayInTicks);
	}
	
	@Override
	public void despawnAll() 
	{
		Bukkit.getWorlds().stream()
		.filter(World::hasStorm)
		.forEach(this::stopStormAt);
	}
}