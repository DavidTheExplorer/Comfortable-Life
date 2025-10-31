package dte.comfortablelife.annoyancestopper;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class StormStopper implements AnnoyanceStopper
{
	private final JavaPlugin plugin;

	public StormStopper(JavaPlugin plugin)
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