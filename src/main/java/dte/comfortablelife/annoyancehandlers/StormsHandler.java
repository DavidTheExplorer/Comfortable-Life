package dte.comfortablelife.annoyancehandlers;

import dte.modernjavaplugin.ModernJavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class StormsHandler implements AnnoyanceHandler
{
	private final String globalMessage;
	private final ModernJavaPlugin plugin;

	public StormsHandler(String globalMessage, ModernJavaPlugin plugin)
	{
		this.globalMessage = globalMessage;
		this.plugin = plugin;
	}

	@Override
	public void stop()
	{
		stopCurrentStorms();
		preventFutureStorms();
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

	private void preventFutureStorms()
	{
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void preventStorm(WeatherChangeEvent event)
			{
				//the new weather has to be storm
				if(!event.toWeatherState())
					return;

				event.setCancelled(true);
				notifyPlayers(event.getWorld());
			}
		}, this.plugin);
	}
	
	private void notifyPlayers(World world) 
	{
		if(this.globalMessage == null)
			return;
		
		for(Player player : world.getPlayers())
			player.sendMessage(this.globalMessage);
	}
}