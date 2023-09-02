package dte.comfortablelife.annoyingservicie;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import dte.comfortablelife.ComfortableLife;

public class AnnoyingStormsHandler implements AnnoyanceHandler, Listener
{
	private String globalMessage;

	public AnnoyingStormsHandler(String globalMessage) 
	{
		this.globalMessage = globalMessage;
	}

	@Override
	public void stopAnnoyance()
	{
		stopCurrentStorms();

		//prevent future storms
		Bukkit.getPluginManager().registerEvents(this, ComfortableLife.getInstance());
	}

	@EventHandler
	public void stopFutureRains(WeatherChangeEvent event) 
	{
		if(!event.toWeatherState()) 
			return;

		event.setCancelled(true);
		
		notifyPlayers(event.getWorld());
	}
	
	private void stopCurrentStorms() 
	{
		Bukkit.getWorlds().stream()
		.filter(World::hasStorm)
		.forEach(world -> 
		{
			world.setStorm(false);
			
			notifyPlayers(world);
		});
	}
	
	private void notifyPlayers(World world) 
	{
		if(this.globalMessage != null)
			world.getPlayers().forEach(player -> player.sendMessage(this.globalMessage));
	}
}