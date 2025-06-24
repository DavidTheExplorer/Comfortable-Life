package dte.comfortablelife.annoyancehandlers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import dte.comfortablelife.ComfortableLife;

public class StormsHandler implements AnnoyanceHandler, Listener
{
	private String globalMessage;

	public StormsHandler(String globalMessage)
	{
		this.globalMessage = globalMessage;
	}

	@Override
	public void stop()
	{
		stopCurrentStorms();

		//prevent future storms
		Bukkit.getPluginManager().registerEvents(this, ComfortableLife.getInstance());
	}

	@EventHandler
	public void stopFutureRains(WeatherChangeEvent event) 
	{
		//the new weather has to be storm
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
		if(this.globalMessage == null)
			return;
		
		for(Player player : world.getPlayers())
			player.sendMessage(this.globalMessage);
	}
}