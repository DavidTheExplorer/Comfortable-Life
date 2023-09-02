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
	public void stopAnnoyance() 
	{
		//stop current storms
		Bukkit.getWorlds().stream()
		.filter(World::hasStorm)
		.forEach(this::stopStormAt);
	
		//prevent next storms
		Bukkit.getScheduler().scheduleSyncRepeatingTask(ComfortableLife.getInstance(), this::stopAnnoyance, 0, 20);
	}
	
	public void stopStormAt(World world) 
	{
		world.setStorm(false);

		if(this.stormStoppedMessage != null)
			world.getPlayers().forEach(player -> player.sendMessage(this.stormStoppedMessage));
	}
}