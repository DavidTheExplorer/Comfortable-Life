package dte.comfortablelife.annoyingservicie;

import java.time.Duration;

import org.bukkit.Bukkit;
import org.bukkit.World;

import dte.comfortablelife.ComfortableLife;

public class AnnoyingStormsHandler implements AnnoyanceHandler
{
	private final String stormStoppedMessage;
	private final long delayInTicks;

	public AnnoyingStormsHandler(String stormStoppedMessage, Duration stormStopDelay) 
	{
		this.stormStoppedMessage = stormStoppedMessage;
		this.delayInTicks = stormStopDelay.getSeconds() / 20;
	}

	@Override
	public void stop() 
	{
		Bukkit.getWorlds().stream()
		.filter(World::hasStorm)
		.forEach(this::stopStormAt);
	}
	
	@Override
	public void stopFutureAnnoyance() 
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(ComfortableLife.getInstance(), this::stop, 0, this.delayInTicks);
	}
	
	public void stopStormAt(World world) 
	{
		world.setStorm(false);

		if(this.stormStoppedMessage != null)
			world.getPlayers().forEach(player -> player.sendMessage(this.stormStoppedMessage));
	}
}