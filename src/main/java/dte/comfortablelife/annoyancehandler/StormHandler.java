package dte.comfortablelife.annoyancehandler;

import dte.modernjavaplugin.ModernJavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class StormHandler implements AnnoyanceHandler
{
	private final ModernJavaPlugin plugin;

	public StormHandler(ModernJavaPlugin plugin)
	{
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
                .forEach(world -> world.setStorm(false));
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
			}
		}, this.plugin);
	}
}