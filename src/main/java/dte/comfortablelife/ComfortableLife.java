package dte.comfortablelife;

import static dte.comfortablelife.utils.ChatColorUtils.colorize;
import static org.bukkit.ChatColor.RED;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.bukkit.entity.EntityType;

import dte.comfortablelife.annoyingservicie.AnnoyanceHandler;
import dte.comfortablelife.annoyingservicie.AnnoyingEntitiesHandler;
import dte.comfortablelife.annoyingservicie.AnnoyingStormsHandler;
import dte.modernjavaplugin.ModernJavaPlugin;

public class ComfortableLife extends ModernJavaPlugin
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
						logToConsole(RED + String.format("Couldn't blacklist \"%s\" because such mob doesn't exist!", typeName));
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