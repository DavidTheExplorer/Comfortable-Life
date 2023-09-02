package dte.comfortablelife.annoyingservicie.factory;

import static dte.comfortablelife.utils.ChatColorUtils.colorize;
import static org.bukkit.ChatColor.RED;

import java.util.Objects;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import dte.comfortablelife.ComfortableLife;
import dte.comfortablelife.annoyingservicie.AnnoyanceHandler;
import dte.comfortablelife.annoyingservicie.AnnoyingEntitiesHandler;
import dte.comfortablelife.annoyingservicie.AnnoyingStormsHandler;

public class AnnoyanceHandlerFactory 
{
	private final Configuration config;

	public AnnoyanceHandlerFactory(Configuration config) 
	{
		this.config = config;
	}
	
	public AnnoyanceHandler parseEntitiesHandler() 
	{
		ConfigurationSection section = this.config.getConfigurationSection("handlers.entities");
		
		//return null if the handler is disabled
		if(!section.getBoolean("active"))
			return null;

		EntityType[] blacklistedEntities = section.getStringList("blacklist").stream()
				.map(typeName -> 
				{
					try 
					{
						return EntityType.valueOf(typeName.toUpperCase().replace(' ', '_'));
					}
					catch(IllegalArgumentException exception)
					{
						ComfortableLife.getInstance().logToConsole(RED + String.format("Couldn't blacklist \"%s\" because such mob doesn't exist!", typeName));
						return null;
					}
				})
				.filter(Objects::nonNull)
				.toArray(EntityType[]::new);

		return new AnnoyingEntitiesHandler(blacklistedEntities);
	}

	public AnnoyanceHandler parseStormsHandler() 
	{
		ConfigurationSection section = this.config.getConfigurationSection("handlers.storms");
		
		//return null if the handler is disabled
		if(!section.getBoolean("active"))
			return null;

		String globalMessage = colorize(section.getString("global-message"));

		if(globalMessage.isEmpty())
			globalMessage = null;

		return new AnnoyingStormsHandler(globalMessage);
	}
}