package dte.comfortablelife;

import static dte.comfortablelife.utils.ChatColorUtils.colorize;
import static org.bukkit.entity.EntityType.LLAMA;
import static org.bukkit.entity.EntityType.PHANTOM;
import static org.bukkit.entity.EntityType.WANDERING_TRADER;

import java.time.Duration;
import java.util.Arrays;

import org.bukkit.plugin.java.JavaPlugin;

import dte.comfortablelife.annoyingservicie.AnnoyingService;
import dte.comfortablelife.annoyingservicie.EntityService;
import dte.comfortablelife.annoyingservicie.SimpleEntityService;
import dte.comfortablelife.annoyingservicie.SimpleStormService;
import dte.comfortablelife.annoyingservicie.StormService;

public class ComfortableLife extends JavaPlugin
{
	private static ComfortableLife INSTANCE;

	@Override
	public void onEnable()
	{
		INSTANCE = this;

		saveDefaultConfig();

		for(AnnoyingService service : Arrays.asList(createEntityService(), newStormService())) 
		{
			service.despawnAll();
			service.preventNextSpawns();
		}
	}

	public static ComfortableLife getInstance() 
	{
		return INSTANCE;
	}
	
	private EntityService createEntityService() 
	{
		return new SimpleEntityService(WANDERING_TRADER, LLAMA, PHANTOM);
	}
	
	private StormService newStormService() 
	{
		String stormStoppedMessage = colorize(getConfig().getString("Services.Storm.Stopped Message"));
		Duration stormStopDelay = Duration.ofSeconds(getConfig().getInt("Services.Storm.Stop Delay in seconds"));

		return new SimpleStormService(stormStoppedMessage, stormStopDelay);
	}
}