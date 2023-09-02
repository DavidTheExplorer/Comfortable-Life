package dte.comfortablelife;

import static dte.comfortablelife.utils.ChatColorUtils.colorize;
import static org.bukkit.ChatColor.RED;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import dte.comfortablelife.annoyingservicie.AnnoyanceHandler;
import dte.comfortablelife.annoyingservicie.AnnoyingEntitiesHandler;
import dte.comfortablelife.annoyingservicie.AnnoyingStormsHandler;

public class ComfortableLife extends JavaPlugin
{
	private static ComfortableLife INSTANCE;

	@Override
	public void onEnable()
	{
		INSTANCE = this;

		parseConfigHandlers().forEach(AnnoyanceHandler::stopAnnoyance);
	}

	public static ComfortableLife getInstance() 
	{
		return INSTANCE;
	}
	
	private List<AnnoyanceHandler> parseConfigHandlers()
	{
		saveDefaultConfig();
		
		return Arrays.asList(parseEntitiesHandler(), parseStormsHandler());
	}
	
	private AnnoyanceHandler parseEntitiesHandler() 
	{
		EntityType[] blacklistedEntities = getConfig().getStringList("handlers.entities.blacklist").stream()
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

		return new AnnoyingEntitiesHandler(blacklistedEntities);
	}
	
	private AnnoyanceHandler parseStormsHandler() 
	{
		String stormStoppedMessage = colorize(getConfig().getString("handlers.storms.stopped-message"));

		return new AnnoyingStormsHandler(stormStoppedMessage);
	}
}