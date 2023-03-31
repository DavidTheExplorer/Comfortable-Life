package dte.comfortablelife;

import static dte.comfortablelife.utils.ChatColorUtils.colorize;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

import org.bukkit.entity.EntityType;
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

		for(AnnoyingService service : Arrays.asList(parseEntityService(), parseStormService())) 
		{
			service.despawnAll();
			service.preventNextSpawns();
		}
	}

	public static ComfortableLife getInstance() 
	{
		return INSTANCE;
	}
	
	private EntityService parseEntityService() 
	{
		EntityType[] treatedTypes = getConfig().getStringList("services.annoying-entity.treated-types").stream()
				.map(typeName -> 
				{
					try 
					{
						return EntityType.valueOf(typeName.toUpperCase().replace(' ', '_'));
					}
					catch(IllegalArgumentException exception) 
					{
						return null;
					}
				})
				.filter(Objects::nonNull)
				.toArray(EntityType[]::new);

		return new SimpleEntityService(treatedTypes);
	}
	
	private StormService parseStormService() 
	{
		String stormStoppedMessage = colorize(getConfig().getString("services.storm.stopped-message"));
		Duration stormStopDelay = Duration.ofSeconds(getConfig().getInt("services.storm.stop-delay-in-seconds"));

		return new SimpleStormService(stormStoppedMessage, stormStopDelay);
	}
}