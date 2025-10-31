package dte.comfortablelife.annoyancehandler;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.bukkit.ChatColor.RED;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import dte.modernjavaplugin.ModernJavaPlugin;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

public class AnnoyanceHandlerProvider
{
	private final Configuration config;
	private final ModernJavaPlugin plugin;

	public AnnoyanceHandlerProvider(Configuration config, ModernJavaPlugin plugin)
	{
		this.config = config;
		this.plugin = plugin;
	}

	public Collection<AnnoyanceHandler> getHandlers()
	{
		return Stream.of(parseEntitiesHandler(), parseStormsHandler())
				.filter(Objects::nonNull) //null means the handler was disabled in the config
				.collect(toList());
	}

	private AnnoyanceHandler parseEntitiesHandler()
	{
		return getActiveHandlerSection("entities")
				.map(section ->
				{
					Set<EntityType> blacklist = section.getStringList("blacklist").stream()
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
							.collect(toSet());

					return new EntitiesHandler(blacklist, this.plugin);
				})
				.orElse(null);
	}

	private AnnoyanceHandler parseStormsHandler()
	{
		return getActiveHandlerSection("storms")
				.map(unused -> new StormsHandler(this.plugin))
				.orElse(null);
	}
	
	private Optional<ConfigurationSection> getActiveHandlerSection(String handlerName)
	{
		ConfigurationSection section = this.config.getConfigurationSection("handlers." + handlerName);
		
		//return null if the handler is disabled
		if(!section.getBoolean("active"))
			return Optional.empty();
		
		return Optional.of(section);
	}
}