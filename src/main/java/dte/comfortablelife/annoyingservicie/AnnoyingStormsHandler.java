package dte.comfortablelife.annoyingservicie;

import org.bukkit.Bukkit;
import org.bukkit.World;

import dte.comfortablelife.ComfortableLife;

public class AnnoyingStormsHandler implements AnnoyanceHandler
{
	private final String stormStoppedMessage;

	public AnnoyingStormsHandler(String stormStoppedMessage) 
	{
		this.stormStoppedMessage = stormStoppedMessage;
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
		Bukkit.getScheduler().scheduleSyncRepeatingTask(ComfortableLife.getInstance(), this::stop, 0, 20);
	}
	
	public void stopStormAt(World world) 
	{
		world.setStorm(false);

		if(this.stormStoppedMessage != null)
			world.getPlayers().forEach(player -> player.sendMessage(this.stormStoppedMessage));
	}
}