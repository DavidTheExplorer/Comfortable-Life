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
		Bukkit.getScheduler().scheduleSyncRepeatingTask(ComfortableLife.getInstance(), this::stopAllStorms, 0, 20);
	}

	private void stopAllStorms()
	{
		Bukkit.getWorlds().stream()
		.filter(World::hasStorm)
		.forEach(this::stopStormAt);
	}
	
	public void stopStormAt(World world) 
	{
		//stop the storm
		world.setStorm(false);

		//notify the world's players
		if(this.stormStoppedMessage != null)
			world.getPlayers().forEach(player -> player.sendMessage(this.stormStoppedMessage));
	}
}