package dte.comfortablelife.annoyancehandlers.factory;

import static dte.comfortablelife.utils.ChatColorUtils.colorize;
import static org.bukkit.ChatColor.RED;

import java.util.Objects;

import dte.modernjavaplugin.ModernJavaPlugin;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import dte.comfortablelife.annoyancehandlers.AnnoyanceHandler;
import dte.comfortablelife.annoyancehandlers.EntitiesHandler;
import dte.comfortablelife.annoyancehandlers.StormsHandler;

public class AnnoyanceHandlerFactory 
{
	private final Configuration config;
	private final ModernJavaPlugin plugin;

	public AnnoyanceHandlerFactory(Configuration config, ModernJavaPlugin plugin)
	{
		this.config = config;
		this.plugin = plugin;
	}

	public AnnoyanceHandler parseEntitiesHandler()
	{
		ConfigurationSection section = getHandlerSection("entities");
		
		//null means the handler is disabled
		if(section == null)
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
						this.plugin.logToConsole(RED + String.format("Couldn't blacklist \"%s\" because such mob doesn't exist!", typeName));
						return null;
					}
				})
				.filter(Objects::nonNull)
				.toArray(EntityType[]::new);

		return new EntitiesHandler(this.plugin, blacklistedEntities);
	}

	public AnnoyanceHandler parseStormsHandler() 
	{
		ConfigurationSection section = getHandlerSection("storms");
		
		//null means the handler is disabled
		if(section == null)
			return null;

		String globalMessage = colorize(section.getString("global-message"));

		if(globalMessage.isEmpty())
			globalMessage = null;

		return new StormsHandler(globalMessage, this.plugin);
	}
	
	private ConfigurationSection getHandlerSection(String handlerName) 
	{
		ConfigurationSection section = this.config.getConfigurationSection("handlers." + handlerName);
		
		//return null if the handler is disabled
		if(!section.getBoolean("active"))
			return null;
		
		return section;
	}
}